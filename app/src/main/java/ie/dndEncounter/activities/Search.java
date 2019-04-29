package ie.dndEncounter.activities;

import android.os.Bundle;

import ie.dndEncounter.R;
import ie.dndEncounter.fragments.SearchFrag;

public class Search extends Base  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }

    @Override
    protected void onResume() {
        super.onResume();

        creatureFrag = SearchFrag.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, creatureFrag)
                .commit(); // add it to the current activity
    }
}
