package com.AriqJmartFA.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.AriqJmartFA.R;
import com.AriqJmartFA.model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragmentss#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragmentss extends Fragment {

    /*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragmentss() {
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
    /*
    // TODO: Rename and change types and number of parameters
    public static ProductFragmentss newInstance(String param1, String param2) {
        ProductFragmentss fragment = new ProductFragmentss();
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


    }


    String accountId, name, weight, conditionUsed, price, discount, category, shipmentPlan;
    public static String JSON_URL = "http://localhost:8080/product/getProductList";
    ArrayList<String> productName = new ArrayList<String>();
    ArrayList<Product> productList = new ArrayList<Product>();

    JsonParser mGparser = new JsonParser();
    Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //Context context = container.getContext();
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);
        ListView lv = (ListView) root.findViewById(R.id.product_list);

//        GetData getData = new GetData();
//        getData.execute();


        try {

            URL url = new URL(JSON_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Connection", "close");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            JsonArray request = (JsonArray) mGparser.parse(in.readLine());
            in.close();
            productList = gson.fromJson(request, new TypeToken<ArrayList<Product>>() {}.getType());


        } catch (IOException e) {

            e.printStackTrace();

        }


        for(int i = 0; i < productList.size(); i++) {

            productName.add("name");

        }





        lv.setAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                productName

        ));


        return root;

    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText;
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    public class GetData extends AsyncTask<String, String, ArrayList<Product>> {

        @Override
        protected ArrayList<Product> doInBackground(String... strings) {


            URL url = null;
            try {

                url = new URL(JSON_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("Connection", "close");
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                JsonArray request = (JsonArray) mGparser.parse(in.readLine());
                in.close();
                productList = gson.fromJson(request, new TypeToken<ArrayList<Product>>() {}.getType());

                return productList;

            } catch (IOException e) {

                e.printStackTrace();

            }

            return null;

        }
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
                    productName.add("name");

                }
            } catch (JSONException e) {

                e.printStackTrace();

            }
        }
    }

     */

}