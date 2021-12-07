package com.AriqJmartFA;

import static com.AriqJmartFA.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        setTitle("About Me");

        String userName = loggedAccount.name;
        String userEmail = loggedAccount.email;
        double userBalance = loggedAccount.balance;

        TextView userNameTV = findViewById(R.id.name_Detail);
        TextView userEmailTV = findViewById(R.id.email_Detail);
        TextView userBalanceTV = findViewById(R.id.balance_Detail);

        userNameTV.setText(userName);
        userEmailTV.setText(userEmail);
        userBalanceTV.setText("" + userBalance);

        EditText topupBalance = findViewById(R.id.topup_Balance);
        Button topupButton = findViewById(R.id.topup_Button);


        topupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }
}