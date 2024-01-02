package com.pavan.chatapp.Utils;

import android.content.Intent;

import com.pavan.chatapp.Models.usermodel;

public class Androidutil {

    public static void passUserModelAsIntent(Intent intent, usermodel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getUserid());

    }

    public static usermodel getUserModelFromIntent(Intent intent){
        usermodel userModel = new  usermodel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserid(intent.getStringExtra("userId"));
//        userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }
}
