package hall.allen.pyg.sourcefm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity {

    Spotify spot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        spot = new Spotify(this);
    }

    public void search(View view) {
        ((EditText) findViewById(R.id.edit_query)).getText();
        ArrayList<Song> results = spot.search(((EditText) findViewById(R.id.edit_query)).getText().toString());
        //TODO visual these results beneeth search bar
        //give what to click on for submit
    }
}
