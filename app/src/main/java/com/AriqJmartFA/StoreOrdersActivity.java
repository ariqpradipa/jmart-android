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

public class StoreOrdersActivity extends AppCompatActivity {

    private static final String PARENT_JSON_URL = "http://10.0.2.2:8080/payment/";
    private static String JSON_URL = PARENT_JSON_URL;

    ListView ordersView;
    SimpleAdapter adapterOrdersView;

    public static Payment selectedOrders;
    List<Product> allProduct = getAllProduct();

    ArrayList<String> ordersName = new ArrayList<String>();
    ArrayList<Payment> ordersList = new ArrayList<Payment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        ordersView = findViewById(R.id.orders_view);
        ordersView.setAdapter(adapterOrdersView);

        JSON_URL = PARENT_JSON_URL;
        JSON_URL = JSON_URL + getLoggedInAccount().id + "/ordersHistory";

        loadTransactionList();

        ordersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedOrders = ordersList.get(position);

                Intent ordetailsIntent = new Intent(StoreOrdersActivity.this, OrdersDetailsActivity.class);
                startActivity(ordetailsIntent);


                //Toast.makeText(UserPaymentActivity.this, Integer.toString(selectedPayment.productId), Toast.LENGTH_SHORT).show();

            }
        });

    }

    String transactionOrdersName;
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

                                // take payment from registered user id
                                for(int j = 0; j < allProduct.size(); j++) {
                                    if(allProduct.get(j).id == paymentObject.getInt("productId")) {
                                        if(allProduct.get(j).accountId == getLoggedInAccount().id) {

                                            transactionOrdersName = allProduct.get(j).name;
                                            break;

                                        }
                                    }
                                }

                                HashMap<String, String> map = new HashMap<String, String>();

                                map.put("productName", transactionOrdersName);
                                map.put("transactionStatus", paymentObject.getJSONArray("history").getJSONObject(paymentObject.getJSONArray("history").length() - 1).getString("status"));
                                mapList.add(map);

                                ordersList.add(payment);
                            }

                            for(int i = 0; i< ordersList.size(); i++ ) {

                                ordersName.add(ordersList.get(i).toString());

                            }
                            //creating custom adapter object
                            adapterOrdersView = new SimpleAdapter(
                                    StoreOrdersActivity.this,
                                    mapList,
                                    R.layout.transaction_layout,
                                    new String[] {"productName", "transactionStatus"},
                                    new int[] {R.id.productname_transaction, R.id.transactionstatus_transaction});
                            ordersView.setAdapter(adapterOrdersView);

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(StoreOrdersActivity.this, "get error", Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StoreOrdersActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(StoreOrdersActivity.this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}