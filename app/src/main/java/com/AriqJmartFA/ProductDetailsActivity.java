package com.AriqJmartFA;

import static com.AriqJmartFA.LoginActivity.getLoggedInAccount;
import static com.AriqJmartFA.fragment.ProductFragment.selectedProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.AriqJmartFA.fragment.ProductFragment;

public class ProductDetailsActivity extends AppCompatActivity {

    double totalpriceBuy;
    int quantityBuy;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        TextView productDetailsName = findViewById(R.id.productdetails_name);
        TextView productDetailsPrice = findViewById(R.id.productdetails_price);
        TextView productDetailsDisc = findViewById(R.id.productdetails_discount);
        TextView productDetailsWeight = findViewById(R.id.productdetails_weight);
        TextView productDetailsCategory = findViewById(R.id.productdetails_category);
        TextView productDetailsCondition = findViewById(R.id.productdetails_condition);
        TextView productDetailsPlan = findViewById(R.id.productdetails_plan);
        TextView productDetailsStore = findViewById(R.id.productdetails_store);

        productDetailsName.setText(selectedProduct.name);
        productDetailsPrice.setText(Double.toString(selectedProduct.price));
        productDetailsDisc.setText(Double.toString(selectedProduct.discount));
        productDetailsWeight.setText(Integer.toString(selectedProduct.weight));
        productDetailsCategory.setText(selectedProduct.category);
        if(!selectedProduct.conditionUsed) {

            productDetailsCondition.setText("New");

        } else {

            productDetailsCondition.setText("Used");

        }

        switch(selectedProduct.shipmentPlan) {
            case 1: {
                productDetailsPlan.setText("INSTANT");
                break;
            }
            case 2: {
                productDetailsPlan.setText("SAME DAY");
                break;
            }
            case 4: {
                productDetailsPlan.setText("NEXT DAY");
                break;

            }
            case 8: {
                productDetailsPlan.setText("REGULER");
                break;
            }
            case 16: {
                productDetailsPlan.setText("KARGO");
            }
        }

        productDetailsStore.setText(selectedProduct.storeName);

        ImageButton reduceQuantity = findViewById(R.id.minquant_button);
        ImageButton increaseQuantity = findViewById(R.id.plusquant_button);
        EditText productQuantity = findViewById(R.id.productcount_buy);
        TextView totalpriceValue = findViewById(R.id.totalprice_buy);
        Button buyButton = findViewById(R.id.buy_button);

        quantityBuy = 0;
        totalpriceBuy = 0.0;
        totalpriceValue.setText(Double.toString(totalpriceBuy));

        reduceQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(productQuantity.getText().toString()) > 0) {

                    quantityBuy -= 1;
                    productQuantity.setText(Integer.toString(quantityBuy));
                    totalpriceBuy -= selectedProduct.price;

                    totalpriceValue.setText(Double.toString(totalpriceBuy));

                }
            }
        });

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantityBuy += 1;
                productQuantity.setText(Integer.toString(quantityBuy));
                totalpriceBuy += selectedProduct.price;

                totalpriceValue.setText(Double.toString(totalpriceBuy));

            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalpriceBuy > getLoggedInAccount().balance) {

                    Toast.makeText(ProductDetailsActivity.this, "Insufficient Balance!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ProductFragment.mapList.clear();
        //ProductFragment.lv.setAdapter(null);
        startActivity(intent);

    }
}