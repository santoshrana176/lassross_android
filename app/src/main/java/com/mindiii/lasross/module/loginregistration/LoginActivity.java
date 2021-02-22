package com.mindiii.lasross.module.loginregistration;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.facebook.login.widget.LoginButton;
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
import com.mindiii.lasross.module.loginregistration.loginPresenter.LoginPresenter;
import com.mindiii.lasross.module.loginregistration.model.ForgotPasswordResponse;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;
import com.mindiii.lasross.module.subscription.SubscriptionActivity;
import com.mindiii.lasross.utils.CommonUtils;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends LasrossParentActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, ApiCallback.LoginCallback {
    private static final String EMAIL = "email";
    private static final int REQ_CODE_GOOGLE = 9001;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText etEmail, etPass;
    private TextView tvEnglish, tvSpanish;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private Gson gson;
    private GoogleSignInClient googleSignInClient;
    private long mLastClickTime = 0;
    private LinearLayout llGoogle;
    private LinearLayout linearLayout, llFacebook;
    private Session session;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(this);
        setContentView(R.layout.login_form);
        init();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        llGoogle.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        llFacebook.setOnClickListener(this);

        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        LoginButton loginButton = findViewById(R.id.login_button);

        loginButton.setReadPermissions(Collections.singletonList(EMAIL));

        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                //etEmail.setText("Login successfull" + loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                //etEmail.setText("Login cancel");
            }

            @Override
            public void onError(FacebookException exception) {

            }
        });

        TextView tvSignUp = findViewById(R.id.tvSignUp);
        TextView tvForgotPass = findViewById(R.id.tvForgotPass);
        TextView tvSignIn = findViewById(R.id.tvSignIn);

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgetPasswordDialog();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                } catch (Exception e) {

                }
                if (email.isEmpty()) {
                    CommonUtils.showCustomAlert(LoginActivity.this, getString(R.string.Please_enter_email));
                    //etEmail.setError("Please enter email");
                    //return;
                } else if (!isValidEmail(email)) {
                    CommonUtils.showCustomAlert(LoginActivity.this, getString(R.string.Please_enter_valid_email));
                    // return;
                } else if (pass.isEmpty()) {
                    CommonUtils.showCustomAlert(LoginActivity.this, getString(R.string.Please_enter_password));
                    // return;
                } else if (!isValidPass(pass)) {
                    CommonUtils.showCustomAlert(LoginActivity.this, getString(R.string.password_max));
                    //etPass.setError("Password should have minimum 6 characters");
                } else {
                    if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                        callLoginApi(email, pass, getCurrentFirebaseToken());
                    } else {
                        showInternetAlertDialog(LoginActivity.this);
                    }
                }

                ////////////////  LOGIN  API CALL  ////////////////////
                //  if (isValidEmail(email) && isValidPass(pass)) {


                /*} else if (!isValidEmail(email)) {
                    CommonUtils.showCustomAlert(LoginActivity.this, "Please enter valid email");
                    //etEmail.setError("Please enter valid email");
                } else if (!isValidPass(pass)) {
                    CommonUtils.showCustomAlert(LoginActivity.this, "Password should have minimum 6 characters");
                    //etPass.setError("Password should have minimum 6 characters");
                }*/
            }
        });

    }

    private void callLoginApi(String myEmail, String password, String dToken) {
        new LoginPresenter(this, this).callLoginApi(myEmail, password, dToken);
    }

    private void init() {
        session = new Session(this);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        llGoogle = findViewById(R.id.llGoogle);
        linearLayout = findViewById(R.id.linearLayout);
        llFacebook = findViewById(R.id.llFacebook);
        tvEnglish = findViewById(R.id.tvEnglish);
        tvSpanish = findViewById(R.id.tvSpanish);
       etEmail.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAppLocale("en");
                session.setIsEnglishLanguage("en");
                startActivity(new Intent(LoginActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });

        tvSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAppLocale("es");
                session.setIsEnglishLanguage("es");
                startActivity(new Intent(LoginActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
    }

    void setAppLocale(String locale) {
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(locale.toLowerCase())); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);

    }

    public void fbLogin(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList(EMAIL));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
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

    private void callSocialApi(String name, String email, String id, String url, String type, String dToken) {
        new LoginPresenter(this, this).callSocialCheck(name, email, id, url, type, dToken);
    }

    private void openForgetPasswordDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);//,android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.forgot_password_dialog_artboard_2_1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText etEmailID;
        TextView tvSend;
        ImageView ivClose;
        etEmailID = dialog.findViewById(R.id.etEmailID);
        tvSend = dialog.findViewById(R.id.tvSend);
        ivClose = dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailDialog = etEmailID.getText().toString().trim();
                if (emailDialog.isEmpty()) {
                    CommonUtils.showCustomAlert(LoginActivity.this, getString(R.string.Please_enter_email));
                } else if (!isValidEmail(emailDialog)) {
                    CommonUtils.showCustomAlert(LoginActivity.this, getString(R.string.Please_enter_valid_email));
                } else {
                    if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                        validPasswordDetail(emailDialog, dialog);
                    } else {
                        showInternetAlertDialog(LoginActivity.this);
                    }
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.gravity = Gravity.TOP;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void validPasswordDetail(String email, final Dialog dialog) {
        new LoginPresenter(this, this).callForgotPasswordApi(email, dialog);
    }

    private boolean isValidEmail(String email) {
        return email.matches(emailPattern);
    }

    private boolean isValidPass(String pass) {
        return pass.length() > 5;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
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
            String url = String.valueOf(acct.getPhotoUrl());
            googleSignInClient.signOut();
            callSocialApi(fullName, socialEmail, socialId, url, "gmail", getCurrentFirebaseToken());
        } else {
            Log.e(",,,,,,,,,failhogaya,,,,", result.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (v.getId()) {
            case R.id.llGoogle:
                if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, REQ_CODE_GOOGLE);
                } else {
                    showInternetAlertDialog(LoginActivity.this);
                }
                break;
            case R.id.linearLayout:
                InputMethodManager inputMethodManager = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.llFacebook:
                if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                    fbLogin(v);
                } else {
                    showInternetAlertDialog(LoginActivity.this);
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSuccessLogin(LoginResponse loginResponse) {
        if (loginResponse.getStatus().equalsIgnoreCase("success")) {
            if (loginResponse.getMessage().equals("You are currently inactive by admin") || loginResponse.getMessage().equals("Actualmente estás inactivo por administrador")) {
                showDialog(this);
            } else {
                session.createRegistration(loginResponse.getData().getUserDetail());
                session.setNotificatioStatus(loginResponse.getData().getUserDetail().getPush_alert_status());
                session.setUserLoggedIn();
                session.setAuthToken(loginResponse.getData().getUserDetail().getAuth_token());
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        } else {
            CommonUtils.showCustomAlert(LoginActivity.this, loginResponse.getMessage());
        }
    }

    @Override
    public void onSuccessSocial(LoginResponse loginResponse) {
        if (loginResponse.getStatus().equalsIgnoreCase("success")) {

            if (loginResponse.getMessage().equals("You are currently inactive by admin") || loginResponse.getMessage().equals("Actualmente estás inactivo por administrador")) {
                showDialog(this);
            } else {
                session.createRegistration(loginResponse.getData().getUserDetail());
                session.setAuthToken(loginResponse.getData().getUserDetail().getAuth_token());
                session.setNotificatioStatus(loginResponse.getData().getUserDetail().getPush_alert_status());
                session.setUserLoggedIn();
                if (loginResponse.getMessageCode().equalsIgnoreCase("social_reg")) {
                    CommonUtils.toastMessage(LoginActivity.this, loginResponse.getMessage());
                    Intent intent = new Intent(LoginActivity.this, SubscriptionActivity.class);
                    startActivity(intent);
                    finish();
                    finishAffinity();
                } else {
                    CommonUtils.toastMessage(LoginActivity.this, loginResponse.getMessage());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    finishAffinity();
                }
            }
        } else {
            CommonUtils.showCustomAlert(LoginActivity.this, loginResponse.getMessage());
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

    @Override
    public void onForgotPassword(ForgotPasswordResponse forgotPasswordResponse, Dialog dialog) {
        if (forgotPasswordResponse.getStatus().equalsIgnoreCase("success")) {
            CommonUtils.toastMessage(this, forgotPasswordResponse.getMessage());
            dialog.dismiss();
        } else {
            CommonUtils.toastMessage(this, forgotPasswordResponse.getMessage());
        }
    }
}
