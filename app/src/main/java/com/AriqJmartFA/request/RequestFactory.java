package com.AriqJmartFA.request;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

public class RequestFactory {

    public static String URL_FORMAT_ID = "http://10.0.2.2:<port>%s/%d";
    public static String URL_FORMAT_PAGE = "http://10.0.2.2:<port>%s/page";

    public static StringRequest getById(String paentURI, int id, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        String URL = String.format(URL_FORMAT_ID, paentURI, id);
        return new StringRequest(Request.Method.GET, URL, listener, errorListener);

    }

    public static StringRequest getPage(String parentURI, int page, int pageSize, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        String url = String.format(URL_FORMAT_PAGE, parentURI);
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));

        return new StringRequest(Request.Method.GET, url, listener, errorListener) {
            public Map<String, String> getParams() {
                return params;
            }
        };
    }
}
