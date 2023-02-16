package com.shacharml.safepass.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shacharml.safepass.R;


public class SplashActivity extends AppCompatActivity {

    private AppCompatImageView splash_IMG_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /**
         *Take instance of Action Bar using getSupportActionBar and
         *if it is not Null then call hide function
         **/
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //find views
        splash_IMG_background = findViewById(R.id.splash_IMG_background);

        /**
         * Move to the next activity after 3 seconds
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish(); //the splash page is dead cant go back to it

            }
        }, 3000);
    }
}