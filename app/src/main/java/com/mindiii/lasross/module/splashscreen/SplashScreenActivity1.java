package com.mindiii.lasross.module.splashscreen;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.home.HomeActivity;
import com.mindiii.lasross.module.loginregistration.LoginActivity;

import java.security.MessageDigest;


public class SplashScreenActivity1 extends LasrossParentActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_1);
        getKeyHashFacebook();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                session = new Session(SplashScreenActivity1.this);
                if (session.getUserLoggedIn() && session.getRegistration() != null) {
                    Intent i = new Intent(SplashScreenActivity1.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreenActivity1.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    //Get Hask Key for facebook integration
    private void getKeyHashFacebook() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}