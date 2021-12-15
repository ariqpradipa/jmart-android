package com.AriqJmartFA;

import static com.AriqJmartFA.MainActivity.productList;
import static com.AriqJmartFA.StoreOrdersActivity.selectedOrders;
import static com.AriqJmartFA.UserPaymentActivity.selectedPayment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.AriqJmartFA.request.PaymentAcceptRequest;
import com.AriqJmartFA.request.PaymentRejectRequest;
import com.AriqJmartFA.request.PaymentSubmitRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class OrdersDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);

        setTitle("Order Details");

        TextView paymentBuyDate = findViewById(R.id.orders_buy_date);
        TextView paymentStoreName = findViewById(R.id.orders_store_name);
        TextView paymentProductName = findViewById(R.id.orders_product_name);
        TextView paymentProductCount= findViewById(R.id.orders_product_count);
        TextView paymentShipmentAddress = findViewById(R.id.orders_ship_address);
        TextView paymentShipmentCost = findViewById(R.id.orders_ship_cost);
        TextView paymentShipmentPlan = findViewById(R.id.orders_ship_plan);
        TextView paymentStatus = findViewById(R.id.orders_status);
        TextView paymentStatusMSG = findViewById(R.id.orders_status_msg);
        TextView paymentStatusDate = findViewById(R.id.orders_status_lastdate);

        Button submitButton = findViewById(R.id.orders_submit_button);
        Button rejectButton = findViewById(R.id.orders_reject_button);
        Button waitBuyerConf = findViewById(R.id.wait_buyer_acc);
        Button buyerCancel = findViewById(R.id.cancelled_by_buyer);
//        Button productOTW = findViewById(R.id.payment_otw);

        paymentBuyDate.setText(selectedOrders.date.toString());



        for(int i = 0; i < productList.size(); i++) {
            if(productList.get(i).id == selectedOrders.productId) {

                paymentStoreName.setText(productList.get(i).storeName);
                paymentProductName.setText(productList.get(i).name);
                break;

            }
        }


        paymentProductCount.setText(Integer.toString(selectedOrders.productCount));
        paymentShipmentAddress.setText(selectedOrders.shipment.address);
        paymentShipmentCost.setText(Integer.toString(selectedOrders.shipment.cost));

        switch(selectedOrders.shipment.plan) {
            case 1: {
                paymentShipmentPlan.setText("INSTANT");
                break;
            }
            case 2: {
                paymentShipmentPlan.setText("SAME DAY");
                break;
            }
            case 4: {
                paymentShipmentPlan.setText("NEXT DAY");
                break;

            }
            case 8: {
                paymentShipmentPlan.setText("REGULER");
                break;
            }
            case 16: {
                paymentShipmentPlan.setText("KARGO");
            }
        }

        paymentStatus.setText(selectedOrders.latestHistory.status);
        paymentStatusMSG.setText(selectedOrders.latestHistory.message);
        paymentStatusDate.setText(selectedOrders.latestHistory.date);



        if(selectedOrders.latestHistory.status.equals("ON_PROGRESS")) {

            submitButton.setVisibility(View.VISIBLE);
            rejectButton.setVisibility(View.VISIBLE);

        } else if(selectedOrders.latestHistory.status.equals("WAITING_CONFIRMATION")) {

            waitBuyerConf.setVisibility(View.VISIBLE);

        } else if(selectedOrders.latestHistory.status.equals("CANCELLED")) {

            buyerCancel.setVisibility(View.VISIBLE);

        } else {

            submitButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);

        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {

                    Toast.makeText(OrdersDetailsActivity.this, "Product Sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrdersDetailsActivity.this, StoreOrdersActivity.class);
                    startActivity(intent);

                };

                Response.ErrorListener ErrorListener = errorResponse -> {

                    Toast.makeText(OrdersDetailsActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();

                };

                PaymentSubmitRequest paymentsubmitRequest = new PaymentSubmitRequest(Integer.toString(selectedPayment.id), "lorem ipsum", listener, ErrorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(OrdersDetailsActivity.this);
                requestQueue.add(paymentsubmitRequest);

            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {

                    Toast.makeText(OrdersDetailsActivity.this, "Product Cancelleed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrdersDetailsActivity.this, StoreOrdersActivity.class);
                    startActivity(intent);

                };

                Response.ErrorListener ErrorListener = errorResponse -> {

                    Toast.makeText(OrdersDetailsActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();

                };

                PaymentRejectRequest paymentrejectRequest = new PaymentRejectRequest(Integer.toString(selectedOrders.id), listener, ErrorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(OrdersDetailsActivity.this);
                requestQueue.add(paymentrejectRequest);

            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(OrdersDetailsActivity.this, StoreOrdersActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}