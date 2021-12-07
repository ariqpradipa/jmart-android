package com.AriqJmartFA.request;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import javax.xml.transform.ErrorListener;

public class TopUpRequest extends StringRequest {

    public static String URL = "http://10.0.2.2:8080/account/%s/topUp";
    Map<String, String> params;

    public TopUpRequest(String id, String balance, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {

        super(Method.POST, String.format(URL, id), listener, errorListener);

        params = new HashMap<>();
        params.put("balance", balance);

    }

    public Map<String, String> getParams() {

        return params;

    }
}
