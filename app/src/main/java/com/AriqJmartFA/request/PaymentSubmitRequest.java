package com.AriqJmartFA.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentSubmitRequest extends StringRequest {

    HashMap<String, String> params;

    public PaymentSubmitRequest(String id, String receipt, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Request.Method.POST, "http://10.0.2.2:8080/payment/" + id + "/submit", listener, errorListener);

        params = new HashMap<String, String>();
        params.put("receipt", receipt);

    }

    public Map<String, String> getParams() {

        return params;

    }
}
