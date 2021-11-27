package com.AriqJmartFA.request;

import android.location.GnssAntennaInfo;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.*;
import com.android.volley.toolbox.*;

public class LoginRequest extends StringRequest {

    String URL = "http://10.0.2.2:<port>/account/login";
    Map<String, String> params;

    LoginRequest(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);


    }

    public Map<String, String> getParams() {

        return params;

    }

}
