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
        Button buyerCancell = findViewById(R.id.cancelled_by_buyer);
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
        paymentShipmentAddress.setText(selectedPayment.shipment.address);
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

        if(selectedPayment.latestHistory.status.equals("ON_PROGRESS")) {

            submitButton.setVisibility(View.VISIBLE);
            rejectButton.setVisibility(View.VISIBLE);

        } else if(selectedPayment.latestHistory.status.equals("WAITING_CONFIRMATION")) {

            waitBuyerConf.setVisibility(View.VISIBLE);

        } else if(selectedPayment.latestHistory.status.equals("CANCELLED")) {

            buyerCancell.setVisibility(View.VISIBLE);

        } else {

            submitButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);

        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(OrdersDetailsActivity.this, UserPaymentActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}