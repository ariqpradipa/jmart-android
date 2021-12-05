package com.AriqJmartFA.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.AriqJmartFA.MainActivity;
import com.AriqJmartFA.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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

        GetData getData = new GetData();
        getData.execute();

        /*

        ListView lv = (ListView)getActivity().findViewById(R.id.product_list);
        ArrayAdapter<String> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                productList);

        lv.setAdapter(adapter);

         */
    }


    String accountId, name, weight, conditionUsed, price, discount, category, shipmentPlan;
    private static String JSON_URL = "localhost:8080/product/getProductList";
    ArrayList<HashMap<String,String>> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Context context = container.getContext();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);

    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while(data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();

            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                JSONArray jsonArray = new JSONArray(s);

                for(int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    accountId = object.getString("accountId");
                    name = object.getString("name");
                    weight = object.getString("weight");
                    conditionUsed = object.getString("conditionUsed");
                    price = object.getString("price");
                    discount = object.getString("discount");
                    category = object.getString("category");
                    shipmentPlan = object.getString("shipmentPlan");

                    HashMap<String, String> products = new HashMap<String, String>();

                    products.put("accountId", accountId);
                    products.put("name", name);
                    products.put("weight", weight);
                    products.put("conditionUsed", conditionUsed);
                    products.put("price", price);
                    products.put("discount", discount);
                    products.put("category", category);
                    products.put("shipmentPlan", shipmentPlan);

                    productList.add(products);


                }
            } catch (JSONException e) {

                e.printStackTrace();

            }
        }
    }
}