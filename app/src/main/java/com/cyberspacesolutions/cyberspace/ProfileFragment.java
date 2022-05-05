package com.cyberspacesolutions.cyberspace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    TextView usernameView;
    TextView careerView;
    TextView profile_descriptionView,countOfPosts;
    SharedPreferences sp;
    Button logout;
    String fetchpost = "http://192.168.1.106:8080/cyberspace/userposts.php";
    String data[];
    ArrayList<Post> listitems = new ArrayList<Post>();
    int counter = 0;
    String username;

    ListView posts ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //get profile info from shared preferences
         sp = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        int id = sp.getInt("id", 0);
        //set user info into profile fields
        username = sp.getString("username", "");
        String career = sp.getString("career", "");
        String profile_description = sp.getString("profile_description","");
        usernameView =  view.findViewById(R.id.UserNameProfile);
        careerView = view.findViewById(R.id.UserWorkField);
        countOfPosts = view.findViewById(R.id.PublishedResearchText);
        profile_descriptionView = view.findViewById(R.id.UserDescription);
        posts= view.findViewById(R.id.PublishesList);
        posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post p = listitems.get(i);
                Bundle ex = new Bundle();
                Intent intent = new Intent(getActivity(),readpost.class);
                ex.putString("username",p.getUsername());
                ex.putString("title",p.getPost_title());
                ex.putString("type",p.getVulnerability_type());
                ex.putString("description",p.getVulnerability_description());
                ex.putString("mitigation",p.getMitigation_description());
                intent.putExtras(ex);
                startActivity(intent);

            }
        });
        usernameView.setText(username);
        careerView.setText(career);
        profile_descriptionView.setText(profile_description);
        // Inflate the layout for this fragment
        logout = view.findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
            }
        });
        getData();
        return view;
    }
    //execute asynctask
    public void getData() {
        new DownloadTask().execute(); }

    //async task to connect to db through php script
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(fetchpost);
                http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setDoInput(true);
                http.setChunkedStreamingMode(0);
                OutputStream outputStream = http.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);
                writer.write("username="+username);
                writer.flush();
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                while ((result = br.readLine()) != null) {
                    sb.append(result + "\n");
                }
                br.close();
                in.close();
                http.disconnect();
                return sb.toString().trim();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            //TextView test = (TextView) findViewById(R.id.infoView);
            JSONArray ja = null;
            try {
                //read json from php and display it in the list views
                ja = new JSONArray(s);
                for (int i = ja.length() - 1; i >= 0; i--) {
                    counter++;
                    JSONObject test = ja.getJSONObject(i);
                    String post_title = test.getString("post_title");
                    String vulnerability_type = test.getString("vulnerability_type");
                    String vulnerability_description = test.getString("vulnerability_description");
                    String mitigation_description = test.getString("mitigation_description");
                    String username = test.getString("username");
                    //custom adapter for custom list view
                    Post p = new Post(post_title, vulnerability_type, vulnerability_description, mitigation_description, username);
                    listitems.add(p);
                }
                countOfPosts.setText("Published "+counter);
                PostAdapter adapter = new PostAdapter(getActivity(), listitems);
                posts.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}