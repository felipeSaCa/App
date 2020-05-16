package com.comov.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Login;
import com.comov.myapplication.datamodel.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactView extends AppCompatActivity {
    private com.comov.myapplication.apiTools.APIService APIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        APIService = APIUtils.getAPIService();

        final EditText textcontact = (EditText) findViewById(R.id.editAddContact);
        Button btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Comprobar campos
                if (textcontact.getText().toString().matches("")) {
                    textcontact.setError("Title is required");
                    textcontact.requestFocus();
                    return;
                }
                Login contact = new Login("USER QUE SE PASA POR EXTRA", textcontact.getText().toString());//TODO
                addContact(contact);
            }
        });
    }

    public void addContact(Login contact) {
        APIService.postContact(contact).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                System.out.println(response.code() + response.toString() );
                if (response.code() == 201){
                    Toast.makeText(getApplicationContext(), "Added Contact 201: ", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (response.code() == 500){
                    Toast.makeText(getApplicationContext(), "500", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server NOT OK " + t, Toast.LENGTH_LONG).show();
                //Manejar error TODO if (response.code() == ??? )
            }
        });
    }
}
