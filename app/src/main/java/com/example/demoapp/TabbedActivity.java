package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.demoapp.ui.main.SectionsPagerAdapter;
import com.example.demoapp.databinding.ActivityTabbedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import javax.xml.validation.SchemaFactoryLoader;

public class TabbedActivity extends AppCompatActivity{
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.demoapp.databinding.ActivityTabbedBinding binding = ActivityTabbedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        ImageButton imageButton =binding.manutab;
        ImageView profile=findViewById(R.id.profilebutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(TabbedActivity.this, ShowalluserActivity.class);
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(v -> {
            auth=FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent = new Intent(TabbedActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(TabbedActivity.this,ProfileEditeActivity.class);
            startActivity(intent);
        });
    }

}