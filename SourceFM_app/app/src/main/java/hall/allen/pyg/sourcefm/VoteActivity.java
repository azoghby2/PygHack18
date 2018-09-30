package hall.allen.pyg.sourcefm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);




        LinearLayout voteQueue = findViewById(R.id.vote_view);

        Song song = new Song("ahfajkfhas8yas8i");
        voteQueue.addView(song.getView(this));
    }


    protected void vote(View view) {
        Object id = view.getTag();
        //Spotify.search(this, "alive");
        Spotify.vote(this,(String)view.getTag());
    }
}
