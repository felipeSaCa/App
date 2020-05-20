package com.comov.myapplication.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.R;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserChannel extends AppCompatActivity {
    private com.comov.myapplication.apiTools.APIService APIService;
    private String channelID;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_channel);
        APIService = APIUtils.getAPIService();
        channelID = getIntent().getStringExtra("channelID");
        token = getIntent().getStringExtra("token");

        final EditText textuserchannel = (EditText) findViewById(R.id.editAddUserChannel);
        Button btnAddUserChannel = (Button) findViewById(R.id.btnAddUserChannel);
        btnAddUserChannel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Comprobar campos
                if (textuserchannel.getText().toString().matches("")) {
                    textuserchannel.setError("User is required");
                    textuserchannel.requestFocus();
                    return;
                }
                Login userChannel = new Login(channelID, textuserchannel.getText().toString());
                addUserChannel(userChannel);
                textuserchannel.setText("");
                onBackPressed();
            }
        });
    }

    public void addUserChannel(Login userChannel) {
        APIService.postUserChannel(token,userChannel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code() + response.toString() );
                if (response.code() == 201){
                    Toast.makeText(getApplicationContext(), "Added user", Toast.LENGTH_LONG).show();
                }
                else if (response.code() == 404){
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server NOT OK", Toast.LENGTH_LONG).show();
            }
        });
    }
}
