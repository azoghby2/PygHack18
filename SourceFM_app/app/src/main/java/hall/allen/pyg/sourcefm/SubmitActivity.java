package hall.allen.pyg.sourcefm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity {

    Spotify spot;
    LinearLayout results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        spot = new Spotify(this);
        results = findViewById(R.id.results);
    }

    public void search(View view) {
        ((EditText) findViewById(R.id.edit_query)).getText();
        ArrayList<Song> searchResults = spot.search(((EditText) findViewById(R.id.edit_query)).getText().toString());
        if (searchResults != null && !searchResults.isEmpty())
            displayResults(searchResults);
        else
            displaySearchError();

    }

    public void displaySearchError() {
        Toast error = Toast.makeText(getApplicationContext(), "Error: Song could not be found.\nPlease Try again.", Toast.LENGTH_LONG);
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
