package com.comov.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.adapters.MessageAdapter;
import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Message;
import com.comov.myapplication.datamodel.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatView extends AppCompatActivity {

    private APIService APIservice;
    private String username;
    private String channelID;
    private String title;
    private String token;
    private List<Message> messages;
    private RecyclerView recyclerView;
    private Handler handler;
    private Runnable runnable;
    private MessageAdapter messageAdapter;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        APIservice = APIUtils.getAPIService();
        username = getIntent().getStringExtra("username");
        channelID = getIntent().getStringExtra("channelID");
        title = getIntent().getStringExtra("title");
        token = getIntent().getStringExtra("token");

        handler = new Handler();
        runnable = () -> {
            getMessages();
            handler.postDelayed(runnable,1000);
        };

        messages = new ArrayList<Message>();
        recyclerView = findViewById(R.id.recyclerChat);
        messageAdapter = new MessageAdapter(messages, username);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        String titleToText = title;
        TextView titleText = findViewById(R.id.TitleChat);
        titleText.setText(titleToText);


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

    public void getMessages(){
        APIservice.getMessage(token,channelID).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.code() == 200){
                        //Toast.makeText(getApplicationContext(),"Mensajes llegados",Toast.LENGTH_LONG).show();
                        List<Message> messages1 = response.body().getMessages();
                        messageAdapter.addItems(messages1);
                        messageAdapter.notifyDataSetChanged();


                }
                else if (response.code() == 404){
                    Toast.makeText(getApplicationContext(), "Messsages not found." +
                            "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void postMessage(View view){
        EditText text = findViewById(R.id.editWriteAMessage);
        if(text.getText().toString().matches("")){
            return;
        }
        Message mymessage = new Message(text.getText().toString(),username, channelID);
        text.getText().clear();
        APIservice.postMessage(token,mymessage).enqueue(new Callback<Message>(){

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code() == 201){
                    //Toast.makeText(getApplicationContext(),"Se envia correctamente",Toast.LENGTH_LONG).show();
                }
                else if(response.code() == 500){
                    Toast.makeText(getApplicationContext(), "Internal server error" +
                            "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    public void deleteChannel(View view){
        APIservice.deleteChannel(token,channelID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(),"Channel removed",Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (response.code() == 500){
                    Toast.makeText(getApplicationContext(), "Server error." +
                            "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openAddUserChannel(View view){
        Intent intent = new Intent(this, AddUserChannel.class);
        intent.putExtra("channelID", channelID);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void takePic(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
