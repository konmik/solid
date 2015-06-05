package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

public class Pair<T1, T2> implements Parcelable {

    private final T1 value1;
    private final T2 value2;

    public Pair(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 value1() {
        return value1;
    }

    public T2 value2() {
        return value2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(value1);
        dest.writeValue(value2);
    }

    protected Pair(Parcel in) {
        ClassLoader classLoader = Pair.class.getClassLoader();
        //noinspection unchecked
        value1 = (T1)in.readValue(classLoader);
        //noinspection unchecked
        value2 = (T2)in.readValue(classLoader);
    }

    public static final Creator<Pair> CREATOR = new Creator<Pair>() {
        public Pair createFromParcel(Parcel in) {
            return new Pair(in);
        }

        public Pair[] newArray(int size) {
            return new Pair[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair)o;

        if (value1 != null ? !value1.equals(pair.value1) : pair.value1 != null) return false;
        if (value2 != null ? !value2.equals(pair.value2) : pair.value2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value1 != null ? value1.hashCode() : 0;
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        return result;
    }
}
