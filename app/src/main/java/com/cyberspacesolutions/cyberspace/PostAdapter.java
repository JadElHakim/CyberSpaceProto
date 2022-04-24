package com.cyberspacesolutions.cyberspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter {
    private Context pcontext;
    private ArrayList<Post> postslist = new ArrayList<>();
    public PostAdapter(@NonNull Context context,ArrayList<Post> posts) {
        super(context, 0,posts);
        pcontext = context;
        postslist = posts;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(pcontext).inflate(R.layout.activity_list_item,parent,false);

        Post currentpost = postslist.get(position);

        TextView title = (TextView) listItem.findViewById(R.id.TitleView);
        title.setText(currentpost.getPost_title());

        TextView info = (TextView) listItem.findViewById(R.id.infoView);
        info.setText(currentpost.getVulnerability_description());

        return listItem;
    }
}

