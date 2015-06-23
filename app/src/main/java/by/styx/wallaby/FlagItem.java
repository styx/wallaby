package by.styx.wallaby;

import android.graphics.drawable.Drawable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlagItem {
    public final String text;
    public final Drawable image;
    private final Set<Integer> props;

    public FlagItem(String text, Drawable image, List<Integer> props) {
        this.text = text;
        this.image = image;
        this.props = new HashSet<>(props);
    }

    public boolean hasProp(Integer prop) {
        return props.contains(prop);
    }
}
