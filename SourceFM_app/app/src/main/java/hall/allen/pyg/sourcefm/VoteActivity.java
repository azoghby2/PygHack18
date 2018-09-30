package hall.allen.pyg.sourcefm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {
    Spotify spot;
    LinearLayout voteQueue;
    ArrayList<String> votedFor;
    int nullCOunter = 0;  //tracks for network disconnects
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        votedFor = new ArrayList<>();
        super.onCreate(savedInstanceState);

        spot = new Spotify(this);

        setContentView(R.layout.activity_vote);

        voteQueue = findViewById(R.id.vote_view);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                clearSongs();
                updateSongs();
                Log.d("ASYNC SONGS", "synced");
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }
    protected void addSong(String id) {
        Song song = spot.loadById(id);
        addSong(song);

    }

    protected void addSong(Song song) {
        View songView = song.getView(this);
        if (votedFor.contains(song.getID())) {
            songView.findViewById(R.id.vote_button).setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        voteQueue.addView(songView);
    }


    protected void updateSongs() {

        ArrayList<Song> topSongs = spot.vote("0");
        if (topSongs != null && !topSongs.isEmpty()) {
            for (Song song : topSongs) {
                addSong(song);
            }
            nullCOunter = 0;
        } else {
            nullCOunter++;
            if (nullCOunter > 3) {
                clearSongs();
                Toast voteFail = Toast.makeText(this, "Error: Network Failure", Toast.LENGTH_LONG);
                voteFail.show();
            }
        }
    }

    protected void clearSongs() {
        voteQueue.removeAllViews();
    }
    public void vote(View view) {
        String id = (String) view.getTag();

            if (votedFor.contains(id)) {
                Toast voteFail =  Toast.makeText(this, "Error: You already voted", Toast.LENGTH_LONG);
                voteFail.show();
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                spot.vote(id);
                Toast voteSucc = Toast.makeText(this, "Success: Vote submitted", Toast.LENGTH_SHORT);
                voteSucc.show();
                votedFor.add(id);
            }

    }

    protected void submit(View view) {
        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }


}
