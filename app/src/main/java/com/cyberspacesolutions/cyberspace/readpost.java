package com.cyberspacesolutions.cyberspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class readpost extends AppCompatActivity {
    TextView username,title,type,description,mitigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readpost);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username = (TextView) findViewById(R.id.usernameText);
        username.setText(extras.getString("username"));
        title = (TextView) findViewById(R.id.titleText);
        title.setText(extras.getString("title"));
        type = (TextView) findViewById(R.id.typeText);
        type.setText(extras.getString("type"));
        description = (TextView) findViewById(R.id.descriptionText);
        description.setText(extras.getString("description"));
        mitigation = (TextView) findViewById(R.id.mitigationText);
        mitigation.setText(extras.getString("mitigation"));
    }

    public void onBackClick(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}