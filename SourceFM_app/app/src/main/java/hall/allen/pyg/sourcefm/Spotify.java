package hall.allen.pyg.sourcefm;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Spotify {
    static String token = "BQDP8V1zGHRiktdcaMfD8k2TTRzTE-NlLIdh-Wk0LkCFDD_2eV1WFR9w6ahPVhq-BD8h5eVcB4lWrBf-L8vpQJLZ5jrS8f3nIxY_S4Mvmpp9li7S4IPqpEQy9qfkUQrsLh_CVHn9GQ_3jjw-0is_N-34Pla7BdNeJCC1AEybw6rGmbEsK-Mtyq0K";

    protected static void vote(final Context context, String id) {


        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://requestbin.fullcontact.com/13b62j61?id="+id;
        JSONObject resp;
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //if (null != response) {
                            Log.d("SUCCESS", "got response");
                        //}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

}
