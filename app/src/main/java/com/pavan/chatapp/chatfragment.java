package com.pavan.chatapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.pavan.chatapp.Adapters.Recentchatrecycleradapter;
import com.pavan.chatapp.Adapters.searchUserAdapter;
import com.pavan.chatapp.Models.chatroommodel;
import com.pavan.chatapp.Models.usermodel;
import com.pavan.chatapp.Utils.Firebaseutils;


public class chatfragment extends Fragment {

    RecyclerView recyclerView;
    Recentchatrecycleradapter recentchatrecycleradapter;


    public chatfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chatfragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_fragment);
        setuprecyclerview();
        return view;
    }

    private void setuprecyclerview() {

        Query query = Firebaseutils.allusercollectionReference()
                .whereArrayContains("userIds",Firebaseutils.currentuserId())
                .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<chatroommodel> options = new FirestoreRecyclerOptions.Builder<chatroommodel>()
                .setQuery(query,chatroommodel.class).build();


        recentchatrecycleradapter = new Recentchatrecycleradapter(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recentchatrecycleradapter);
        recentchatrecycleradapter.startListening();
        recentchatrecycleradapter.updateOptions(options);
    }

    @Override
    public  void onStart() {
        super.onStart();
        if(recentchatrecycleradapter!= null){
            recentchatrecycleradapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(recentchatrecycleradapter!= null){
            recentchatrecycleradapter.stopListening();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        if(recentchatrecycleradapter!= null){
            recentchatrecycleradapter.notifyDataSetChanged();
        }
    }
}