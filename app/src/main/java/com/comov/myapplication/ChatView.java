package com.comov.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;

public class ChatView extends AppCompatActivity {

    private APIService APIservice;
    private String username;
    private String channelID;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APIservice = APIUtils.getAPIService();
        username = getIntent().getStringExtra("username");
        channelID = getIntent().getStringExtra("channelID");
        title = getIntent().getStringExtra("title");
        TextView titleText = findViewById(R.id.titleChannel);
        titleText.setText(title);
        //TODO habria que recibir la lista de mensajes(tanto interno como externo), asi como los que componen el chat
        setContentView(R.layout.activity_chat);

    }
}
