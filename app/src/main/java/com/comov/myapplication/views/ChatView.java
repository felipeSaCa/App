package com.comov.myapplication.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.adapters.MessageAdapter;
import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Message;
import com.comov.myapplication.datamodel.MessageResponse;
import com.comov.myapplication.location.LocationAddressModel;
import com.comov.myapplication.location.LocationHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatView extends AppCompatActivity {


    public final static int MY_LOCATION_PERMISSION = 0;
    public final static int MY_LOCATION_PERMISSION_FINE = 1;

    private FusedLocationProviderClient fusedLocationClient;


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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
        Message mymessage = new Message(text.getText().toString(),username, channelID,Message.TEXT_MESSAGE);
        text.getText().clear();
        APIservice.postMessage(token,mymessage).enqueue(new Callback<Message>(){

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code() == 500){
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
                call.cancel();
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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            int nh = (int) ( imageBitmap.getHeight() * (300.0 / imageBitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, 300 , nh, true);
            String encodedImage = Base64.encodeToString(bitmapToByteArray(scaled), Base64.DEFAULT);
            Message photo = new Message(encodedImage,username,channelID,Message.IMAGE_MESSAGE);

            APIservice.postPic(token,photo).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 201) {

                    } else if (response.code() == 500){
                        Toast.makeText(getApplicationContext(), "Server error" +
                                "", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fail " + t, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            return bos.toByteArray();
        }finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkPermissionsLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, MY_LOCATION_PERMISSION);
            }
        } else {
        }
    }



    public void getLocation(View v) {
        checkPermissionsLocation();
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if(location != null){
                LatLng coordenadas = new LatLng(location.getLatitude(),location.getLongitude());
                Log.i("Prueba de concepto","coordendas-> latitud:"+ coordenadas.latitude+" longitud: "+coordenadas.longitude);
                Message locationMsg = new Message(parseCoordenadasToString(coordenadas),username,channelID,Message.TEXT_MESSAGE);
                APIservice.postLocation(token, locationMsg).enqueue(new Callback<ResponseBody>(){

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==201){

                        }else if (response.code() == 500){
                            Toast.makeText(getApplicationContext(), "Server error" +
                                    "", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Pues no hay ubicacion parece" +
                        "", Toast.LENGTH_LONG).show();
            }
        });

    }

    public String parseCoordenadasToString(LatLng location){
        return location.latitude+","+location.longitude;
    }


}
