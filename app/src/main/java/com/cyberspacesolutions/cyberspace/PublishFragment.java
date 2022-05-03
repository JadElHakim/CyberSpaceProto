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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.StringEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishFragment extends Fragment{
    Spinner sp;
    EditText title;
    String type;
    EditText vdesc;
    EditText mdesc;
    Button post;
    String PostUrl= "http://192.168.1.105:8080/cyberspace/publishpost.php";
    SharedPreferences sharedP;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PublishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublishFragment newInstance(String param1, String param2) {
        PublishFragment fragment = new PublishFragment();
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
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        sharedP = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        sp = (Spinner) view.findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        title = view.findViewById(R.id.postTitle);
        vdesc = view.findViewById(R.id.Vdescription);
        mdesc = view.findViewById(R.id.Mdescription);
        post =  view.findViewById(R.id.postButton);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("pressed?","Yes i am pressed");
                new PostExecution().execute();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    class PostExecution extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection http;
            String titleHolder = title.getText().toString().trim();
            String typeHolder = type;
            String vdescHolder = vdesc.getText().toString().trim();
            String mdescHolder = mdesc.getText().toString().trim();
            String res = "";

            String usernameholder= sharedP.getString("username","");
           // Toast.makeText(getActivity().getApplicationContext(), ""+username, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title",titleHolder);
                jsonObject.put("vulnerability_description",vdescHolder);
                jsonObject.put("mitigation_description",mdescHolder);
                jsonObject.put("vulnerability_type",typeHolder);
                jsonObject.put("username",usernameholder);
                String json = jsonObject.toString();
                url = new URL(PostUrl);
                http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setDoInput(true);
                http.setChunkedStreamingMode(0);
                http.setRequestProperty("Content-Type", "application/json; utf-8");
                http.setRequestProperty("Accept", "application/json");
                http.setRequestProperty("Content-Length", Integer.toString(json.getBytes().length));
                http.setRequestProperty("Content-Language", "en-US");
                Log.e("sent?",""+json);
                DataOutputStream outputStream = new DataOutputStream(http.getOutputStream());
                outputStream.writeBytes(json);
                outputStream.flush();
                outputStream.close();
                Log.e("sent?","yes it is sent");
                http.disconnect();

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("err", "error"+e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity().getApplicationContext(), "this is result"+result, Toast.LENGTH_SHORT).show();
            Log.e("ok but?","yes i reached here");
        }
    }
}