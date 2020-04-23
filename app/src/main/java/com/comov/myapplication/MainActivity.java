package com.comov.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private com.comov.myapplication.apiTools.APIService APIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        APIService = APIUtils.getAPIService();
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
        final EditText name = findViewById(R.id.editUsername);
        final EditText password = findViewById(R.id.editPassword);

        if(name.getText().toString().matches("")) {
            name.setError("Username is required");
            name.requestFocus();
            return;
        }
        if(password.getText().toString().matches("")) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        Login login = new Login(name.getText().toString(), password.getText().toString());
        sendLogin(login);
        Intent intent = new Intent(MainActivity.this, ChatView.class);
        startActivity(intent);
    }

    public void sendLogin(Login login) {
        APIService.postLogin(login).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Login OK", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                }
                Log.e("TOKEN", response.toString());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("###### Fail ####### " + call + "  " + t);
                call.cancel();
                Toast.makeText(getApplicationContext(), "Login NOT OK", Toast.LENGTH_LONG).show();
                //Manejar error TODO
            }
        });
    }

    public void openRegister(View v) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}
