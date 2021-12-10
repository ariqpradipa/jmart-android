package com.AriqJmartFA;

import static com.AriqJmartFA.LoginActivity.getLoggedInAccount;
import static com.AriqJmartFA.LoginActivity.loggedAccount;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.AriqJmartFA.model.Account;
import com.AriqJmartFA.model.Product;
import com.AriqJmartFA.model.Store;
import com.AriqJmartFA.request.RegisterStoreRequest;
import com.AriqJmartFA.request.TopUpRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutMeActivity extends AppCompatActivity {

    Gson gson = new Gson();
    public static String PARENT_URL = "http://10.0.2.2:8080/account/";
    public static String USER_URL = "http://10.0.2.2:8080/account/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        setTitle("About Me");

        String userName = loggedAccount.name;
        String userEmail = loggedAccount.email;
        String userBalance = Double.toString(getLoggedInAccount().balance);

        TextView userNameTV = findViewById(R.id.name_Detail);
        TextView userEmailTV = findViewById(R.id.email_Detail);
        TextView userBalanceTV = findViewById(R.id.balance_Detail);

        userNameTV.setText(userName);
        userEmailTV.setText(userEmail);
        userBalanceTV.setText(userBalance);

        EditText topupBalance = findViewById(R.id.topup_Balance);
        Button topupButton = findViewById(R.id.topup_Button);

        topupButton.setOnClickListener(v -> {

            Response.Listener<String> listener = response -> {

            };

            Response.ErrorListener errorListener = errorResponse -> {

            };

            if(topupBalance.getText().toString().length() == 0 || topupBalance.getText().toString().contains(" ")) {

                Toast.makeText(AboutMeActivity.this, "Input only number!", Toast.LENGTH_SHORT).show();


            } else {

                Toast.makeText(AboutMeActivity.this, "Top Up Success!", Toast.LENGTH_SHORT).show();

                TopUpRequest topupRequest = new TopUpRequest(Integer.toString(loggedAccount.id), topupBalance.getText().toString(), listener, errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(topupRequest);

                loggedAccount.balance += Double.parseDouble(topupBalance.getText().toString());
                Intent intent = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                startActivity(intent);


            }
        });

        androidx.constraintlayout.widget.ConstraintLayout storeLayout = findViewById(R.id.store_layout);
        androidx.constraintlayout.widget.ConstraintLayout storeCreateLayout = findViewById(R.id.register_store_id);
        storeCreateLayout.setVisibility(View.GONE);

        Button registerStore = findViewById(R.id.register_store_button);

        if(getLoggedInAccount().store == null) {

            storeLayout.setVisibility(View.INVISIBLE);

        } else {

            String storeName = loggedAccount.store.name;
            String storeAddress = loggedAccount.store.address;
            String storePhone = loggedAccount.store.phoneNumber;

            TextView storeNameTV = findViewById(R.id.store_name);
            TextView storeAddressTV = findViewById(R.id.store_address);
            TextView storePhoneTV = findViewById(R.id.phone_number);

            storeNameTV.setText(storeName);
            storeAddressTV.setText(storeAddress);
            storePhoneTV.setText(storePhone);

            registerStore.setVisibility(View.GONE);

        }

        EditText storeName = findViewById(R.id.store_name_register);
        EditText storeAddress = findViewById(R.id.store_address_register);
        EditText storePhone = findViewById(R.id.store_phone_register);

        registerStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerStore.setVisibility(View.GONE);
                storeCreateLayout.setVisibility(View.VISIBLE);

            }
        });

        Button cancelStoreRegister = findViewById(R.id.store_cancel_button);
        Button createStoreRegister = findViewById(R.id.store_register_button);

        createStoreRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            if(object != null) {

                                Toast.makeText(AboutMeActivity.this, "Store created successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                };

                Response.ErrorListener errorListener = errorResponse -> {

                    Toast.makeText(AboutMeActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();

                };

                RegisterStoreRequest registerstoreRequest = new RegisterStoreRequest(
                        Integer.toString(getLoggedInAccount().id),
                        storeName.getText().toString(),
                        storeAddress.getText().toString(),
                        storePhone.getText().toString(),
                        listener,
                        errorListener);

                Store store = new Store(storeName.getText().toString(), storeAddress.getText().toString(), storePhone.getText().toString(), "0");
                loggedAccount.store = store;

                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(registerstoreRequest);

            }
        });

        cancelStoreRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storeCreateLayout.setVisibility(View.GONE);
                registerStore.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
        startActivity(intent);

    }
}