package ie.dndEncounter.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.dndEncounter.R;
import ie.dndEncounter.models.Creature;

public class CListAdapter extends ArrayAdapter<Creature>
{
    private Context context;
    private View.OnClickListener deleteListener;
    public List<Creature> creatureList;

    public CListAdapter(Context context, View.OnClickListener deleteListener, List<Creature> creatureList)
    {
        super(context, R.layout.creaturerow, creatureList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.creatureList = creatureList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CreatureIT item = new CreatureIT(context, parent,
                                         deleteListener, creatureList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return creatureList.size();
    }

    @Override
    public Creature getItem(int position) {
        return creatureList.get(position);
    }
}
