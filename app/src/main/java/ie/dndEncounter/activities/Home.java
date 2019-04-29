package ie.dndEncounter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ie.dndEncounter.R;
import ie.dndEncounter.fragments.CreatureFrag;
import ie.dndEncounter.models.Creature;

public class Home extends Base {

    DatabaseReference Encounterhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);
        Encounterhelp = FirebaseDatabase.getInstance().getReference("Creatures");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Information", Snackbar.LENGTH_LONG)
                        .setAction("More Info...", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
       // if(app.creatureList.isEmpty()) setupCreatures();



Encounterhelp.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        app.creatureList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Creature creature = ds.getValue(Creature.class);
            app.creatureList.add(creature);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    public void add(View v) {
        startActivity(new Intent(this, Add.class));
    }

    public void search(View v) {
        startActivity(new Intent(this, Search.class));
    }

    public void favourites(View v) { startActivity(new Intent(this, Favourites.class)); }

    public void diceroller(View v) { startActivity(new Intent(this, diceroller.class)); }



    @Override
    protected void onResume() {
        super.onResume();

        creatureFrag = CreatureFrag.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, creatureFrag)
                .commit(); // add it to the current activity
    }


}

