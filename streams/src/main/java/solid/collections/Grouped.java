package solid.collections;

import solid.stream.Stream;

/**
 * Group - list pair.
 */
public class Grouped<G, T> {

    public final G group;
    public final Stream<T> stream;

    public Grouped(G group, Stream<T> stream) {
        this.group = group;
        this.stream = stream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grouped<?, ?> grouped = (Grouped<?, ?>) o;

        if (group != null ? !group.equals(grouped.group) : grouped.group != null) return false;
        return !(stream != null ? !stream.equals(grouped.stream) : grouped.stream != null);
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (stream != null ? stream.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Grouped{" +
            "group=" + group +
            ", list=" + stream +
            '}';
    }
}
