package com.comov.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.adapters.ContactAdapter;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.UserResponse;
import com.comov.myapplication.datamodel.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactList extends AppCompatActivity implements ContactAdapter.ContactListener{
    private com.comov.myapplication.apiTools.APIService APIService;
    private RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        APIService = APIUtils.getAPIService();
        recyclerView = findViewById(R.id.contactList);
        contactAdapter = new ContactAdapter(new ArrayList<String>(),this);
        recyclerView.setAdapter(contactAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        username = getIntent().getStringExtra("username");
        getContactList();
    }

    private void getContactList(){
        APIService.getUser(MainView.token,username).enqueue(new Callback<UserResponse>(){
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    List<Users> usersList = response.body().getUsers();
                    List<String> contactsList =  usersList.get(0).getContacts();
                    contactAdapter.updateItems(contactsList);
                    contactAdapter.notifyDataSetChanged();

                } else if (response.code() == 404 )
                    Toast.makeText(getApplicationContext(), "Contacts not found." +
                            "", Toast.LENGTH_LONG).show();
                else if (response.code() == 500)
                    Toast.makeText(getApplicationContext(), "Server error." +
                            "", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t){
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClickContact(String contact) {
        APIService.getUser(MainView.token,contact).enqueue(new Callback<UserResponse>(){
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    List<Users> usersList = response.body().getUsers();
                    Intent intent = new Intent(ContactList.this, ContactProfile.class);
                    intent.putExtra("username", usersList.get(0).getName());
                    intent.putExtra("email", usersList.get(0).getEmail());
                    intent.putExtra("age", usersList.get(0).getAge());
                    startActivity(intent);
                } else if (response.code() == 404 )
                    Toast.makeText(getApplicationContext(), "Contact not found." +
                            "", Toast.LENGTH_LONG).show();
                else if (response.code() == 500)
                    Toast.makeText(getApplicationContext(), "Server error." +
                            "", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t){
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
            }
        });
    }
}
