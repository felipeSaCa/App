package com.comov.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.adapters.ChannelAdapter;
import com.comov.myapplication.apiTools.APIUtils;

import com.comov.myapplication.datamodel.*;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainView extends AppCompatActivity implements ChannelAdapter.ChannelListener {
    private com.comov.myapplication.apiTools.APIService APIService;
    List<Channel> channels;
    RecyclerView recyclerViewChannel;
    String username;
    MainView mainView;
    ChannelAdapter channelAdapter;
    Handler handler;
    Runnable runnable;
    //private EditText message;
    //private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main_activity);
        APIService = APIUtils.getAPIService();
        mainView = this;
        handler = new Handler();
        runnable = () -> {
            getChannelsFromUser();
            handler.postDelayed(runnable,5000);
        };
        //obtener con getIntent() los parametros de login obtenidos
        username = getIntent().getStringExtra("name");
        TextView nameTxt = findViewById(R.id.user_name);
        String finalTxt = "Hi "+username;
        nameTxt.setText(finalTxt);
        channels = new ArrayList<Channel>();
        recyclerViewChannel = findViewById(R.id.channelsList);
        channelAdapter = new ChannelAdapter(channels,mainView);
        recyclerViewChannel.setAdapter(channelAdapter);
        recyclerViewChannel.setLayoutManager(new LinearLayoutManager(mainView));
        //Get Channels

        getChannelsFromUser();
    }


    @Override
    protected void onStart() {
        super.onStart();
        runnable.run();

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);

    }

    public void getChannelsFromUser(){
        APIService.getChannel(username).enqueue(new Callback<ChannelResponse>(){
            @Override
            public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {
                if (response.code() == 200) {
                    //Toast.makeText(getApplicationContext(), "Got chats", Toast.LENGTH_LONG).show();
                    channels = response.body().getChannels();
                    channelAdapter.addItems(channels);
                    channelAdapter.notifyDataSetChanged();

                } else if (response.code() == 404 )
                    Toast.makeText(getApplicationContext(), "Chats not found." +
                            "", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<ChannelResponse> call, Throwable t){
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openAddChannel(View v) {
        Intent intent = new Intent(MainView.this, AddChatView.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openAddContact(View v){
        Intent intent = new Intent(MainView.this, AddContactView.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Listener implementado para diferenciar canales
     * @param channel
     */
    @Override
    public void onClickChannel(Channel channel) {
        Intent intent = new Intent(this, ChatView.class);
        intent.putExtra("username", username);
        intent.putExtra("channelID", channel.get_id());
        intent.putExtra("title", channel.getTitle());
        startActivity(intent);
    }
}
