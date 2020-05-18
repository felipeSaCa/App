package com.comov.myapplication.views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import com.comov.myapplication.services.Background;

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
    String token;
    private Intent notService;
    private static MainView mainView;
    Runnable runnable;
    Handler handler;
    private static String current_channel;
    //private EditText message;
    //private Button btnSend;

    public static MainView getInstance(){
        return mainView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        notService = new Intent(this, Background.class);
        setSupportActionBar(toolbar);
        APIService = APIUtils.getAPIService();
        mainView = this;
        createNotificationChannel();

        handler = new Handler();
        runnable = () -> {
            getChannelsFromUser();
            handler.postDelayed(runnable,5000);
        };
        //obtener con getIntent() los parametros de login obtenidos
        username = getIntent().getStringExtra("name");
        token = getIntent().getStringExtra("token");
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

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(getString(R.string.channelID),name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
        startNotification();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        stopNotification();
    }


    public void getChannelsFromUser(){
        APIService.getChannel(token,username).enqueue(new Callback<ChannelResponse>(){
            @Override
            public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {
                if (response.code() == 200) {
                    List<Channel> channels1 = response.body().getChannels();
                    channelAdapter.addItems(channels1);
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

    private void startNotification() {
        startService(notService);

    }

    private void stopNotification() {
        stopService(notService);
    }

    public void openAddChannel() {
        Intent intent = new Intent(MainView.this, AddChatView.class);
        intent.putExtra("username", username);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void openAddContact(){
        Intent intent = new Intent(MainView.this, AddContactView.class);
        intent.putExtra("username", username);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void openContactList(){
        Intent intent = new Intent(MainView.this, ContactList.class);
        intent.putExtra("username", username);
        intent.putExtra("token", token);
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
        intent.putExtra("token", token);
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
