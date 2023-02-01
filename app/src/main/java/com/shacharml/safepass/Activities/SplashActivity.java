package com.shacharml.safepass.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.shacharml.safepass.R;


public class SplashActivity extends AppCompatActivity {

    private AppCompatImageView splash_IMG_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //find views
        splash_IMG_background = findViewById(R.id.splash_IMG_background);

        /**
         * Move to the next activity after 3 seconds
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish(); //the splash page is dead cant go back to it
            }
        }, 3000);
    }
}