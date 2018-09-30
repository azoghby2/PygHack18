package hall.allen.pyg.sourcefm;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity {

    Spotify spot;
    LinearLayout results;
    ArrayList<Song> lastResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lastResults = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        spot = new Spotify(this);
        results = findViewById(R.id.results);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {//runnable for live search results
            @Override
            public void run() {
                search();
                handler.postDelayed(this,100);
            }
        };
        handler.post(runnable);
    }

    /*    //TODO obeslete
    public void search(View view) {
        String query = ((EditText) findViewById(R.id.edit_query)).getText().toString();
        spot.search(query);
        ArrayList<Song> searchResults = spot.search(query);
        if (searchResults != null && !searchResults.isEmpty())
            displayResults(searchResults);
        else
            displaySearchError();

    }*/
    public void search() {
        String query = ((EditText) findViewById(R.id.edit_query)).getText().toString();
        ArrayList<Song> searchResults = spot.search(query);;
        if (searchResults != null && !searchResults.isEmpty() && !searchResults.equals(lastResults))
            displayResults(searchResults);
        //else
          //  displaySearchError();

    }

    public void displaySearchError() {
        Toast error = Toast.makeText(getApplicationContext(), "Error: song could not be found.\nPlease Try again.", Toast.LENGTH_LONG);
        error.show();

    }
    public void displayResults(ArrayList<Song> songResult) {
        results.removeAllViews();
        for (Song song: songResult) {
            View songView = song.getView(this);
            songView.findViewById(R.id.song_vote).setVisibility(View.INVISIBLE);
            Button butt = ((Button) songView.findViewById(R.id.vote_button));
            butt.setText("Submit");
            results.addView(song.getView(this));
        }
    }

    public void vote(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        spot.vote((String) view.getTag());

    }
}
