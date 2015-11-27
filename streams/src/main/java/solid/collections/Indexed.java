package solid.collections;

/**
 * Index - value pair.
 */
public class Indexed<T> {

    public final int index;
    public final T value;

    public Indexed(int index, T value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Indexed<?> indexed = (Indexed<?>) o;

        if (index != indexed.index) return false;
        return !(value != null ? !value.equals(indexed.value) : indexed.value != null);
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + index;
        return result;
    }

    @Override
    public String toString() {
        return "Indexed{" +
            "value=" + value +
            ", index=" + index +
            '}';
    }
}
