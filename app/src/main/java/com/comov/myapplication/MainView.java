package com.comov.myapplication;

import android.content.Intent;
import android.os.Bundle;
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
    List<Channel> prueba;
    RecyclerView recyclerViewChannel;
    String username;
    MainView mainView;
    //private EditText message;
    //private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main_activity);
        APIService = APIUtils.getAPIService();
        //obtener con getIntent() los parametros de login obtenidos
        username = getIntent().getStringExtra("name");
        TextView nameTxt = findViewById(R.id.user_name);
        String finalTxt = "Hi "+username;
        nameTxt.setText(finalTxt);
        channels = new ArrayList<Channel>();
        //Get Channels
        getChannelsFromUser(username);
        mainView = this;

        //Init recycler
        prueba = new ArrayList<Channel>();
        for (int i=0;i<10;i++){
            prueba.add( new Channel("Channel " +i,null,"wtf"));
        }



    }

    public void getChannelsFromUser(String user){

        APIService.getChannel(user).enqueue(new Callback<ChannelResponse>(){
            @Override
            public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Got chats", Toast.LENGTH_LONG).show();
                    channels = response.body().getChannels();
                    channels.get(0);
                    System.out.println(channels.get(0).getTitle() + " \n#############################");
                    System.out.println(response.toString()+ " \n#############################");
                    System.out.println("Peta 1");
                    recyclerViewChannel = findViewById(R.id.channelsList);
                    System.out.println("Peta 2");
                    ChannelAdapter channelAdapter = new ChannelAdapter(channels,mainView);
                    System.out.println("Peta 3");
                    recyclerViewChannel.setAdapter(channelAdapter);
                    System.out.println("Peta 4");
                    recyclerViewChannel.setLayoutManager(new LinearLayoutManager(mainView));
                    System.out.println("Por que no pinta?");

                } else if (response.code() == 404 )
                    Toast.makeText(getApplicationContext(), "Chats not found. Tu princesa esta en otro castillo." +
                            "", Toast.LENGTH_LONG).show();
            }
            @Override

            public void onFailure(Call<ChannelResponse> call, Throwable t){

                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
                System.out.println(t + " #######################################################################");
            }
        });
    }

    public void openAddChat(View v) {
        Intent intent = new Intent(MainView.this, AddChatView.class);
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
        intent.putExtra("channelID", channel.getUid());
        intent.putExtra("title", channel.getTitle());
        startActivity(intent);
    }
}
