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

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loadResources();

        ListView mFilterList = (ListView) view.findViewById(R.id.filterList);
        filterAdapter = new FilterArrayAdapter(getActivity(), filterItems);
        mFilterList.setAdapter(filterAdapter);

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

        ListView mFlagList = (ListView) view.findViewById(R.id.flagsList);
        flagsAdapter = new FlagAdapter(getActivity(), flagItems);
        mFlagList.setAdapter(flagsAdapter);

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
            filterItems.add(new FilterItem(filterNames.getString(i), filterImages.getDrawable(i), filterImages.getInt(i, -1)));
        }

        filterNames.recycle();
        filterImages.recycle();

        TypedArray flagImages = res.obtainTypedArray(R.array.flagImages);
        TypedArray flagNames = res.obtainTypedArray(R.array.flagNames);
        size = flagNames.length();

        for (int i = 0; i < size; i++) {
            try {
                field = res_cl.getField("flag_p" + String.valueOf(i));
                int[] flagProps = res.getIntArray(field.getInt(null));

                LogUtil.v(String.valueOf(field));
                List<Integer> intList = new ArrayList<>();
                for (int flagProp : flagProps) {
                    intList.add(flagProp);
                }

                flagItems.add(new FlagItem(flagNames.getString(i), flagImages.getDrawable(i), intList));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtil.v(String.valueOf(size));
        flagNames.recycle();
        flagImages.recycle();
    }

    private void resetFilter() {
        for (FilterItem filterItem : filterItems) {
            filterItem.setSelected(false);
        }
        filterAdapter.notifyDataSetChanged();
        flagsAdapter.notifyDataSetChanged();
    }
}