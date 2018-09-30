package hall.allen.pyg.sourcefm;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {
    Spotify spot;
    LinearLayout voteQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spot = new Spotify(this);

        setContentView(R.layout.activity_vote);

        voteQueue = findViewById(R.id.vote_view);

        for (int i = 0; i < 20; i++){
            addSong(new Song());
        }
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
//        ColorDrawable background = (ColorDrawable) view.getBackground();
//        if (background.getColor() == getResources().getColor(R.color.colorAccent)) {
//            (Toast.makeText(this, "Already Voted", Toast.LENGTH_LONG)).show();
//        }else {
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            spot.vote((String) view.getTag());
       // }
    }

    protected void submit(View view) {
        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }
}
