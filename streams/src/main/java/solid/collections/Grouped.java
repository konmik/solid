package solid.collections;

import java.util.List;

/**
 * Group - list pair.
 */
public class Grouped<G, T> {

    public final G group;
    public final List<T> list;

    public Grouped(G group, List<T> list) {
        this.group = group;
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grouped<?, ?> grouped = (Grouped<?, ?>) o;

        if (group != null ? !group.equals(grouped.group) : grouped.group != null) return false;
        return !(list != null ? !list.equals(grouped.list) : grouped.list != null);
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Grouped{" +
            "group=" + group +
            ", list=" + list +
            '}';
    }
}
