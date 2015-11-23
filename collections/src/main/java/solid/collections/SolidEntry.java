package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import solid.functions.Func1;

/**
 * This is a Parcelable implementation of {@link Map.Entry} which can be used by
 * {@link SolidMap} and {@link solid.stream.Stream#groupBy(Func1)}.
 */
public class SolidEntry<K, V> implements Map.Entry<K, V>, Parcelable {

    private final K key;
    private final V value;

    public SolidEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public SolidEntry(Map.Entry<K, V> entry) {
        this.key = entry.getKey();
        this.value = entry.getValue();
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Deprecated
    @Override
    public V setValue(V object) {
        throw new UnsupportedOperationException();
    }

    private static final ClassLoader CLASS_LOADER = SolidEntry.class.getClassLoader();

    protected SolidEntry(Parcel in) {
        key = (K) in.readValue(CLASS_LOADER);
        value = (V) in.readValue(CLASS_LOADER);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(key);
        dest.writeValue(value);
    }

    public static final Creator<SolidEntry> CREATOR = new Creator<SolidEntry>() {
        @Override
        public SolidEntry createFromParcel(Parcel in) {
            return new SolidEntry(in);
        }

        @Override
        public SolidEntry[] newArray(int size) {
            return new SolidEntry[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolidEntry<?, ?> that = (SolidEntry<?, ?>) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SolidEntry{" +
            "key=" + key +
            ", value=" + value +
            '}';
    }
}
