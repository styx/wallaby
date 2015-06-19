package by.styx.wallaby;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView mFilterList;
    private ArrayList<FilterItem> filterItems = new ArrayList<>();
    private FilterArrayAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loadFilterItems();

        mFilterList = (ListView) view.findViewById(R.id.filterList);
        adapter = new FilterArrayAdapter(getActivity(), filterItems);
        mFilterList.setAdapter(adapter);

        mFilterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterItem filterItem = (FilterItem) parent.getItemAtPosition(position);
                filterItem.setSelected(!filterItem.isSelected());
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.filterSelected);
                checkBox.setChecked(filterItem.isSelected());
            }
        });

        Button resetFilter = (Button) view.findViewById(R.id.resetFilter);
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilter();
            }
        });

        return view;
    }

    private void loadFilterItems() {
        Resources res = getResources();
        TypedArray filterImages = res.obtainTypedArray(R.array.filterImages);
        TypedArray filterNames = res.obtainTypedArray(R.array.filterNames);

        int size = filterNames.length();

        for (int i = 0; i < size; i++) {
            filterItems.add(new FilterItem(filterNames.getString(i), filterImages.getDrawable(i)));
        }

        filterNames.recycle();
        filterImages.recycle();
    }

    private void resetFilter() {
        for (FilterItem filterItem : filterItems) {
            filterItem.setSelected(false);
        }
        adapter.notifyDataSetChanged();
    }
}
