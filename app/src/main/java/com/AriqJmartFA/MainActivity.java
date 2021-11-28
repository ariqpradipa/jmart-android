package com.AriqJmartFA;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userMessage = findViewById(R.id.helloUser);

        String userName = "Hello " + LoginActivity.getLoggedInAccount().name + "!";
        userMessage.setText(userName);

    }
}