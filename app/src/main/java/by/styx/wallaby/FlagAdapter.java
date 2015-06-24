package by.styx.wallaby;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// The standard textView view adapter only seems to search from the beginning of whole words
// so we've had to write this whole class to make it possible to search
// for parts of the arbitrary string we want
public class FlagAdapter extends BaseAdapter implements Filterable {
    private List<FlagItem> originalData = null;
    private List<FlagItem> filteredData = null;
    private LayoutInflater mInflater;
    private FlagFilter mFilter = new FlagFilter();

    public FlagAdapter(Context context, List<FlagItem> data) {
        this.filteredData = data;
        this.originalData = data;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.flag, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.flagImage);
            holder.textView = (TextView) convertView.findViewById(R.id.flagText);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FlagItem filterItem = filteredData.get(position);
        holder.textView.setText(filterItem.text);
        holder.imageView.setImageDrawable(filterItem.image);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class FlagFilter extends Filter {
        private final TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(';');

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            splitter.setString(constraint.toString());
            List<Integer> selectedProps = new ArrayList<>();
            for (String s : splitter) {
                selectedProps.add(Integer.valueOf(s));
            }

            FilterResults results = new FilterResults();

            final List<FlagItem> list = originalData;
            int count = list.size();
            final ArrayList<FlagItem> nList = new ArrayList<>(count);

            FlagItem filterableFlag;

            for (int i = 0; i < count; i++) {
                filterableFlag = list.get(i);

                if (filterableFlag.hasProps(selectedProps)) {
                    nList.add(filterableFlag);
                }
            }

            results.values = nList;
            results.count = nList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<FlagItem>) results.values;
            notifyDataSetChanged();
        }

    }
}
