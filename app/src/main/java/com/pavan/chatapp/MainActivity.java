package com.pavan.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton searchbtn;


    chatfragment chatfragment;
    profilefragment profilefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatfragment = new chatfragment();
        profilefragment = new profilefragment();

        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        searchbtn = findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,searchactivity.class));
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              if(item.getItemId() == R.id.chat){
                  getSupportFragmentManager().beginTransaction().replace(R.id.framelay,chatfragment);
              }
                if(item.getItemId() == R.id.profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelay,profilefragment);
                }
                    return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.chat);


    }
}