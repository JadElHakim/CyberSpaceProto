package com.cyberspacesolutions.cyberspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginClick(View v){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
    public void onRegisterClick(View v){
        Intent intent = new Intent(this, SignupScreen.class);
        startActivity(intent);
    }
}