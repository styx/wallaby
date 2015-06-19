package by.styx.wallaby;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FilterArrayAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<FilterItem> values;

    public FilterArrayAdapter(Activity context, ArrayList<FilterItem> values) {
        super(context, R.layout.filter, values);
        this.context = context;
        this.values = values;
    }

    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.filter, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.filterText);
            holder.imageView = (ImageView) rowView.findViewById(R.id.filterImage);
            holder.checkBox = (CheckBox) rowView.findViewById(R.id.filterSelected);

            rowView.setTag(holder);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    FilterItem filterItem = (FilterItem) cb.getTag();
                    filterItem.setSelected(cb.isChecked());
                }
            });

        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        FilterItem filterItem = values.get(position);
        holder.textView.setText(filterItem.text);
        holder.imageView.setImageDrawable(filterItem.image);
        holder.checkBox.setChecked(filterItem.isSelected());
        holder.checkBox.setTag(filterItem);

        return rowView;
    }
}
