package com.AriqJmartFA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AriqJmartFA.request.RegisterRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText nameRegister = findViewById(R.id.registerName);
        EditText emailRegister = findViewById(R.id.registerEmail);
        EditText passwordRegister = findViewById(R.id.registerPassword);
        Button button = findViewById(R.id.registerButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject object = new JSONObject(response);
                            if(object != null) {

                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }
                        } catch(JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "something when wrong", Toast.LENGTH_LONG).show();

                        }
                    }
                };

                Response.ErrorListener errorListener = errorResponse -> {

                    Toast.makeText(RegisterActivity.this, "something when wrong", Toast.LENGTH_LONG).show();

                };

                RegisterRequest registerRequest = new RegisterRequest(nameRegister.getText().toString(), emailRegister.getText().toString(), passwordRegister.getText().toString(), listener, errorListener);

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(registerRequest);

            }
        });
    }
}