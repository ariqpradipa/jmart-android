package com.AriqJmartFA.request;

import java.util.HashMap;
import java.util.Map;

import com.AriqJmartFA.model.ProductCategory;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import javax.xml.transform.ErrorListener;

public class CreateProductRequest extends StringRequest {

    public static String URL = "http://10.0.2.2:8080/product/create";
    Map<String, String> params;

    public CreateProductRequest(String accountId,
                         String name,
                         String weight,
                         String conditionUsed,
                         String price,
                         String discount,
                         String category,
                         String shipmentPlan,
                         Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", accountId);
        params.put("name", name);
        params.put("weight", weight);
        params.put("conditionUsed", conditionUsed);
        params.put("price", price);
        params.put("discount", discount);
        params.put("category", category);
        params.put("shipmentPlan", shipmentPlan);

    }

    public Map<String, String> getParams() {

        return params;

    }
}
