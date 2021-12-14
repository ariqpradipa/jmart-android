package com.AriqJmartFA;

import static com.AriqJmartFA.LoginActivity.getLoggedInAccount;
import static com.AriqJmartFA.MainActivity.getAllProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.AriqJmartFA.model.Payment;
import com.AriqJmartFA.model.Product;
import com.AriqJmartFA.model.Shipment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserPaymentActivity extends AppCompatActivity {


    private static final String PARENT_JSON_URL = "http://10.0.2.2:8080/payment/";
    private static String JSON_URL = PARENT_JSON_URL;
    ListView transactionView;
    SimpleAdapter adapterTransactionView;

    public static Payment selectedPayment;
    List<Product> allProduct = getAllProduct();

    ArrayList<String> paymentName = new ArrayList<String>();
    ArrayList<Payment> paymentList = new ArrayList<Payment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);

        transactionView = findViewById(R.id.orders_view);
        transactionView.setAdapter(adapterTransactionView);

        JSON_URL = PARENT_JSON_URL;
        JSON_URL = JSON_URL + getLoggedInAccount().id + "/paymentHistory";

        loadTransactionList();

        transactionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedPayment = paymentList.get(position);

                Intent pydetailsIntent = new Intent(UserPaymentActivity.this, PaymentDetailsActivity.class);
                startActivity(pydetailsIntent);


                //Toast.makeText(UserPaymentActivity.this, Integer.toString(selectedPayment.productId), Toast.LENGTH_SHORT).show();

            }
        });
    }

    String transactionProductName;
    List<HashMap<String, String>> mapList = new ArrayList<>();
    private void loadTransactionList() {

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        try {
                            //getting the whole json object from the response

                            //we have the array named tutorial inside the object
                            //so here we are getting that json array
                            JSONArray paymentArray = new JSONArray(response);

                            //now looping through all the elements of the json array
                            for (int i = 0; i < paymentArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject paymentObject = paymentArray.getJSONObject(i);

                                Payment.Record record = new Payment.Record(
                                        paymentObject.getJSONArray("history").getJSONObject(paymentObject.getJSONArray("history").length() - 1).getString("date"),
                                        paymentObject.getJSONArray("history").getJSONObject(paymentObject.getJSONArray("history").length() - 1).getString("message"),
                                        paymentObject.getJSONArray("history").getJSONObject(paymentObject.getJSONArray("history").length() - 1).getString("status")

                                );
                                //creating a tutorial object and giving them the values from json object
                                Payment payment = new Payment(
                                        paymentObject.getInt("id"),
                                        paymentObject.getString("date"),
                                        paymentObject.getInt("buyerId"),
                                        paymentObject.getInt("productId"),
                                        paymentObject.getInt("productCount"),
                                        new Shipment(
                                                paymentObject.getJSONObject("shipment").getString("address"),
                                                paymentObject.getJSONObject("shipment").getInt("cost"),
                                                (byte) paymentObject.getJSONObject("shipment").getInt("plan"),
                                                "none"
                                        ),
                                        record
                                );

                                for(int j = 0; j < allProduct.size(); j++) {
                                    if(allProduct.get(j).id == paymentObject.getInt("productId")) {

                                        transactionProductName = allProduct.get(j).name;
                                        break;

                                    }
                                }

                                HashMap<String, String> map = new HashMap<String, String>();

                                map.put("productName", transactionProductName);
                                map.put("transactionStatus", paymentObject.getJSONArray("history").getJSONObject(paymentObject.getJSONArray("history").length() - 1).getString("status"));
                                mapList.add(map);

                                paymentList.add(payment);
                            }

                            for(int i = 0; i< paymentList.size(); i++ ) {

                                paymentName.add(paymentList.get(i).toString());

                            }
                            //creating custom adapter object
                            adapterTransactionView = new SimpleAdapter(
                                    UserPaymentActivity.this,
                                    mapList,
                                    R.layout.transaction_layout,
                                    new String[] {"productName", "transactionStatus"},
                                    new int[] {R.id.productname_transaction, R.id.transactionstatus_transaction});
                            transactionView.setAdapter(adapterTransactionView);

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(UserPaymentActivity.this, "get error", Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(UserPaymentActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(UserPaymentActivity.this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}