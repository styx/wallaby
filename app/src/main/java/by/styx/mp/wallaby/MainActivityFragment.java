package by.styx.mp.wallaby;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<FilterItem> filterItems = new ArrayList<>();
    private ArrayList<FlagItem> flagItems = new ArrayList<>();
    private FilterArrayAdapter filterAdapter;
    private FlagAdapter flagsAdapter;
    private TextView flagsCount;
    private CheckBox cbExactMatch;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loadResources();

        flagsCount = (TextView) view.findViewById(R.id.flagsCount);

        ListView mFilterList = (ListView) view.findViewById(R.id.filterList);
        filterAdapter = new FilterArrayAdapter(getActivity(), filterItems, MainActivityFragment.this);
        mFilterList.setAdapter(filterAdapter);

        mFilterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterItem filterItem = (FilterItem) parent.getItemAtPosition(position);
                filterItem.setSelected(!filterItem.isSelected());
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.filterSelected);
                checkBox.setChecked(filterItem.isSelected());
                doFilter();
            }
        });

        Button resetFilter = (Button) view.findViewById(R.id.resetFilter);
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilter();
            }
        });

        cbExactMatch = (CheckBox) view.findViewById(R.id.cbExactMatch);
        cbExactMatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doFilter();
            }
        });

        ListView mFlagList = (ListView) view.findViewById(R.id.flagsList);
        flagsAdapter = new FlagAdapter(getActivity(), flagItems);
        mFlagList.setAdapter(flagsAdapter);

        doFilter();

        return view;
    }

    private void loadResources() {
        Class<R.array> res_cl;
        Field field;

        res_cl = R.array.class;

        Resources res = getResources();
        TypedArray filterImages = res.obtainTypedArray(R.array.filterImages);
        TypedArray filterNames = res.obtainTypedArray(R.array.filterNames);

        int size = filterNames.length();

        for (int i = 0; i < size; i++) {
            filterItems.add(new FilterItem(filterNames.getString(i), filterImages.getDrawable(i), filterImages.getResourceId(i, -1)));
        }

        filterNames.recycle();
        filterImages.recycle();

        TypedArray flagImages = res.obtainTypedArray(R.array.flagImages);
        TypedArray flagNames = res.obtainTypedArray(R.array.flagNames);
        size = flagNames.length();

        for (int i = 0; i < size; i++) {
            try {
                field = res_cl.getField("flag_p" + String.valueOf(i));
                TypedArray flagProps = res.obtainTypedArray(field.getInt(null));

                List<Integer> intList = new ArrayList<>();
                int propSize = flagProps.length();

                for (int j = 0; j < propSize; j++) {
                    intList.add(flagProps.getResourceId(j, -1));
                }

                flagItems.add(new FlagItem(flagNames.getString(i), flagImages.getDrawable(i), intList));

                flagProps.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        flagNames.recycle();
        flagImages.recycle();
    }

    public void resetFilter() {
        for (FilterItem filterItem : filterItems) {
            filterItem.setSelected(false);
        }
        filterAdapter.notifyDataSetChanged();
        doFilter();
    }

    public void doFilter() {
        StringBuilder s = new StringBuilder(String.valueOf(cbExactMatch.isChecked()) + ";");
        for (FilterItem filterItem : filterItems) {
            if (filterItem.isSelected()) {
                s.append(filterItem.getId()).append(";");
            }
        }

        flagsAdapter.getFilter().filter(s, new Filter.FilterListener() {
            public void onFilterComplete(int count) {
                flagsCount.setText(String.valueOf(count));
            }
        });
    }
}