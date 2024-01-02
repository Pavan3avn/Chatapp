package com.pavan.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.pavan.chatapp.Adapters.chatrecycleadapter;
import com.pavan.chatapp.Models.ChatmessageModel;
import com.pavan.chatapp.Models.chatroommodel;
import com.pavan.chatapp.Models.usermodel;
import com.pavan.chatapp.Utils.Androidutil;
import com.pavan.chatapp.Utils.Firebaseutils;


import org.w3c.dom.Text;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {



    usermodel otheruser;
    EditText chatmsgview;
    ImageButton sendbtn;
    ImageButton backbtn;
    RecyclerView recyclerView;
    TextView otherusername;
    chatrecycleadapter adapter;

    String chatroomId;
    chatroommodel chatroommodel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        otheruser = Androidutil.getUserModelFromIntent(getIntent());
        chatroomId = Firebaseutils.getChatroomId(Firebaseutils.currentuserId(),otheruser.getUserid());
        chatmsgview = findViewById(R.id.chatmsgview);
        sendbtn = findViewById(R.id.sendbtn);
        recyclerView = findViewById(R.id.recycler_view);
        otherusername = findViewById(R.id.otherusername);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(v->{
            onBackPressed();
        });
        otherusername.setText(otheruser.getUsername());

        sendbtn.setOnClickListener(v->{
            String message = chatmsgview.getText().toString().trim();
            if(message.isEmpty())
                return;
            sendMessageToUser(message);
        });

        getOrCreateChatroomModel();
        setupChatRecyclerView();

    }

    private void sendMessageToUser(String message) {

        chatroommodel.setLastmsgtime(Timestamp.now());
        chatroommodel.setLastMessageSenderId(Firebaseutils.currentuserId());
        chatroommodel.setLastmessage(message);
        Firebaseutils.getChatroomReference(chatroomId).set(chatroommodel);

        ChatmessageModel chatMessageModel = new ChatmessageModel(message,Firebaseutils.currentuserId(),Timestamp.now());
        Firebaseutils.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                           chatmsgview.setText("");
                            sendNotification(message);
                        }
                    }
                });
    }

    private void sendNotification(String message) {
    }

    void getOrCreateChatroomModel(){
        Firebaseutils.getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chatroommodel = task.getResult().toObject(chatroommodel.class);
                if(chatroommodel==null){
                    //first time chat
                    chatroommodel = new chatroommodel(
                            chatroomId,
                            Arrays.asList(Firebaseutils.currentuserId(),otheruser.getUserid()),
                            Timestamp.now(),
                            ""
                    );
                    Firebaseutils.getChatroomReference(chatroomId).set(chatroommodel);
                }
            }
        });
    }

    void setupChatRecyclerView(){
        Query query = Firebaseutils.getChatroomMessageReference(chatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING
                );

        FirestoreRecyclerOptions<ChatmessageModel> options = new FirestoreRecyclerOptions.Builder<ChatmessageModel>()
                .setQuery(query,ChatmessageModel.class).build();

        adapter = new chatrecycleadapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

}