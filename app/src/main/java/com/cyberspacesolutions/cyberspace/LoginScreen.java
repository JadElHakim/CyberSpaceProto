package com.cyberspacesolutions.cyberspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void onBackClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}