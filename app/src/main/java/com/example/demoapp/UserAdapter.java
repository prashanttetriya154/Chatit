package com.example.demoapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class UserAdapter extends FirebaseRecyclerAdapter<User, UserAdapter.Viewholder> {
    public UserAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder viewholder, int i, @NonNull User user) {
        viewholder.username.setText(user.getName());
        viewholder.userabout.setText(user.getAbout());
        Picasso.get().load(user.getProfileImage()).into(viewholder.userimage);
        
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userrecyclerview,parent,false);
        return new Viewholder(view);
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        ShapeableImageView userimage;
        TextView username;
        TextView userabout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            userimage = itemView.findViewById(R.id.userprofile);
            username = itemView.findViewById(R.id.usernamerecycle);
            userabout = itemView.findViewById(R.id.lastmessagerecycle);
        }
    }
}
