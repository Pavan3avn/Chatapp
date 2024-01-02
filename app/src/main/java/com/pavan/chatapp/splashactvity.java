package com.pavan.chatapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

import com.pavan.chatapp.Utils.Firebaseutils;


public class splashactvity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Firebaseutils.isloggedin()){
                    startActivity(new Intent(splashactvity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(splashactvity.this,loginactivity.class));
                }
                finish();
            }
        },2000);


}
}