package ie.dndEncounter.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import ie.dndEncounter.R;

public class SearchFrag extends CreatureFrag
        implements AdapterView.OnItemSelectedListener {

    String selected;
    SearchView searchView;

    public SearchFrag() {
        // Required empty public constructor
    }

    public static SearchFrag newInstance() {
        SearchFrag fragment = new SearchFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(activity, R.array.CreatureTypes,
                        android.R.layout.simple_spinner_item);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = activity.findViewById(R.id.searchSpinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        searchView = activity.findViewById(R.id.searchView);
        searchView.setQueryHint("Search for your Creatures Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCreature.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCreature.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context c) { super.onAttach(c); }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void checkSelected(String selected)
    {
        if (selected != null) {
            if (selected.equals("All Types")) {
                filterCreature.setFilter("all");
            } else if (selected.equals("Favourites")) {
                filterCreature.setFilter("favourites");
            }

            String filterText = ((SearchView)activity
                    .findViewById(R.id.searchView)).getQuery().toString();

            if(filterText.length() > 0)
                filterCreature.filter(filterText);
            else
                filterCreature.filter("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        checkSelected(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void deleteCreature(ActionMode actionMode) {
        super.deleteCreature(actionMode);
        checkSelected(selected);
    }

}
