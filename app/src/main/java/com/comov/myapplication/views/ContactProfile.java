package com.comov.myapplication.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.comov.myapplication.R;

public class ContactProfile extends AppCompatActivity {
    private String username;
    private String email;
    private String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        age = getIntent().getStringExtra("age");

        final TextView tvname = (TextView)findViewById(R.id.textContactUsername);
        final TextView tvemail = (TextView)findViewById(R.id.textContactEmail);
        final TextView tvage = (TextView)findViewById(R.id.textContactAge);

        tvname.setText(username);
        tvemail.setText(email);
        if (age.matches("")){
            tvage.setText("No age defined");
        } else
            tvage.setText(age);

    }
}
