package com.pavan.chatapp.Utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class Firebaseutils {

    public static String currentuserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isloggedin(){
        if(currentuserId()!= null){
            return true;
        }
        return false;
    }

    public static DocumentReference currentuserdetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentuserId());
    }

    public static CollectionReference allusercollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }

    public static String getChatroomId(String userId1,String userId2){
        if (userId1 != null && userId2 != null) {
            if (userId1.hashCode() < userId2.hashCode()) {
                return userId1 + "_" + userId2;
            } else {
                return userId2 + "_" + userId1;
            }
        }
        else {
            // Handle the case where either userId1 or userId2 is null
            return "fallback_chatroom_id";
        }
    }

    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(Firebaseutils.currentuserId())){
            return allusercollectionReference().document(userIds.get(1));
        }else{
            return allusercollectionReference().document(userIds.get(0));
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

//    public static StorageReference  getCurrentProfilePicStorageRef(){
//        return FirebaseStorage.getInstance().getReference().child("profile_pic")
//                .child(FirebaseUtil.currentUserId());
//    }
//
//    public static StorageReference  getOtherProfilePicStorageRef(String otherUserId){
//        return FirebaseStorage.getInstance().getReference().child("profile_pic")
//                .child(otherUserId);
//    }
}
