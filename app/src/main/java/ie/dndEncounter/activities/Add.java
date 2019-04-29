package ie.dndEncounter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ie.dndEncounter.R;
import ie.dndEncounter.models.Creature;

public class Add extends Base {

    private String 		creatureName, creautreclasstype;
    private double 		creatureArmor, ratingValue;
    private EditText name, classtype, armorClass;
    private RatingBar challangerating;

    DatabaseReference Encounterhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        name = findViewById(R.id.addNameET);
        classtype =  findViewById(R.id.addClassET);
        armorClass =  findViewById(R.id.editArmorCET);
        challangerating =  findViewById(R.id.addCRatingBar);

        Encounterhelp = FirebaseDatabase.getInstance().getReference("Creatures");

    }

    public void addCreature(View v) {

        creatureName = name.getText().toString();
        creautreclasstype = classtype.getText().toString();
        try {
            creatureArmor = Double.parseDouble(armorClass.getText().toString());
        } catch (NumberFormatException e) {
            creatureArmor = 0.0;
        }
        ratingValue = challangerating.getRating();

        if ((creatureName.length() > 0) && (creautreclasstype.length() > 0)
                && (armorClass.length() > 0)) {
            String id = Encounterhelp.push().getKey();
            Creature c = new Creature(id, creatureName, creautreclasstype, ratingValue,
                    creatureArmor, false);

            Encounterhelp.child(id).setValue(c);


            app.creatureList.add(c);
            startActivity(new Intent(this, Home.class));
        } else
            Toast.makeText(
                    this,
                    "You must Enter Something for "
                            + "\'Name\', \'Class\' and \'Armor Class\'",
                    Toast.LENGTH_SHORT).show();


    }
}
