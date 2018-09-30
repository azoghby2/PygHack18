package hall.allen.pyg.sourcefm;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Spotify {

    static int numSongs = 20;


    ArrayList<Song> topSong;
    ArrayList<Song> songResults;
    ArrayList<Song> loadedSong;
    Context context;
    public Spotify(Context context) {
        this.context = context;
    }

    static String token = "BQAEf9HDCnkRgLC3jChjSmt7pPUEpz_fhdF4htB01H9ZcidSz5JRUEg0nmk3Z828KMcE3QAgbJ_SVGJZIi_JCe2uzvQFwZNifbxxnhWX-g7OmkMdTMSXVgom0o7div6q8jAudsIcftbN0Vqdy7_QmvcuprI11gIQh6PTjjCS";
    protected ArrayList<Song> vote(String id) {

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
                            topSong = topJson2Songs(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return topSong;
    }

    protected Song loadById(String id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.spotify.com/v1/tracks/" + id;
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            loadedSong = spotJson2Songs(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer "+ token);
                return header;
            }
        };
        queue.add(request);
        if (!loadedSong.isEmpty()) {
            return loadedSong.get(0);
        } else {
            return null;
        }
    }

    protected ArrayList<Song> search(String name) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String cvtString = stringToGet(name);
        String url = "https://api.spotify.com/v1/search?q=" + cvtString + "&type=track";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            songResults = spotJson2Songs(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Authorization", "Bearer "+ token);
                return header;
            }
        };
        queue.add(request);
        return songResults;
    }

    private static String stringToGet(String spaced) {
        return spaced.replaceAll(" ", "+");
    }

    private ArrayList<Song> topJson2Songs(JSONObject json) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            Song song;
            for (int i = 0; i < numSongs; i++) {
                String id = json.getString("id"+i);
                song = new Song(id);
                songs.add(song);
            }
            Log.d("JSON", "processed");
        }   catch (JSONException e) {
            Log.e("JSON", "json a bitch");
        }
        return songs;
    }
    private ArrayList<Song> spotJson2Songs(JSONObject json) {
        ArrayList<Song> songs = new ArrayList<>();



        try {
            Song song;
            for (int i = 0; i < numSongs; i++) {
                String id = json.getString("id"+i);
                song = new Song(id);
                songs.add(song);
            }
            Log.d("JSON", "processed");
        }   catch (JSONException e) {
            Log.e("JSON", "json a bitch");
        }
        return songs;
    }
}
