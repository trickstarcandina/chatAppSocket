package com.example.chattogether.ui.service;

public class OnInitializedSocketStream{

    OnCreatedSocketStream onCreatedSocketStream;
    public OnInitializedSocketStream( OnCreatedSocketStream onCreatedSocketStream){
        this.onCreatedSocketStream = onCreatedSocketStream;
    }

    public void dispatch(){
        onCreatedSocketStream.callback();
    }

}
