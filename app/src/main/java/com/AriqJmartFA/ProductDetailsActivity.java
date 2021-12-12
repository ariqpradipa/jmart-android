package com.AriqJmartFA;

import static com.AriqJmartFA.fragment.ProductFragment.selectedProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.AriqJmartFA.fragment.ProductFragment;

public class ProductDetailsActivity extends AppCompatActivity {

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