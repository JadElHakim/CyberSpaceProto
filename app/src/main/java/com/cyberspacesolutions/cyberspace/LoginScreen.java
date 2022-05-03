package com.cyberspacesolutions.cyberspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginScreen extends AppCompatActivity {
    EditText username,password;
    Button Signin;
    String SigninURL = "http://192.168.1.105:8080/cyberspace/loginuser.php";
    SignInExecution be ;
    User user;
    SharedPreferences sp;

    class SignInExecution extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection http;
            String usernameHolder= username.getText().toString().trim();
            String passwordHolder= password.getText().toString().trim();
            Log.e("testing",passwordHolder);
            String res = "";
            try{
                url = new URL(SigninURL);
                http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setDoInput(true);
                http.setChunkedStreamingMode(0);
                OutputStream outputStream = http.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);
                writer.write("username="+usernameHolder+"&"+"password="+passwordHolder);
                writer.flush();
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb= new StringBuilder();
                while ((res = br.readLine())!= null){
                    sb.append(res+"\n");
                }
                br.close();
                in.close();
                http.disconnect();
                return sb.toString().trim();
            }
            catch(Exception e){
                e.printStackTrace();
                Log.e("err","error");
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray ja = null;
            try {
                if(result.compareTo("Not Logged") != 0){
                    SharedPreferences.Editor editor = sp.edit();
                    ja = new JSONArray(result);
                    JSONObject test = ja.getJSONObject(0);
                    int id = test.getInt("id");
                    editor.putInt("id",id);
                    String username = test.getString("username");
                    editor.putString("username",username);
                    String career = test.getString("career");
                    editor.putString("career",career);
                    String profile_description = test.getString("profile_description");
                    editor.putString("profile_description", profile_description);
                    user = new User(id, username, career, profile_description);
                    editor.commit();
                    Intent intent = new Intent(getBaseContext(), HomeScreen.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }

                } catch (JSONException e) {
                Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username = (EditText) findViewById(R.id.loginUsername);
        password = (EditText) findViewById(R.id.logInpassword);
        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
    }
    public void onLoginClick(View view){
        be = new SignInExecution();
        be.execute();
    }

    public void onBackClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}