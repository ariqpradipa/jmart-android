package com.AriqJmartFA;

import static com.AriqJmartFA.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.AriqJmartFA.fragment.ProductFragment;
import com.AriqJmartFA.model.Product;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout mainTab;
    ViewPager2 pager2;
    FragmentAdapter adapter;

    SimpleAdapter adapterProductView;

    ListView productSearch;

    public static String JSON_URL = "http://10.0.2.2:8080/product/getAllProduct";

    ArrayList<String> productName = new ArrayList<String>();
    public static ArrayList<Product> productList = new ArrayList<Product>();

    public static List<Product> getAllProduct() {

        return productList;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("JMART");

        loadProductList();

        productSearch = findViewById(R.id.product_search_view);
        productSearch.setAdapter(adapterProductView);

        mainTab = findViewById(R.id.main_Tab);
        pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        mainTab.addTab(mainTab.newTab().setText("PRODUCT"));
        mainTab.addTab(mainTab.newTab().setText("FILTER"));


        mainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mainTab.selectTab(mainTab.getTabAt(position));
            }
        });

        productSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object selectedItem = parent.getItemAtPosition(position);
                for(int i = 0; i < productList.size(); i++) {

                    if(selectedItem.toString().contains(productList.get(i).name) && selectedItem.toString().contains(productList.get(i).storeName)) {

                        //Toast.makeText(getContext(), "Selected: " + productList.get(i).name + " " + productList.get(i).storeName, Toast.LENGTH_SHORT).show();
                        ProductFragment.selectedProduct = productList.get(i);
                        Intent prdetailsIntent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        startActivity(prdetailsIntent);

                    }
                }
            }
        });
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
                                        productObject.getInt("id"),
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
                            adapterProductView = new SimpleAdapter(
                                    MainActivity.this,
                                    mapList,
                                    R.layout.row_layout,
                                    new String[] {"name", "store"},
                                    new int[] {R.id.productname_listview, R.id.storename_listview});
                            productSearch.setAdapter(adapterProductView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        if(loggedAccount.store == null) {

            MenuItem itemProduct = menu.findItem(R.id.add_Product);
            MenuItem itemOrder = menu.findItem(R.id.store_order);
            if(itemProduct != null || itemOrder != null) {

                itemProduct.setVisible(false);
                itemOrder.setVisible(false);

            }
        }

        //ScrollView scrollView = findViewById(R.id.scroll_view_search);
        MenuItem itemSearch = menu.findItem(R.id.search_product);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                /*
                mainTab.setVisibility(View.INVISIBLE);
                pager2.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);

                if(list.contains(query)){
                    adapterScroll.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }

                 */

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainTab.setVisibility(View.INVISIBLE);
                pager2.setVisibility(View.INVISIBLE);
                productSearch.setVisibility(View.VISIBLE);
                adapterProductView.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mainTab.setVisibility(View.VISIBLE);
                pager2.setVisibility(View.VISIBLE);
                productSearch.setVisibility(View.GONE);
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_Product: {

                Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
                startActivity(intent);
                return true;

            }
            case R.id.about_me: {

                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                return true;

            }
            case R.id.buyer_payment_history: {

                Intent intent = new Intent(MainActivity.this, UserPaymentActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.store_order: {
                /*
                Intent intent = new Intent(MainActivity.this, UserPaymentActivity.class);
                startActivity(intent);
                return true;

                 */
            }
            default: {

                return super.onOptionsItemSelected(item);

            }
        }

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}