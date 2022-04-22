package com.cyberspacesolutions.cyberspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberspacesolutions.cyberspace.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ArrayList<Post> listitems = new ArrayList<Post>();

    ListView posts ;
    String res = "";
    String fetchpost = "http://192.168.1.105:8080/cyberspace/fetchposts.php";
    String data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_home_screen);
        replaceFragment(new HomeFragment());
        posts = findViewById(R.id.HomeListView);
        getData();
       // adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item,listitems);
     ///   posts.setAdapter(adapter);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.publish:
                    replaceFragment(new PublishFragment());
                    break;
                case R.id.notifications:
                    replaceFragment(new NotificationFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
//    private void getData(){
//        try {
//
//            URL url = new URL(fetchpost);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            InputStream is = new BufferedInputStream(con.getInputStream());
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//            String line = "";
//            while(br.readLine() != null){
//                Toast.makeText(getBaseContext(), "now i is appending", Toast.LENGTH_SHORT).show();
//                sb.append(line+"\n");
//            }
//            is.close();
//            String result = sb.toString();
//            JSONArray ja = new JSONArray(result);
//            JSONObject jo = null;
//            data = new String[ja.length()];
//            for(int i =0 ; i < ja.length() ; i++){
//                jo = ja.getJSONObject(i);
//                data[i] = jo.getString("username");
//                Toast.makeText(getBaseContext(), ""+data[i], Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void getData(){
        new DownloadTask().execute();
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
             String result = "";
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(fetchpost);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb= new StringBuilder();
                while ((result = br.readLine())!= null){
                    sb.append(result+"\n");
                }
                br.close();
                in.close();
                http.disconnect();
                return sb.toString().trim();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
             catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            //TextView test = (TextView) findViewById(R.id.infoView);
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for(int i =0; i < ja.length() ; i++) {
                    JSONObject test = ja.getJSONObject(i);
                    String post_title = test.getString("post_title");
                    String vulnerability_type = test.getString("vulnerability_type");
                    String vulnerability_description = test.getString("vulnerability_description");
                    String mitigation_description = test.getString("mitigation_description");
                    int id = test.getInt("id");
                    Post p = new Post(post_title,vulnerability_type,vulnerability_description,mitigation_description,id);
                    listitems.add(p);
                    ArrayAdapter<Post> adapter= new ArrayAdapter<Post>(getApplicationContext(),
                            android.R.layout.activity_list_item,
                            listitems);
                }
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), "i failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
           // test.setText(s);
        }

    }
}