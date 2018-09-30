package hall.allen.pyg.sourcefm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Song {


    private String id;
    private String name;
    private String singer;
    private int length; //length in minutes
    private int votes;
    public Song(String id) {
        this.id = id;
        resolve_stats(id);
    }

    private void resolve_stats(String id) {
        //TODO spotidy api here
        name = "songName";
        singer = "singer";
        length = 101;

        this.name = name;
        this.singer = singer;
        this.length = length;
        this.votes = 1;
    }

    public View getView(Context context) {
        LayoutInflater vi = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View song = vi.inflate(R.layout.song_item,null);
        ((TextView) song.findViewById(R.id.song_name)).setText(this.name);
        ((TextView) song.findViewById(R.id.song_singer)).setText(this.singer);
        ((TextView) song.findViewById(R.id.song_length)).setText(this.length/60 + ":" + this.length%60);
        ((TextView) song.findViewById(R.id.song_vote)).setText(Integer.toString(this.votes));
        song.findViewById(R.id.vote_button).setTag(id);
        return song;
    }

}
