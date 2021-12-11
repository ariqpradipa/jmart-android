package com.AriqJmartFA;

import static com.AriqJmartFA.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.AriqJmartFA.model.ProductCategory;
import com.AriqJmartFA.request.CreateProductRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreateProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String conditionUsed;
    public String[] shipmentString = {"INSTANT", "SAME DAY", "NEXT DAY", "REGULER", "KARGO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        setTitle("Create Product");

        EditText productName = findViewById(R.id.product_name);
        EditText productWeight = findViewById(R.id.product_weight);
        EditText productPrice = findViewById(R.id.product_price);
        EditText productDiscount = findViewById(R.id.product_discount);
        RadioGroup radioCondition = findViewById(R.id.radio_condition);

        Spinner categorySpinner = findViewById(R.id.category_spinner);
        categorySpinner.setOnItemSelectedListener(this);
        Spinner shipmentplanSpinner = findViewById(R.id.shipmentplan_spinner);
        shipmentplanSpinner.setOnItemSelectedListener(this);

        Button createButton = findViewById(R.id.create_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        ArrayList<String> categoryList = new ArrayList<String>();
        for(ProductCategory category : ProductCategory.values()) {

            categoryList.add(category.toString());

        }

        ArrayAdapter productAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(productAdapter);

        ArrayAdapter shipmentAdapter =new ArrayAdapter(this, android.R.layout.simple_spinner_item, shipmentString);
        shipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shipmentplanSpinner.setAdapter(shipmentAdapter);

        radioCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioCondition, int checkedId) {

                int selectedID = radioCondition.getCheckedRadioButtonId();

                if(selectedID == R.id.radio_new) {

                    conditionUsed = "false";

                } else if(selectedID == R.id.radio_used) {

                    conditionUsed = "true";

                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null) {

                                Toast.makeText(CreateProductActivity.this, "Product has been added!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch(JSONException e) {

                            e.printStackTrace();

                        }
                    }
                };

                Response.ErrorListener errorListener = errorResponse -> {

                    Toast.makeText(CreateProductActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                };

                String shipment = shipmentplanSpinner.getSelectedItem().toString();
                String shipmentPlanByte;
                switch(shipment) {
                    case "INSTANT": {

                        shipmentPlanByte = "1";
                        break;

                    }
                    case "SAME DAY": {

                        shipmentPlanByte = "2";
                        break;

                    }
                    case "NEXT DAY": {

                        shipmentPlanByte = "4";
                        break;

                    }
                    case "REGULER": {

                        shipmentPlanByte = "8";
                        break;

                    }
                    case "KARGO": {

                        shipmentPlanByte = "16";
                        break;

                    }
                    default: {

                        shipmentPlanByte = "1";

                    }
                }

                CreateProductRequest createProduct = new CreateProductRequest(
                        Integer.toString(loggedAccount.id),
                        productName.getText().toString(),
                        productWeight.getText().toString(),
                        conditionUsed,
                        productPrice.getText().toString(),
                        productDiscount.getText().toString(),
                        categorySpinner.getSelectedItem().toString(),
                        shipmentPlanByte,
                        listener,
                        errorListener);

                RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
                requestQueue.add(createProduct);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateProductActivity.this, "Create product cancelled", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}