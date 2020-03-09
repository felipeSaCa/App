package com.comov.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChatView extends AppCompatActivity {
    private TextView username;
    private ImageView image;
    private RecyclerView recyclerView;
    private EditText message;
    private Button btnSend;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            username = (TextView) findViewById(R.id.textViewUserChat);
            image = (ImageView) findViewById(R.id.imageViewChat);
            //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            image = (ImageView) findViewById(R.id.imageViewChat);
        }
}
