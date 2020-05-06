package com.comov.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;

public class MainView extends AppCompatActivity {
    private com.comov.myapplication.apiTools.APIService APIService;
    //private ImageView image;
    //private EditText message;
    //private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main_activity);
        APIService = APIUtils.getAPIService();
        //obtener con getIntent() los parametros de login obtenidos
        String username = getIntent().getStringExtra("name");
        TextView nameTxt = findViewById(R.id.user_name);
        String finalTxt = "Hi "+username;
        nameTxt.setText(finalTxt);

    }







}
