package hall.allen.pyg.sourcefm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);



        LayoutInflater vi = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View song = vi.inflate(R.layout.song_item,null);
        LinearLayout voteQueue = findViewById(R.id.vote_view);
        ((TextView) song.findViewById(R.id.song_name)).setText("Song");
        ((TextView) song.findViewById(R.id.song_singer)).setText("Singer");
        ((TextView) song.findViewById(R.id.song_length)).setText("0:00");
        ((TextView) song.findViewById(R.id.song_vote)).setText("121");
        voteQueue.addView(song);
    }


    protected void vote(View view) {

        Spotify.vote(this,"view");
    }
}
