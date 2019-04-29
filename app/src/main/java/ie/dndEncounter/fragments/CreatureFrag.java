package ie.dndEncounter.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import ie.dndEncounter.R;
import ie.dndEncounter.activities.Base;
import ie.dndEncounter.activities.Edit;
import ie.dndEncounter.activities.Favourites;
import ie.dndEncounter.adapters.FilterCreature;
import ie.dndEncounter.adapters.CListAdapter;
import ie.dndEncounter.models.Creature;

public class CreatureFrag extends ListFragment implements View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static CListAdapter listAdapter;
    public ListView listView;
    public FilterCreature filterCreature;

    public CreatureFrag() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("creatureId", (String) v.getTag());
        Intent goEdit = new Intent(getActivity(), Edit.class); // Creates a new Intent
        /* Add the bundle to the intent here */
        goEdit.putExtras(activityInfo);
        getActivity().startActivity(goEdit); // Launch the Intent
    }

    public static CreatureFrag newInstance() {
        CreatureFrag fragment = new CreatureFrag();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listAdapter = new CListAdapter(activity, this, activity.app.creatureList);
        filterCreature = new FilterCreature(activity.app.creatureList,"all",listAdapter);

        if (getActivity() instanceof Favourites) {
            filterCreature.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
            filterCreature.filter(null); // Filter the data, but don't use any prefix
            listAdapter.notifyDataSetChanged(); // Update the adapter
        }
        setListAdapter (listAdapter);
        setRandomCreature();
        checkEmptyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        listView = v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Creature)
        {
            onCreatureDelete ((Creature) view.getTag());
        }
    }

    public void onCreatureDelete(final Creature creature)
    {
        String stringName = creature.creatureName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Creature\' " + stringName + "?");
        builder.setCancelable(false);

        DatabaseReference EncounterhelpDEL = FirebaseDatabase.getInstance().getReference().child("Creatures");
        DatabaseReference EncounterhelpDELETE = EncounterhelpDEL.child(creature.creatureId);
        EncounterhelpDELETE.removeValue();

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.creatureList.remove(creature); // remove from our list
                listAdapter.creatureList.remove(creature); // update adapters data
                setRandomCreature();
                listAdapter.notifyDataSetChanged(); // refresh adapter
                checkEmptyList();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_creature:
                deleteCreature(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteCreature(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() -1 ; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.creatureList.remove(listAdapter.getItem(i));
                if (activity instanceof Favourites)
                   listAdapter.creatureList.remove(listAdapter.getItem(i));
            }
        }
        setRandomCreature();
        listAdapter.notifyDataSetChanged(); // refresh adapter
        checkEmptyList();

        actionMode.finish();
    }

    public void setRandomCreature() {

        ArrayList<Creature> creatureList = new ArrayList<>();

        for(Creature c : activity.app.creatureList)
            if (c.marked)
                creatureList.add(c);

        if (activity instanceof Favourites)
            if( !creatureList.isEmpty()) {
                Creature randomCreature = creatureList.get(new Random()
                            .nextInt(creatureList.size()));

                ((TextView) getActivity().findViewById(R.id.FavouriteCreatureName)).setText(randomCreature.creatureName);
                ((TextView) getActivity().findViewById(R.id.favouriteCreatureClass)).setText(randomCreature.classtype);
                ((TextView) getActivity().findViewById(R.id.favouriteCreatureArmor)).setText("AC " + randomCreature.armorClass);
                ((TextView) getActivity().findViewById(R.id.favouriteCreatureCR)).setText(randomCreature.challangerating + " *");
            }
            else {
                ((TextView) getActivity().findViewById(R.id.FavouriteCreatureName)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteCreatureClass)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteCreatureArmor)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteCreatureCR)).setText("N/A");
            }
    }

    public void checkEmptyList()
    {
        TextView recentList = getActivity().findViewById(R.id.emptyList);

        if(activity.app.creatureList.isEmpty())
            recentList.setText(getString(R.string.emptyMessageLbl));
        else
            recentList.setText("");
    }
    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {}
    /* ************ MultiChoiceModeListener methods (end) *********** */
}
