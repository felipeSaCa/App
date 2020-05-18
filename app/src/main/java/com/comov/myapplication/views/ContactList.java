package com.comov.myapplication.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.R;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactList extends AppCompatActivity {
    private com.comov.myapplication.apiTools.APIService APIService;
    private String username;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        APIService = APIUtils.getAPIService();
        username = getIntent().getStringExtra("username");
        token = getIntent().getStringExtra("token");
        getCoctactList();
    }

    private void getCoctactList(){
        APIService.getUser(token,username).enqueue(new Callback<Users>(){
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.code() == 200) {
                    List<String> contactsList = response.body().getContacts();

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
}
