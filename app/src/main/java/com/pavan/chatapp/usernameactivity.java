package com.pavan.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pavan.chatapp.Models.usermodel;
import com.pavan.chatapp.Utils.Firebaseutils;

public class usernameactivity extends AppCompatActivity {

    EditText username;
    Button btn;

    String phonenum;
    usermodel usermodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usernameactivity);

        username = findViewById(R.id.username);
        btn = findViewById(R.id.btn);

        phonenum = getIntent().getExtras().getString("phone");
        getUsername();

        btn.setOnClickListener(view -> {
            setusername();
        });


    }

    void setusername() {
        String user = username.getText().toString();
        if(user.isEmpty()||user.length()<4){
            username.setError("username should be atleast 4 characters");
            return;
        }
        if(usermodel!= null){
          usermodel.setUsername(user);
        }else{
            usermodel = new usermodel(phonenum,user, Timestamp.now(),Firebaseutils.currentuserId());
        }

        Firebaseutils.currentuserdetails().set(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(usernameactivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    void getUsername() {

         Firebaseutils.currentuserdetails().get().addOnCompleteListener(task -> {
             if(task.isSuccessful()){
                    usermodel usermodel = task.getResult().toObject(usermodel.class);
                    if(usermodel!= null){
                        username.setText(usermodel.getUsername());
                    }
             }
         });

    }
}