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

import java.util.ArrayList;

public class Spotify {
    static String token = "BQAbzSxDBcQcTMJ0QcozriM1DrkcAMTpnhXwsA9CzcmD7Q6NRFU-N5LgVfOLVXoFNYSGBxEGgDjDVXCz9iChm99AUt5ckjIeGe7DMiQelo3lRH-keZC7ScOcM13RIoaIHUAWJTU-4JBDAscurQYX2MpvLKShf6ujRco6YqRk";
    protected static void vote(final Context context, String id) {

        RequestQueue queue = Volley.newRequestQueue(context);
        //String url = "http://requestbin.fullcontact.com/13b62j61?id="+id;
        String url = "http://10.194.211.136:2570/vote?id="+id;
        JSONObject resp;
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            //json2Songs(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    private static ArrayList<Song> json2Songs(JSONObject json) {
        ArrayList<Song> topSongs = new ArrayList<>();

        try {
            topSongs.add(new Song(json.getString()))
            json.getString("id")
            Log.d("JSON", json.getString("bitch"));
        }   catch (JSONException e) {
            Log.e("JSON", "json a bitch");
        }

    }
    protected static void search(final Context context, String name) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String cvtString = stringToGet(name);
        String url = "https://api.spotify.com/v1/search?q=" + cvtString + "&type=track";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            json2Songs(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    private static String stringToGet(String spaced) {
        return spaced.replaceAll(" ", "+");
    }
}
