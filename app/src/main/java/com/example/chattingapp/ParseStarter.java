package com.example.chattingapp;

import android.app.Application;

import com.parse.Parse;

public class ParseStarter  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("myappID")
                .clientKey("jVkqiYzfWMz2")
                .server("http://3.16.29.59/parse/")
                .build()
        );
    }
}
