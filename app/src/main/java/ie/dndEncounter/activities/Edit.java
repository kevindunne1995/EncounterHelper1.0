package ie.dndEncounter.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ie.dndEncounter.R;
import ie.dndEncounter.models.Creature;

public class Edit extends Base {
    public Context context;
    public boolean isFavourite;
    public Creature aCreature;
    public ImageView editFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        context = this;
        activityInfo = getIntent().getExtras();
        aCreature = getCreatureObject(activityInfo.getString("creatureId"));

        ((TextView)findViewById(R.id.editTitleTV)).setText(aCreature.creatureName);

        ((EditText)findViewById(R.id.editNameET)).setText(aCreature.creatureName);
        ((EditText)findViewById(R.id.editClassET)).setText(aCreature.classtype);
        ((EditText)findViewById(R.id.editArmorCET)).setText(""+ aCreature.armorClass);
        ((RatingBar) findViewById(R.id.EditCRatingBar)).setRating((float) aCreature.challangerating);

        editFavourite = findViewById(R.id.editFavourite);

        if (aCreature.marked == true) {
            editFavourite.setImageResource(R.drawable.favourites_72_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.favourites_72);
            isFavourite = false;
        }
    }

    private Creature getCreatureObject(String id) {

        for (Creature c : app.creatureList)
            if (c.creatureId.equalsIgnoreCase(id))
                return c;

        return null;
    }

    public void saveCreature(View v) {

        String creatureName = ((EditText) findViewById(R.id.editNameET)).getText().toString();
        String creatureClass = ((EditText) findViewById(R.id.editClassET)).getText().toString();
        String creatureArmor = ((EditText) findViewById(R.id.editArmorCET)).getText().toString();
        double ratingValue =((RatingBar) findViewById(R.id.EditCRatingBar)).getRating();
        double ClassCreature;

        try {
            ClassCreature = Double.parseDouble(creatureArmor);
        } catch (NumberFormatException e) {
            ClassCreature = 0.0;
        }

        if ((creatureName.length() > 0) && (creatureClass.length() > 0) && (creatureArmor.length() > 0)) {
            aCreature.creatureName = creatureName;
            aCreature.classtype = creatureClass;
            aCreature.armorClass = ClassCreature;
            aCreature.challangerating = ratingValue;

            DatabaseReference EncounterhelpEDIT = FirebaseDatabase.getInstance().getReference("Creatures").child(aCreature.creatureId);
            EncounterhelpEDIT.setValue(aCreature);

            startActivity(new Intent(this,Home.class));

        } else
            Toast.makeText(this, "You must Enter Something for Name and Shop",Toast.LENGTH_SHORT).show();
    }

    public void toggle(View view) {

        if (isFavourite) {
            aCreature.marked = false;
            Toast.makeText(this,"Removed From Favourites",Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.favourites_72);
        } else {
            aCreature.marked = true;
            Toast.makeText(this,"Added to Favourites !!",Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.favourites_72_on);
        }
    }
}
