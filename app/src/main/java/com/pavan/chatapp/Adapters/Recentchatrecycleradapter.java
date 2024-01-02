package com.pavan.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pavan.chatapp.ChatActivity;
import com.pavan.chatapp.Models.chatroommodel;
import com.pavan.chatapp.Models.usermodel;
import com.pavan.chatapp.R;
import com.pavan.chatapp.Utils.Androidutil;
import com.pavan.chatapp.Utils.Firebaseutils;

public class Recentchatrecycleradapter extends FirestoreRecyclerAdapter<chatroommodel, Recentchatrecycleradapter.ChatroomModelViewHolder> {

    Context context;

    public Recentchatrecycleradapter(@NonNull FirestoreRecyclerOptions<chatroommodel> options, Context context) {
        super(options);
        this.context = context;
    }
    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatitem,parent,false);
        return new ChatroomModelViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull chatroommodel model) {
        Firebaseutils.getOtherUserFromChatroom(model.getUserid())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        boolean lastMessageSentByMe = model.getLastMessageSenderId().equals(Firebaseutils.currentuserId());


                        usermodel otherUser = task.getResult().toObject(usermodel.class);
                        holder.usernameText.setText(otherUser.getUsername());
                        if(lastMessageSentByMe)
                            holder.lastMessageText.setText("You : "+model.getLastmessage());
                        else
                            holder.lastMessageText.setText(model.getLastmessage());
                        holder.lastMessageTime.setText(Firebaseutils.timestampToString(model.getLastmsgtime()));

                        holder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(context, ChatActivity.class);
                            Androidutil.passUserModelAsIntent(intent,otherUser);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        });}


                });
    }


    class ChatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView lastMessageText;
        TextView lastMessageTime;
        ImageView profilePic;

        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.name);
            lastMessageText = itemView.findViewById(R.id.lastmsg);
            lastMessageTime = itemView.findViewById(R.id.time);
            profilePic = itemView.findViewById(R.id.profile);
        }
    }
}