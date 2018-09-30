package hall.allen.pyg.sourcefm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class VoteActivity extends AppCompatActivity {
    Spotify spot;
    LinearLayout voteQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spot = new Spotify(this);

        setContentView(R.layout.activity_vote);

        voteQueue = findViewById(R.id.vote_view);

        addSong("11dFghVXANMlKmJXsNCbNl");
        addSong("11dFghVXANMlKmJXsNCbNl");
        addSong("11dFghVXANMlKmJXsNCbNl");

    }

    protected void addSong(String id) {
        Song song = spot.loadById(id);
        voteQueue.addView(song.getView(this));
    }

    protected void addSong(Song song) {
        voteQueue.addView(song.getView(this));
    }

    protected void updateSongs() {
        spot.vote("0");
    }
    protected void vote(View view) {
        spot.vote((String)view.getTag());
        //spot.search("alive");
    }

    protected void submit(View view) {
        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }
}
