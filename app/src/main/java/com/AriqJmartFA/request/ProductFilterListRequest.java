package com.AriqJmartFA.request;

import androidx.annotation.Nullable;

import com.AriqJmartFA.model.Product;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFilterListRequest extends StringRequest {

    public static final String URL = "http://10.0.2.2:8080/product/getFiltered";
    Map<String, String> params;

    public ProductFilterListRequest(
            String page,
            String pageSize,
            String usedStatus,
            String search,
            String minPrice,
            String maxPrice,
            String category,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener) {

        super(Method.GET, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("usedStatus", usedStatus);
        params.put("search", search);
        params.put("minPrice", minPrice);
        params.put("maxPrice", maxPrice);
        params.put("category", category);

    }

    public Map<String, String> getParams() {

        return params;

    }
}
