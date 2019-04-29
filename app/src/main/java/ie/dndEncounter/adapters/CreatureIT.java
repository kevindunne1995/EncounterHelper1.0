package ie.dndEncounter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ie.dndEncounter.R;
import ie.dndEncounter.models.Creature;

public class CreatureIT {
    View view;

    public CreatureIT(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, Creature creature)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.creaturerow, parent, false);
        view.setTag(creature.creatureId);

        updateControls(creature);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(creature);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Creature creature) {
        ((TextView) view.findViewById(R.id.rowCreatureName)).setText(creature.creatureName);

        ((TextView) view.findViewById(R.id.rowCreatureClass)).setText(creature.classtype);
        ((TextView) view.findViewById(R.id.rowCR)).setText(creature.challangerating + " *");
        ((TextView) view.findViewById(R.id.rowArmorClass)).setText(new DecimalFormat("0.00").format(creature.armorClass));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if (creature.marked == true)
            imgIcon.setImageResource(R.drawable.favourites_72_on);
        else
            imgIcon.setImageResource(R.drawable.favourites_72);


    }
}