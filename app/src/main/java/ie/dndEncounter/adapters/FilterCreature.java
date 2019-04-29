package ie.dndEncounter.adapters;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ie.dndEncounter.models.Creature;

public class FilterCreature extends Filter {
	public List<Creature> originalCreatureList;
	public String filterText;
	public CListAdapter adapter;

	public FilterCreature(List<Creature> originalCreatureList, String filterText,
						  CListAdapter adapter) {
		super();
		this.originalCreatureList = originalCreatureList;
		this.filterText = filterText;
		this.adapter = adapter;
	}

	public void setFilter(String filterText) {
		this.filterText = filterText;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();

		List<Creature> newCreatures;
		String creaturesName;

		if (prefix == null || prefix.length() == 0) {
			newCreatures = new ArrayList<>();
			if (filterText.equals("all")) {
				results.values = originalCreatureList;
				results.count = originalCreatureList.size();
			} else {
				if (filterText.equals("favourites")) {
					for (Creature c : originalCreatureList)
						if (c.marked)
							newCreatures.add(c);
					}
					results.values = newCreatures;
					results.count = newCreatures.size();
			}
		} else {
			String prefixString = prefix.toString().toLowerCase();
			newCreatures = new ArrayList<>();

			for (Creature c : originalCreatureList) {
				creaturesName = c.creatureName.toLowerCase();
				if (creaturesName.contains(prefixString)) {
					if (filterText.equals("all")) {
						newCreatures.add(c);
					} else if (c.marked) {
						newCreatures.add(c);
					}}}
			results.values = newCreatures;
			results.count = newCreatures.size();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence prefix, FilterResults results) {

		adapter.creatureList = (ArrayList<Creature>) results.values;

		if (results.count >= 0)
			adapter.notifyDataSetChanged();
		else {
			adapter.notifyDataSetInvalidated();
			adapter.creatureList = originalCreatureList;
		}
		Log.v("encounterhelper", "publishResults : " + adapter.creatureList);
	}
}
