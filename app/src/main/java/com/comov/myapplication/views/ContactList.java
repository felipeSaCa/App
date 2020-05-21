package com.comov.myapplication.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.adapters.ContactAdapter;
import com.comov.myapplication.apiTools.APIUtils;
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
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        APIService = APIUtils.getAPIService();
        recyclerView = findViewById(R.id.contactList);
        contactAdapter = new ContactAdapter(new ArrayList<String>(),this);
        username = getIntent().getStringExtra("username");
        token = getIntent().getStringExtra("token");
        getContactList();
    }

    private void getContactList(){
        APIService.getUser(token,username).enqueue(new Callback<Users>(){
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.code() == 200) {
                    List<String> contactsList = response.body().getContacts();
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
            public void onFailure(Call<Users> call, Throwable t){
                Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClickContact(String contact) {
        //TODO
    }
}
