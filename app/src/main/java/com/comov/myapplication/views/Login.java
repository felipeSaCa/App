package com.comov.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.R;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
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

        /*MessageDigest auxCipherPassword = null;
        try {
            auxCipherPassword = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //auxCipherPassword.update(password.getText().toString().getBytes());
        String cipherPassword = auxCipherPassword.digest(password.getText().toString().getBytes()).toString();*/

        com.comov.myapplication.datamodel.Login login = new com.comov.myapplication.datamodel.Login(name.getText().toString(),password.getText().toString());
        sendLogin(login);

    }

    public void sendLogin(com.comov.myapplication.datamodel.Login login) {
        APIService.postLogin(login).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    String token = response.body().getToken();
                    String named = response.body().getName();
                    openMainView(named,token);
                }
                else if (response.code() == 404 || response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                System.out.println("###### Fail ####### " + call + "  " + t);
                call.cancel();
                Toast.makeText(getApplicationContext(), "Login NOT OK", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openRegister(View v) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    public void openMainView(String name, String token){
        Intent intent = new Intent(Login.this, MainView.class);
        intent.putExtra("name", name);
        intent.putExtra("token", token);
        startActivity(intent);
    }
}