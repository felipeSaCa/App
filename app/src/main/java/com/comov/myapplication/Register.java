package com.comov.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.APITools.APIService;
import com.comov.myapplication.APITools.APIUtils;
import com.comov.myapplication.datamodel.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private APIService APIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText nickname = (EditText)findViewById(R.id.editNickmaneRegister);
        final EditText password = (EditText)findViewById(R.id.editPasswordRegister);
        final EditText email = (EditText)findViewById(R.id.editEmailRegister);
        final EditText phone = (EditText)findViewById(R.id.editPhoneRegister);

        Button btnRegisterRegister = (Button) findViewById(R.id.btnRegisterRegister);
        APIService = APIUtils.getAPIService();

        btnRegisterRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Comprobar campos
                if(nickname.getText().toString().matches("")) {
                    nickname.setError("Nickname is required");
                    nickname.requestFocus();
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


                String title = "POST";
                String body = nickname.getText().toString().trim() + " ";
                body += password.getText().toString().trim() + " ";
                body += email.getText().toString().trim() + " ";
                body += phone.getText().toString().trim() + " ";
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
                    sendPost(title, body);
                }
            }
        });
    }
    public void sendPost(String title, String body) {
        Post post = new Post(title, body, 1);
        APIService.newPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(getApplicationContext(), "Register OK", Toast.LENGTH_LONG).show();
                finish();
                // Ver si el usuario esta registrado antes o no y ese percal TODO
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register NOT OK", Toast.LENGTH_LONG).show();
                //Manejar error TODO
            }
        });
    }
}

