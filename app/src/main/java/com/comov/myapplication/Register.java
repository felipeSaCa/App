package com.comov.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Post;
import com.comov.myapplication.datamodel.Users;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private APIService APIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText name = (EditText)findViewById(R.id.editNickmaneRegister);
        final EditText password = (EditText)findViewById(R.id.editPasswordRegister);
        final EditText email = (EditText)findViewById(R.id.editEmailRegister);
        final EditText age = (EditText)findViewById(R.id.editAgeRegister);

        Button btnRegisterRegister = (Button) findViewById(R.id.btnRegisterRegister);
        APIService = APIUtils.getAPIService();

        btnRegisterRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Comprobar campos
                if(name.getText().toString().matches("")) {
                    name.setError("User name is required");
                    name.requestFocus();
                    return;
                }
                if(email.getText().toString().matches("")) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ) {
                    email.setError("Enter a valid email");
                    email.requestFocus();
                    return;
                }
                if(password.getText().toString().matches("")) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if(password.getText().toString().trim().length() < 6) {
                    password.setError("At least 6 characters");
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

                Users user;
                if(age.getText().toString().trim().length() > 0) {
                    user = new Users(name.getText().toString(), password.getText().toString(), email.getText().toString(), age.getText().toString(), new ArrayList<String>());
                } else {
                    user = new Users(name.getText().toString(), password.getText().toString(), email.getText().toString(), new ArrayList<String>());
                }
                sendRegister(user);
            }
        });
    }
    public void sendRegister(Users user) {
        APIService.postRegister(user).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.code() == 401)
                    Toast.makeText(getApplicationContext(), "User already exists " + response.body().getId(), Toast.LENGTH_LONG).show();
                else if (response.code() == 201){
                    Toast.makeText(getApplicationContext(), "Register OK ", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register NOT OK " + t, Toast.LENGTH_LONG).show();
                //Manejar error TODO if (response.code() == ??? )
            }
        });
    }
}

