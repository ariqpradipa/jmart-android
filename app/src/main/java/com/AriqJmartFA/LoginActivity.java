package com.AriqJmartFA;

import androidx.appcompat.app.AppCompatActivity;

import com.AriqJmartFA.request.LoginRequest;
import com.android.volley.Response;
import com.google.gson.*;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.AriqJmartFA.model.Account;

public class LoginActivity extends AppCompatActivity {

    Gson gson;
    Account loggedAccount = null;

    Account getLoggedInAccount() {

        return loggedAccount;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText emailLogin = findViewById(R.id.email);
        EditText passwordLogin = findViewById(R.id.password);
        Button buttonLogin = findViewById(R.id.button);
        LoginRequest loginRequest;
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener listener = new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                    }

                };
                LoginRequest loginRequest = new LoginRequest(emailLogin.getText().toString(), passwordLogin.getText().toString()));
            }
        });
    }
}