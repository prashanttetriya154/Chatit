package com.example.demoapp;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView username,userabout;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setUser(Application application, String uid, String name, String phonenumber, String profileImage, String about){
      imageView= itemView.findViewById(R.id.userprofile);
      username =imageView.findViewById(R.id.usernamerecycle);
      userabout=imageView.findViewById(R.id.lastmessagerecycle);
        Picasso.get().load(profileImage).into(imageView);
        username.setText(name);
        if (about.equals("")){
            userabout.setText("Hii");
        }else {
            userabout.setText(about);
        }
    }
}
