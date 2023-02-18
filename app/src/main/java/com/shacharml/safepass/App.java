package com.shacharml.safepass;

import android.app.Application;

import com.shacharml.safepass.Utils.HelperHTML;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize HTML viewer helper singleton
        HelperHTML.initHelper();

    }
}
