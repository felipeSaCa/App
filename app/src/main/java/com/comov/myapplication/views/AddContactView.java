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
import com.comov.myapplication.datamodel.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactView extends AppCompatActivity {
    private com.comov.myapplication.apiTools.APIService APIService;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        APIService = APIUtils.getAPIService();
        username = getIntent().getStringExtra("username");

        final EditText textcontact = (EditText) findViewById(R.id.editAddContact);
        Button btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Comprobar campos
                if (textcontact.getText().toString().matches("")) {
                    textcontact.setError("Contact is required");
                    textcontact.requestFocus();
                    return;
                }
                Login contact = new Login(username, textcontact.getText().toString());
                addContact(contact);
                textcontact.setText("");
                onBackPressed();
            }
        });
    }

    public void addContact(Login contact) {
        APIService.postContact(MainView.token,contact).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                System.out.println(response.code() + response.toString() );
                if (response.code() == 201){
                    Toast.makeText(getApplicationContext(), "Added Contact", Toast.LENGTH_LONG).show();
                }
                else if (response.code() == 404){
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server NOT OK", Toast.LENGTH_LONG).show();
            }
        });
    }
}
