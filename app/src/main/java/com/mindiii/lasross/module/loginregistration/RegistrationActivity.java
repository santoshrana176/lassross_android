package com.mindiii.lasross.module.loginregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.home.HomeActivity;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;
import com.mindiii.lasross.module.loginregistration.presenter.SignUpPresenter;
import com.mindiii.lasross.module.subscription.SubscriptionActivity;
import com.mindiii.lasross.utils.CommonUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Pattern;


public class RegistrationActivity extends LasrossParentActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
        , ApiCallback.SignUpCallback {
    private static final int REQ_CODE_GOOGLE = 9001;
    private static final String EMAIL = "email";
    String dToken;
    private TextView tvSignUp, tvForgotPass, tvSignIn;
    private EditText etFullName, etEmail, etPass;
    private LinearLayout llImage;
    private LinearLayout linearLayout, llGoogle, llFacebook;
    private Pattern pattern;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient googleSignInClient;
    private Gson gson;
    private GsonBuilder gsonBuilder;
    private Session session;
    private CallbackManager callbackManager;
    private long mLastClickTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);
        init();
        Session session = new Session(this);
        dToken = getCurrentFirebaseToken();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        callbackManager = CallbackManager.Factory.create();

        llGoogle = findViewById(R.id.llGoogle);
        linearLayout = findViewById(R.id.linearLayout);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        llGoogle.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        llFacebook.setOnClickListener(this);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));

        progressDialog = new ProgressDialog(this);
        llImage = findViewById(R.id.llImage);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        final String formattedDate = df.format(c.getTime());
        Log.e("date is ", formattedDate);

        pattern = Pattern.compile("^[a-zA-Z ]+$");

        tvSignIn = findViewById(R.id.tvSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etFullName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (name.equals("")) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Please enter full name");
                    return;
                } else if (name.length() < 3) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Full name should not less than 2 characters");
                    return;
                } else if (email.equals("")) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Please enter email");
                    return;
                } else if (pass.equals("")) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Please enter password");
                    return;
                }

                ////////////////  REGISTRATION  API CALL  ////////////////////
                if (isValidEmail(email) && isValidPass(pass) && isValidName(name)) {
                    if (CommonUtils.isNetworkAvailable(RegistrationActivity.this)) {
                        callSignUpApi(name, email, pass, formattedDate, getCurrentFirebaseToken());
                    } else {
                        showInternetAlertDialog(RegistrationActivity.this);
                    }
                } else if (!isValidName(name)) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Only alphabets are allowed in full name");
                } else if (!isValidEmail(email)) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Please enter valid email");
                } else if (!isValidPass(pass)) {
                    CommonUtils.showCustomAlert(RegistrationActivity.this, "Password should have minimum 6 characters");
                }
            }
        });
    }

    // simple signup
    private void callSignUpApi(String name, String email, String pass, String formattedDate, String dToken) {
        new SignUpPresenter(this, this).callSignUpApi(name, email, pass, "", "2", dToken);
    }

    private void init() {
        session = new Session(this);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        llGoogle = findViewById(R.id.llGoogle);
        linearLayout = findViewById(R.id.linearLayout);
        llFacebook = findViewById(R.id.llFacebook);
    }

    public void fbLogin(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList(EMAIL));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //etEmail.setText("Login successfull" +loginResult.getAccessToken().getUserId());
                        //etEmail.setText("Login successfull");
                        final GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.e("Main", response.toString());
                                        try {
                                            String name = object.getString("name");
                                            String email = object.getString("email");
                                            String id = object.getString("id");
                                            JSONObject picture = object.getJSONObject("picture");
                                            JSONObject data = picture.getJSONObject("data");
                                            String url = data.getString("url");
                                            LoginManager.getInstance().logOut();
                                            callSocialApi(name, email, id, url, "facebook", getCurrentFirebaseToken());
                                        } catch (Exception e) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name,email,id,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        //etEmail.setText("Login cancel");
                        //tvNameEmail.setText("Login cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isValidPass(String pass) {
        return pass.length() > 5;
    }

    private boolean isValidName(String lName) {
        return pattern.matcher(lName).matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            /*Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleresult(task);*/
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        String socialId, socialEmail;
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            if (acct.getId() != null)
                socialId = acct.getId();
            else
                socialId = "";
            if (acct.getEmail() != null)
                socialEmail = acct.getEmail();
            else
                socialEmail = "";
            String fullName = acct.getDisplayName();
            if (fullName != null) {

            }
            String url = String.valueOf(acct.getPhotoUrl());
            googleSignInClient.signOut();
            callSocialApi(fullName, socialEmail, socialId, url, "gmail", getCurrentFirebaseToken());
            //  validateSocialLogin(fullName, socialEmail, socialId, url, "gmail");
        } else {
            Log.e(",,,,,,,,,failhogaya,,,,", result.toString());
            //CustomToast.getInstance(this).showToast(this, "fail");
        }
    }

    private void callSocialApi(String fullName, String socialEmail, String socialId, String url, String type, String dToken) {
        new SignUpPresenter(this, this).callSocialCheck(fullName, socialEmail, socialId
                , url, type, dToken);
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (v.getId()) {
            case R.id.llGoogle:
                if (CommonUtils.isNetworkAvailable(RegistrationActivity.this)) {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, REQ_CODE_GOOGLE);
                } else {
                    showInternetAlertDialog(RegistrationActivity.this);
                }
                break;
            case R.id.linearLayout:
                InputMethodManager inputMethodManager = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.llFacebook:
                if (CommonUtils.isNetworkAvailable(RegistrationActivity.this)) {
                    fbLogin(v);
                } else {
                    showInternetAlertDialog(RegistrationActivity.this);
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSuccessSocial(LoginResponse loginResponse) {
        if (loginResponse.getStatus().equalsIgnoreCase("success")) {
            session.createRegistration(loginResponse.getData().getUserDetail());
            session.setUserLoggedIn();
            //session.setAuthToken(loginResponse.getData().getUserDetail().getAuth_token());
            if (loginResponse.getMessageCode().equalsIgnoreCase("social_reg")) {
                CommonUtils.toastMessage(RegistrationActivity.this, loginResponse.getMessage());
                Intent intent = new Intent(RegistrationActivity.this, SubscriptionActivity.class);
                startActivity(intent);
                finish();
                finishAffinity();
            } else {
                CommonUtils.toastMessage(RegistrationActivity.this, loginResponse.getMessage());
                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                finishAffinity();
            }
        } else {
            CommonUtils.showCustomAlert(this, loginResponse.getMessage());
        }
    }

    @Override
    public void onSuccessSignUp(LoginResponse loginResponse) {
        if (loginResponse.getStatus().equalsIgnoreCase("success")) {
            session.createRegistration(loginResponse.getData().getUserDetail());
        //    session.setAuthToken(loginResponse.getData().getUserDetail().getAuth_token());
            session.setUserLoggedIn();
            startActivity(new Intent(RegistrationActivity.this, SubscriptionActivity.class));
            finish();
        } else {
            CommonUtils.showCustomAlert(this, loginResponse.getMessage());
        }
    }

    @Override
    public void onShowBaseLoader() {
        showLoader();
    }

    @Override
    public void onHideBaseLoader() {
        hideLoader();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
