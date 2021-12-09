package com.AriqJmartFA.fragment;

import static com.AriqJmartFA.LoginActivity.loggedAccount;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.AriqJmartFA.R;
import com.AriqJmartFA.model.Product;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        /*
        GetData getData = new GetData();
        getData.execute();

        ListView lv = (ListView)getActivity().findViewById(R.id.product_list);
        ArrayAdapter<String> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                productList);

        lv.setAdapter(adapter);

         */
    }


    //String accountId, name, weight, conditionUsed, price, discount, category, shipmentPlan;
    public static String PARENT_JSON_URL = "http://10.0.2.2:8080/product/getProductList";
    public static String JSON_URL = "http://10.0.2.2:8080/product/getProductList";
    public static String maxPageURL = "http://10.0.2.2:8080/product/getMaxPage";

    ArrayList<String> productName = new ArrayList<String>();
    ArrayList<Product> productList = new ArrayList<Product>();

    Integer pageNum = 1;
    Integer pageMax;

    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //Context context = container.getContext();
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);
        lv = (ListView) root.findViewById(R.id.product_list);

        loadProductList();
        Button nextButton = root.findViewById(R.id.next_button);
        Button prevButton = root.findViewById(R.id.prev_button);
        TextView pageNumber = root.findViewById(R.id.current_page);

        pageNumber.setText(pageNum.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, maxPageURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pageMax = Integer.parseInt(response);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pageNum < pageMax) {

                    pageNum++;

                }
                pageNumber.setText(pageNum.toString());
                JSON_URL = PARENT_JSON_URL;
                JSON_URL = JSON_URL + "?page=" + pageNum;
                mapList.clear();
                lv.setAdapter(null);
                loadProductList();

            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pageNum > 1) {
                    pageNum--;
                }
                pageNumber.setText(pageNum.toString());
                JSON_URL = PARENT_JSON_URL;
                JSON_URL = JSON_URL + "?page=" + pageNum;
                mapList.clear();
                lv.setAdapter(null);
                loadProductList();
            }
        });

        return root;

    }

    List<HashMap<String, String>> mapList = new ArrayList<>();
    private void loadProductList() {

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
                            JSONArray productArray = new JSONArray(response);

                            //now looping through all the elements of the json array
                            for (int i = 0; i < productArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject productObject = productArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                Product product = new Product(
                                        productObject.getInt("accountId"),
                                        productObject.getString("category"),
                                        productObject.getBoolean("conditionUsed"),
                                        productObject.getDouble("discount"),
                                        productObject.getString("name"),
                                        productObject.getDouble("price"),
                                        productObject.getInt("shipmentPlan"),
                                        productObject.getInt("weight"),
                                        productObject.getString("storeName"));
                                HashMap<String, String> map = new HashMap<String, String>();

                                map.put("name", product.name);
                                map.put("store", product.storeName);
                                mapList.add(map);

                                //adding the tutorial to tutoriallist
                                productList.add(product);
                            }

                            for(int i = 0; i< productList.size(); i++ ) {

                                productName.add(productList.get(i).toString());

                            }
                            //creating custom adapter object
                            lv.setAdapter(new SimpleAdapter(
                                    getActivity(),
                                    mapList,
                                    R.layout.row_layout,
                                    new String[] {"name", "store"},
                                    new int[] {R.id.productname_listview, R.id.storename_listview}));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



}