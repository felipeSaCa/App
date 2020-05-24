package com.comov.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.adapters.ChannelAdapter;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Channel;
import com.comov.myapplication.datamodel.ChannelResponse;
import com.comov.myapplication.services.NotificationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainView extends AppCompatActivity implements ChannelAdapter.ChannelListener {
    private com.comov.myapplication.apiTools.APIService APIService;
    RecyclerView recyclerViewChannel;
    ChannelAdapter channelAdapter;
    List<Channel> channels;
    String username;
    Runnable runnable;
    Handler handler;
    private Intent requestIntent;
    private static MainView mainView;
    private Boolean backgroundService = false;
    public static volatile String current_channel;
    public static String token;

    final Handler tokenHandler = new Handler();

    public static MainView getInstance(){
        return mainView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //notService = new Intent(this, Background.class);
        setSupportActionBar(toolbar);
        APIService = APIUtils.getAPIService();
        mainView = this;

        handler = new Handler();
        runnable = () -> {
            getChannelsFromUser();
            handler.postDelayed(runnable,5000);
        };

        // Token Refresh
        /*tokenHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Refreshing token." +
                        "", Toast.LENGTH_LONG).show();
                APIService.getLogin(token,username).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.code() == 200){
                            token = response.body().getToken();
                            Toast.makeText(getApplicationContext(), "Token refreshed." +
                                    "", Toast.LENGTH_LONG).show();
                        }
                        else if (response.code() == 500){
                            Toast.makeText(getApplicationContext(), "Fail to refresh token." +
                                    "", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 10000);*/
        //1800000

        //obtener con getIntent() los parametros de login obtenidos
        username = getIntent().getStringExtra("name");
        token = getIntent().getStringExtra("token");
        if(!backgroundService){
            requestIntent = new Intent(this, NotificationService.class);
            NotificationService.startNotificationService(3,MainView.this, requestIntent, username, token);
            backgroundService = true;
        }
        channels = new ArrayList<Channel>();
        recyclerViewChannel = findViewById(R.id.channelsList);
        channelAdapter = new ChannelAdapter(channels,mainView);
        recyclerViewChannel.setAdapter(channelAdapter);
        recyclerViewChannel.setLayoutManager(new LinearLayoutManager(mainView));
        //Get Channels

        getChannelsFromUser();
    }

    public static String getCurrent_channel() {
        return current_channel;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        current_channel ="";
        runnable.run();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopNotification();
    }

    private void stopNotification() {
        stopService(requestIntent);
        backgroundService = false;
    }


    public void getChannelsFromUser(){
        APIService.getChannel(token,username).enqueue(new Callback<ChannelResponse>(){
            @Override
            public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {
                if (response.code() == 200) {
                    List<Channel> channels1 = response.body().getChannels();
                    channelAdapter.updateItems(channels1);
                    channelAdapter.notifyDataSetChanged();

                } else if (response.code() == 404 )
                    Toast.makeText(getApplicationContext(), "Chats not found." +
                            "", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<ChannelResponse> call, Throwable t){
                Toast.makeText(getApplicationContext(), "Fail in connection", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }



    public void openAddChannel() {
        Intent intent = new Intent(MainView.this, AddChatView.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openAddContact(){
        Intent intent = new Intent(MainView.this, AddContactView.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openContactList(){
        Intent intent = new Intent(MainView.this, ContactList.class);
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
        current_channel = channel.get_id();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutMenu)
            finish();
        else if (id == R.id.contactListMenu)
            openContactList();
        else if (id == R.id.addContactMenu)
            openAddContact();
        else if (id == R.id.addChannelMenu)
            openAddChannel();
        return true;
    }
}
