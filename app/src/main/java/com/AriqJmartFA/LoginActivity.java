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
import android.widget.Toast;

import com.AriqJmartFA.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Gson gson = new Gson();
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

        buttonLogin.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if(object != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //loggedAccount = gson.fromJSON(object.toString(), Account.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            };

            LoginRequest loginRequest = new LoginRequest(emailLogin.getText().toString(), passwordLogin.getText().toString(), listener, (Response.ErrorListener) listener);

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(loginRequest);

        });
    }
}