package com.AriqJmartFA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.AriqJmartFA.MainActivity.productList;
import static com.AriqJmartFA.UserPaymentActivity.selectedPayment;

public class PaymentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        TextView paymentBuyDate = findViewById(R.id.payment_buy_date);
        TextView paymentProductName = findViewById(R.id.payment_product_name);
        TextView paymentProductCount= findViewById(R.id.payment_product_count);
        TextView paymentShipmentAddress = findViewById(R.id.payment_ship_address);
        TextView paymentShipmentCost = findViewById(R.id.payment_ship_cost);
        TextView paymentShipmentPlan = findViewById(R.id.payment_ship_plan);
        TextView paymentStatus = findViewById(R.id.payment_status);
        TextView paymentStatusMSG = findViewById(R.id.payment_status_msg);
        TextView paymentStatusDate = findViewById(R.id.payemnt_status_lastdate);

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


        paymentStatus.setText(selectedPayment.history.get(2).status.toString());
        //paymentStatusMSG.setText(selectedPayment.history.get(selectedPayment.history.size() - 1).message);
        paymentStatusDate.setText(selectedPayment.history.get(2).date.toString());



    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(PaymentDetailsActivity.this, UserPaymentActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}