package com.AriqJmartFA.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PaymentCancelRequest extends StringRequest  {

    public PaymentCancelRequest(String id, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Request.Method.POST, "http://10.0.2.2:8080/payment/" + id + "/cancel", listener, errorListener);

    }
}
