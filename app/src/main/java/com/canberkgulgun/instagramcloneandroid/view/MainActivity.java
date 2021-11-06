package com.canberkgulgun.instagramcloneandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.canberkgulgun.instagramcloneandroid.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(/*context=*/ this);



    }
}