package com.comov.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;

public class ChatView extends AppCompatActivity {

    private APIService APIservice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APIservice = APIUtils.getAPIService();
        //TODO habria que recibir la lista de mensajes(tanto interno como externo), asi como los que componen el chat
        setContentView(R.layout.activity_chat);

    }
}
