package by.styx.mp.wallaby;

import android.graphics.drawable.Drawable;

public class FilterItem {
    public final String text;
    public final Drawable image;
    private final Integer id;
    private boolean selected = false;

    public FilterItem(String text, Drawable image, Integer id) {
        this.id = id;
        this.text = text;
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }
}
