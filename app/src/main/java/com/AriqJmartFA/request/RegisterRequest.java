package com.AriqJmartFA.request;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import javax.xml.transform.ErrorListener;

public class RegisterRequest extends StringRequest {

    public static String URL = "http://10.0.2.2:8080/account/register";
    Map<String, String> params;

    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);

    }

    public Map<String, String> getParams() {

        return params;

    }


}
