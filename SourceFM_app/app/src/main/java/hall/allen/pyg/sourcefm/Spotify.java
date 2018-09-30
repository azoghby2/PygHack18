package hall.allen.pyg.sourcefm;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Spotify {

    static int numSongs = 20;


    ArrayList<Song> topSong;
    ArrayList<Song> songResults;
    Song loadedSong;
    Context context;
    public Spotify(Context context) {
        this.context = context;
        songResults = new ArrayList<>();
    }

    static String token = "BQC-2xrvFDtZpTbfFz5BlUZObayu4aENXLJfRL3WJhoWBaMAgByLPi4W634htq0YLwYfCZ2Minc1oIUCm7BjWNRMcm-9GuaaBMBjqgmx1m4-WXuauWR869on089OPi7is0rHGENZ-fTWfteLg-SMlWJIDRCj31E-BHVhONvL";
    //works
    protected ArrayList<Song> vote(String id) {
        topSong = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        //String url = "http://requestbin.fullcontact.com/13b62j61?id="+id;
        String url = "http://10.194.211.136:5000/vote?id="+id;
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (null != response) {
                            Log.d("JSON", "got response");
                            //topSong = topJson2Songs(response); //TODO check with server
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

        //todo remove this with actually server working
//        Song testSong = loadById("11dFghVXANMlKmJXsNCbNl");
//        if (testSong!= null)
//            topSong.add(testSong);

        Song newSOng = loadById("7GhIk7Il098yCjg4BQjzvb");
        if (newSOng!= null)
            topSong.add(newSOng);
        //topSong.add(new Song());
        //topSong.add(new Song());
        return topSong;
        //return topSong;
    }
    //TODO first time it runs it doesnt get a response
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
                                JSONArray artist = response.getJSONArray("artists");
                                JSONObject o = artist.getJSONObject(0);
                                String singer = o.getString("name");
                                int length = response.getInt("duration_ms")/1000;
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
        if (name.isEmpty()) {
            songResults = null;
        }
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

    //process json from our server TODO test with server
    private ArrayList<Song> topJson2Songs(JSONObject json) {
        ArrayList<Song> songs = new ArrayList<>();

        Iterator keys = json.keys();
        try {
            while (keys.hasNext()) {
                String id = keys.next().toString();
                Song song = loadById(id);
                song.setVotes(json.getInt(id));
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
            JSONObject tracks = json.getJSONObject("tracks");
            JSONArray items = json.getJSONArray("items");
            int count = tracks.getInt("total");
            for (int i = 0; i < count; i++) {
                JSONObject resu = items.getJSONObject(i);
                Song newSong = json2song(resu);
                songs.add(newSong);
            }
        } catch (JSONException e) {
            Log.e("JSON", "json a bitch");
        }

        return songs;
    }

    private static Song json2song(JSONObject response) {
        Song conSong;
        try {
            String id = response.getString("id");
            String name = response.getString("name");
            JSONArray artist = response.getJSONArray("artists");
            JSONObject o = artist.getJSONObject(0);
            String singer = o.getString("name");
            int length = response.getInt("duration_ms")/1000;
            int votes = 0;
            conSong = new Song(id, name, singer, length, votes);
        } catch (Exception e) {
            Log.e("JSON", "failed to get response");
            conSong = null;
        }
        return conSong;
    }
}
