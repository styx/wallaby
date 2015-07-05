package by.styx.mp.wallaby;

import android.graphics.drawable.Drawable;

import java.util.Collection;
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

    public boolean hasProps(Collection<Integer> filterProps) {
        Set<Integer> tmp = new HashSet<>(filterProps);
        tmp.removeAll(props);
        return tmp.size() == 0;
    }

    public boolean hasExactProps(Collection<Integer> filterProps) {
        Set<Integer> tmp = new HashSet<>(filterProps);
        return tmp.equals(props);
    }
}
