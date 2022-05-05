package com.cyberspacesolutions.cyberspace;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ArrayList<Post> listitems = new ArrayList<Post>();

    ListView posts ;
    String res = "";
    String fetchpost = "http://192.168.1.106:8080/cyberspace/fetchposts.php";
    String data[];

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        posts = (ListView) view.findViewById(R.id.HomeListView);
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
        getData();
        // Inflate the layout for this fragment
        return view;
    }
    //execute asynctask
    public void getData(){
        new DownloadTask().execute();
    }
    //async task to connect to db through php script
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
                //read json from php and display it in the list views
                ja = new JSONArray(s);
                for(int i =ja.length()-1; i >= 0 ; i--) {
                    JSONObject test = ja.getJSONObject(i);
                    String post_title = test.getString("post_title");
                    String vulnerability_type = test.getString("vulnerability_type");
                    String vulnerability_description = test.getString("vulnerability_description");
                    String mitigation_description = test.getString("mitigation_description");
                    String username = test.getString("username");
                    //custom adapter for custom list view
                    Post p = new Post(post_title,vulnerability_type,vulnerability_description,mitigation_description,username);
                    listitems.add(p);
                }
                PostAdapter adapter= new PostAdapter(getActivity(), listitems);
                posts.setAdapter(adapter);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "i failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

}