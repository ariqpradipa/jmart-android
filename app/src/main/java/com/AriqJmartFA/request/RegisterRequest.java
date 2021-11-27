package com.AriqJmartFA.request;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.*;
import com.android.volley.toolbox.*;

import javax.xml.transform.ErrorListener;

public class RegisterRequest extends StringRequest {

    String URL = "http://10.0.2.2:<port>/account/register";
    Map<String, String> params;

    RegisterRequest(String name, String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);

    }

    Map<String, String> getParmas() {

        return params;

    }


}
