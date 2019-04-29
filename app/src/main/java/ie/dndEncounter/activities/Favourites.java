package ie.dndEncounter.activities;

import android.os.Bundle;
import android.widget.TextView;
import ie.dndEncounter.R;
import ie.dndEncounter.fragments.CreatureFrag;

public class Favourites extends Base {

    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        emptyList = findViewById(R.id.emptyList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(app.creatureList.isEmpty())
            emptyList.setText(getString(R.string.emptyMessageLbl));
        else
            emptyList.setText("");

        creatureFrag = CreatureFrag.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, creatureFrag)
                .commit(); // add it to the current activity
    }
}
