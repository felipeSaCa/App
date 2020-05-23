package com.comov.myapplication.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.R;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Channel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChatView extends AppCompatActivity{
    private com.comov.myapplication.apiTools.APIService APIService;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        APIService = APIUtils.getAPIService();
        username = getIntent().getStringExtra("username");

        final EditText title = (EditText)findViewById(R.id.editTitle);
        final EditText usuarios = (EditText)findViewById(R.id.editUsuarios);
        Button btnAddChannel = (Button) findViewById(R.id.btnAddChannel);
        btnAddChannel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Comprobar campos
                if (title.getText().toString().matches("")) {
                    title.setError("Title is required");
                    title.requestFocus();
                    return;
                }
                if (usuarios.getText().toString().matches("")) {
                    usuarios.setError("User/s is required");
                    usuarios.requestFocus();
                    return;
                }

                List<String> users = new LinkedList<>(Arrays.asList(usuarios.getText().toString().split(",")));
                users.add(0, username);
                Channel channel = new Channel(title.getText().toString(), users);
                addChannel(channel);
            }
        });
    }

    public void addChannel(Channel channel) {
        APIService.postChannel(MainView.token,channel).enqueue(new Callback<Channel>() {
            @Override
            public void onResponse(Call<Channel> call, Response<Channel> response) {
                System.out.println(response.code() + response.toString() );
                if (response.code() == 201){
                    Toast.makeText(getApplicationContext(), "Added Chat", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (response.code() == 500){
                    Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Channel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register NOT OK " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

}
