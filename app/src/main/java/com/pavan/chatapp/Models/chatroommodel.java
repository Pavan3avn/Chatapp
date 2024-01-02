package com.pavan.chatapp.Models;

import com.google.firebase.Timestamp;

import java.util.List;

public class chatroommodel {

    String chatroomId;
    List<String> userid;
    Timestamp lastmsgtime;
    String lastmessage;

    String lastMessageSenderId;

    public chatroommodel() {
    }

    public chatroommodel(String chatroomId, List<String> userid, Timestamp lastmsgtime, String lastmessage) {
        this.chatroomId = chatroomId;
        this.userid = userid;
        this.lastmsgtime = lastmsgtime;
        this.lastmessage = lastmessage;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserid() {
        return userid;
    }

    public void setUserid(List<String> userid) {
        this.userid = userid;
    }

    public Timestamp getLastmsgtime() {
        return lastmsgtime;
    }

    public void setLastmsgtime(Timestamp lastmsgtime) {
        this.lastmsgtime = lastmsgtime;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
