package com.AriqJmartFA;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OrderDetailsActivity extends AppCompatActivity {

    ListView ordersView;
    SimpleAdapter adapterOrdersView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ordersView = findViewById(R.id.orders_view);
        ordersView.setAdapter(adapterOrdersView);


    }
}