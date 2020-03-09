package com.comov.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.datamodel.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private com.comov.myapplication.APITools.APIService APIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void openLogin(View v) {
        final EditText username = (EditText)findViewById(R.id.editUsername);
        final EditText password = (EditText)findViewById(R.id.editPassword);
        if(username.getText().toString().matches("")) {
            username.setError("Username is required");
            username.requestFocus();
            return;
        }
        if(password.getText().toString().matches("")) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        Login login = new Login(username.getText().toString(), password.getText().toString());

        if (sendLogin(login)){
            Intent intent = new Intent(MainActivity.this, ChatView.class);
            startActivity(intent);
        }
    }

    public boolean sendLogin(Login login) {
        APIService.postLogin(login).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Login OK", Toast.LENGTH_LONG).show();
                // if (response.code() == 201){ }
                // Ver si el usuario esta registrado antes o no y ese percal TODO
                // set boolean
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), "Login NOT OK", Toast.LENGTH_LONG).show();
                //Manejar error TODO
            }
        });
        // set boolean
        return true;
    }

    public void openRegister(View v) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}
