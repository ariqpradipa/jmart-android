package com.AriqJmartFA.request;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import javax.xml.transform.ErrorListener;

public class RegisterStoreRequest extends StringRequest {

    Map<String, String> params;

    public RegisterStoreRequest(String id, String name, String address, String phoneNumber, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        super(Method.POST, "http://10.0.2.2:8080/account/" + id + "/registerStore", listener, errorListener);

        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);

    }

    public Map<String, String> getParams() {

        return params;

    }
}
