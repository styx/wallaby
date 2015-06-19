package by.styx.wallaby;

import android.graphics.drawable.Drawable;

public class FilterItem {
    public final String text;
    public final Drawable image;
    private boolean selected = false;

    public FilterItem(String text, Drawable image) {
        this.text = text;
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
