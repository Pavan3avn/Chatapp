package com.pavan.chatapp;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.pavan.chatapp.Adapters.searchUserAdapter;
import com.pavan.chatapp.Models.usermodel;
import com.pavan.chatapp.Utils.Firebaseutils;

public class searchactivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchbtn;
    RecyclerView recyclerView;
    ImageButton backbtn;
    searchUserAdapter searchUserAdapter;

    String userInput;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_searchactivity);

        searchInput = findViewById(R.id.search_username_input);
        searchbtn = findViewById(R.id.search_user_btn);
        recyclerView = findViewById(R.id.search_user_recycler_view);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(v->{
            onBackPressed();
        });

        searchbtn.setOnClickListener(v->{
            userInput = searchInput.getText().toString();
            if(userInput.isEmpty() || userInput.length() <=3){
                searchInput.setError("Invalid username");
                return;
            }
            setupsearchrecyclerview();
        });


    }

    private void setupsearchrecyclerview() {

        Query query = Firebaseutils.allusercollectionReference()
                .whereGreaterThanOrEqualTo("username",userInput);

        FirestoreRecyclerOptions<usermodel> options = new FirestoreRecyclerOptions.Builder<usermodel>()
                .setQuery(query,usermodel.class).build();


        searchUserAdapter = new searchUserAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchUserAdapter);
        searchUserAdapter.startListening();
    }

    @Override
    protected  void onStart() {
        super.onStart();
        if(searchUserAdapter!= null){
            searchUserAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(searchUserAdapter!= null){
            searchUserAdapter.stopListening();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        if(searchUserAdapter!= null){
            searchUserAdapter.notifyDataSetChanged();
        }
    }
}
