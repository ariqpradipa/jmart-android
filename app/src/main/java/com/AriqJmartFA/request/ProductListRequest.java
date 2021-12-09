package com.AriqJmartFA.request;

import androidx.annotation.Nullable;

import com.AriqJmartFA.model.Product;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductListRequest extends StringRequest {

    public static final String URL = "http://10.0.2.2:8080/product/getProductList";
    Map<String, String> params;

    public ProductListRequest(String page, String pageSize, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {

        super(Method.GET, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("page", page);
        params.put("pageSize", pageSize);

    }

    public Map<String, String> getParams() {

        return params;

    }
}
