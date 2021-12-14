package com.AriqJmartFA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.AriqJmartFA.MainActivity.productList;
import static com.AriqJmartFA.UserPaymentActivity.selectedPayment;
import static com.AriqJmartFA.model.Invoice.Status.ON_PROGRESS;

import com.AriqJmartFA.request.PaymentAcceptRequest;
import com.AriqJmartFA.request.PaymentCancelRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class PaymentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        setTitle("Transaction Details");
        TextView paymentBuyDate = findViewById(R.id.payment_buy_date);
        TextView paymentProductName = findViewById(R.id.payment_product_name);
        TextView paymentProductCount= findViewById(R.id.payment_product_count);
        TextView paymentShipmentAddress = findViewById(R.id.payment_ship_address);
        TextView paymentShipmentCost = findViewById(R.id.payment_ship_cost);
        TextView paymentShipmentPlan = findViewById(R.id.payment_ship_plan);
        TextView paymentStatus = findViewById(R.id.payment_status);
        TextView paymentStatusMSG = findViewById(R.id.payment_status_msg);
        TextView paymentStatusDate = findViewById(R.id.payemnt_status_lastdate);

        Button acceptButton = findViewById(R.id.payment_accept_button);
        Button cancelButton = findViewById(R.id.payment_cancel_button);
        Button waitSellerConf = findViewById(R.id.payment_seller_conf);
        Button productOTW = findViewById(R.id.payment_otw);

        paymentBuyDate.setText(selectedPayment.date.toString());


        for(int i = 0; i < productList.size(); i++) {
            if(productList.get(i).id == selectedPayment.productId) {

                paymentProductName.setText(productList.get(i).name);
                break;

            }
        }


        paymentProductCount.setText(Integer.toString(selectedPayment.productCount));
        paymentShipmentAddress.setText(selectedPayment.shipment.address);
        paymentShipmentCost.setText(Integer.toString(selectedPayment.shipment.cost));


        switch(selectedPayment.shipment.plan) {
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

        paymentStatus.setText(selectedPayment.latestHistory.status);
        paymentStatusMSG.setText(selectedPayment.latestHistory.message);
        paymentStatusDate.setText(selectedPayment.latestHistory.date);

        if(selectedPayment.latestHistory.status.equals("WAITING_CONFIRMATION")) {

            acceptButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);

        } else if(selectedPayment.latestHistory.status.equals("ON_PROGRESS")) {

            waitSellerConf.setVisibility(View.VISIBLE);

        } else if(selectedPayment.latestHistory.status.equals("ON_DELIVERY")) {

            productOTW.setVisibility(View.VISIBLE);

        } else {

            acceptButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);

        }

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {

                    Toast.makeText(PaymentDetailsActivity.this, "Product Processed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentDetailsActivity.this, UserPaymentActivity.class);
                    startActivity(intent);

                };

                Response.ErrorListener ErrorListener = errorResponse -> {

                    Toast.makeText(PaymentDetailsActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();

                };

                PaymentAcceptRequest paymentacceptRequest = new PaymentAcceptRequest(Integer.toString(selectedPayment.id), listener, ErrorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentDetailsActivity.this);
                requestQueue.add(paymentacceptRequest);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {

                    Toast.makeText(PaymentDetailsActivity.this, "Transacton Cancelled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentDetailsActivity.this, UserPaymentActivity.class);
                    startActivity(intent);

                };

                Response.ErrorListener ErrorListener = errorResponse -> {

                    Toast.makeText(PaymentDetailsActivity.this, "Unable to process", Toast.LENGTH_SHORT).show();

                };

                PaymentCancelRequest paymentcancelRequest = new PaymentCancelRequest(Integer.toString(selectedPayment.id), listener, ErrorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(PaymentDetailsActivity.this);
                requestQueue.add(paymentcancelRequest);

            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(PaymentDetailsActivity.this, UserPaymentActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}