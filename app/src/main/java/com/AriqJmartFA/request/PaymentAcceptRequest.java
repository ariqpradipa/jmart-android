package com.AriqJmartFA.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PaymentAcceptRequest extends StringRequest  {


    public PaymentAcceptRequest(String id, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, "http://10.0.2.2:8080/payment/" + id + "/accept", listener, errorListener);

    }
}
