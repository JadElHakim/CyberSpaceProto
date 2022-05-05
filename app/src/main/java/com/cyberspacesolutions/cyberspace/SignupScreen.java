package com.cyberspacesolutions.cyberspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SignupScreen extends AppCompatActivity {
    EditText username,email,password,verify_password;
    Button SignUp;
    //sign up post request url
    String SignupURL = "http://192.168.1.106:8080/cyberspace/registeruser.php",res;
    BackendExecution be ;

    //async task to connect to db through php script
        class BackendExecution extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                URL url;
                HttpURLConnection http;
                String usernameHolder= username.getText().toString().trim();
                String emailHolder= email.getText().toString().trim();
                String passwordHolder= password.getText().toString().trim();
                 Log.e("testing",passwordHolder);
                try{

                    url = new URL(SignupURL);
                    http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoOutput(true);
                    http.setDoInput(true);
                    http.setChunkedStreamingMode(0);
                    OutputStream outputStream = http.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter writer = new BufferedWriter(outputStreamWriter);

                    writer.write("username="+usernameHolder+"&"+"email="+emailHolder+"&"+"password="+passwordHolder);
                    writer.flush();
                }
                catch(Exception e){
                e.printStackTrace();
                return null;
                }
                    http.disconnect();
                    return "data posted";
            }

                @Override
                protected void onPostExecute(String result) {
                    res=result;
                    Log.d("result",""+res);
                    Toast. makeText(getApplicationContext(),res,Toast. LENGTH_SHORT).show();
                }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        username = findViewById(R.id.userNameRegister);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passwordRegister);
        verify_password = findViewById(R.id.verifyPassword);
        SignUp = findViewById(R.id.SignUpButton);
    }
    public void onBackClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onSignUp(View view) {
            //check if all fields are filled else prompt user
        if (username.getText().toString().matches("") && email.getText().toString().matches("") && password.getText().toString().matches("") && verify_password.getText().toString().matches("")) {

            Toast.makeText(getBaseContext(), "Please fill in All fields", Toast.LENGTH_SHORT).show();
        }
        else{
            //check if password field matches the verify password field else prompt user
            if (password.getText().toString().matches(verify_password.getText().toString())){
                be = new BackendExecution();
                be.execute();
                setContentView(R.layout.activity_login_screen);
            } else {
                Toast.makeText(getBaseContext(), "Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onSignIn(View view){
            //go to signin page
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
    }
    public void onBackPressed() {
        return;
    }
    }

