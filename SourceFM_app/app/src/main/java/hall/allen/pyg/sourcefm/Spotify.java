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
    Song loadedSong;
    Context context;
    public Spotify(Context context) {
        this.context = context;
    }

    static String token = "BQDyfr99WvB019k9ZZxRMkXBkhiE2n9TvT4fHpHYynKI80G144UkDCQC0F_YOxySeBI8JV2nFCM0-hZOsyHTWUfaELkpSArd5jdCLfrtsq31RR7S-k04AQMr880U9HFQ1sN8fRjbAwv-uy9UZAYFTV5FWSKCrkHNmGVIdnoD";
    //works
    protected ArrayList<Song> vote(String id) {
        topSong = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        //String url = "http://requestbin.fullcontact.com/13b62j61?id="+id;
        String url = "http://10.194.211.136:2570/vote?id="+id;
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            topSong = topJson2Songs(response); //TODO check with server
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


        topSong.add(new Song());
        topSong.add(new Song());
        topSong.add(new Song());
        return topSong;
        //return topSong;
    }
    protected Song loadById(String id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.spotify.com/v1/tracks/" + id;
        final JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            try {
                                String id = response.getString("id");
                                String name = response.getString("name");
                                String singer = response.getJSONObject("artists").getJSONObject("0").getString("name");
                                int length = response.getInt("duration_ms");
                                int votes = 0;
                                loadedSong = new Song(id, name, singer, length, votes);
                            } catch (Exception e) {
                                Log.e("JSON", "failed to get response");
                                loadedSong = null;
                            }
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
        //return new Song();
        return loadedSong;
    }

    protected ArrayList<Song> search(String name) {
        songResults = new ArrayList<>();
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
        songResults.add(new Song());
        return songResults;
    }

    private static String stringToGet(String spaced) {
        return spaced.replaceAll(" ", "+");
    }

    //process json from our server TODO needs to check vote count
    private ArrayList<Song> topJson2Songs(JSONObject json) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            Song song;
            for (int i = 0; i < numSongs; i++) {
                String id = json.getString("id"+i);
                song = loadById(id);
                songs.add(song);
            }
            Log.d("JSON", "processed");
        }   catch (JSONException e) {
            Log.e("JSON", "json a bitch");
        }
        return songs;
    }

    //process json from spotify TODO all processing basically
    private ArrayList<Song> spotJson2Songs(JSONObject json) {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            Song song;
            for (int i = 0; i < numSongs; i++) {
                String id = json.getString("id"+i);
                song = loadById(id);
                songs.add(song);
            }
            Log.d("JSON", "processed");
        }   catch (JSONException e) {
            Log.e("JSON", "json a bitch");
        }
        return songs;
    }
}
