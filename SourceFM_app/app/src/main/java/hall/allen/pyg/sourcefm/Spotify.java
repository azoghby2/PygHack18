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

    static String token = "BQDjE7FQ8OOrX1YHQrvfj_OZdfhFDvYuXiiQb7z8tZTwoZcFNAFeOBF8so_caZ5zji9AJx5lGutijXfMF1lH6_YUGC-feIxsyelEpzmkxfQ8iUG6jekqvnPXUQCBT9sxwiNseYGSyBp5qRW8JR-F2bqGZKcdB-GoY-zTOniw";

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
                            topSong = topJson2Songs(response);
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
    //TODO fix, not getting an response
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
                                String id = "12312"; //response.getString("id");
                                String name = "adas";
                                String singer = "sss";
                                int length = 222;
                                int votes = 0;
                                loadedSong = new Song(id, name, singer, length, votes);
                            } catch (Exception e) {
                                Log.e("JSON", "failed to get response");
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
        return new Song();
        //return loadedSong;
    }

    //TODO same as loadById
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
        songResults.add(new Song());
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
                song = loadById(id);
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
