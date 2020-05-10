package com.comov.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.apiTools.APIUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //Get Channels
        getChannelsFromUser(username);
    }

    public void getChannelsFromUser(String user){
        APIService.getChannel(user).enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Got chats", Toast.LENGTH_LONG).show();
                    System.out.println(response.body() + " \n#############################" );
                    System.out.println(response.body().get("ChannelsObj"));
                    JsonArray channels = (JsonArray) response.body().get("ChannelsObj");

                    //System.out.println(channels[0]);
                } else if (response.code() == 404 )
                    Toast.makeText(getApplicationContext(), "Chats not found. Tu princesa esta en otro castillo." +
                            "", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t){
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
                System.out.println(t + " #######################################################################");
            }
        });
    }

    public void openAddChat(View v) {
        Intent intent = new Intent(MainView.this, AddChatView.class);
        startActivity(intent);
    }







}
