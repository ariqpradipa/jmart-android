package com.AriqJmartFA.request;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.*;
import com.android.volley.toolbox.*;

public class LoginRequest extends StringRequest {

    public static String URL = "http://10.0.2.2:8080/account/login";
    Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);


    }

    public Map<String, String> getParams() {

        return params;

    }

}
