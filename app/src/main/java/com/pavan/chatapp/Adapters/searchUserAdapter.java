package com.pavan.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.pavan.chatapp.ChatActivity;
import com.pavan.chatapp.Models.usermodel;
import com.pavan.chatapp.R;
import com.pavan.chatapp.Utils.Androidutil;
import com.pavan.chatapp.Utils.Firebaseutils;

public class searchUserAdapter extends FirestoreRecyclerAdapter<usermodel,searchUserAdapter.viewholder> {

    Context context;
    public searchUserAdapter(@NonNull FirestoreRecyclerOptions<usermodel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull usermodel model) {
        holder.name.setText(model.getUsername());
        holder.number.setText(model.getPhone());
        String currentUserID = Firebaseutils.currentuserId();
        if (currentUserID != null && currentUserID.equals(model.getUserid())) {
            holder.name.setText(model.getUsername() + "(me)");
        }

        holder.itemView.setOnClickListener(v->{
            Intent intent  =  new Intent(context, ChatActivity.class);
            Androidutil.passUserModelAsIntent(intent,model);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.searchuseritem,parent,false);
        return new viewholder(v);
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView name,number;
        ImageView profilepic;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name  = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            profilepic = itemView.findViewById(R.id.profilepic);
        }
    }
}
