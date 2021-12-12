package com.AriqJmartFA;

import androidx.appcompat.app.AppCompatActivity;

import com.AriqJmartFA.request.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.*;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AriqJmartFA.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Gson gson = new Gson();
    public static Account loggedAccount = null;

    public static Account getLoggedInAccount() {

        return loggedAccount;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        EditText emailLogin = findViewById(R.id.loginEmail);
        EditText passwordLogin = findViewById(R.id.loginPassword);
        Button buttonLogin = findViewById(R.id.loginButton);
        TextView registerLogin = findViewById(R.id.registerLogin);

        buttonLogin.setOnClickListener(v -> {

            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if(object != null) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        loggedAccount = gson.fromJson(object.toString(), Account.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "username or password is wrong!", Toast.LENGTH_LONG).show();

                }
            };

            Response.ErrorListener errorListener = errorResponse -> {

                Toast.makeText(LoginActivity.this, "username or password is wrong!", Toast.LENGTH_LONG).show();

            };

            LoginRequest loginRequest = new LoginRequest(emailLogin.getText().toString(), passwordLogin.getText().toString(), listener, errorListener);

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(loginRequest);

        });

        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);

            }
        });
    }
}