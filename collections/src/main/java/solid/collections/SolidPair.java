package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is a duplicate of the {@link android.util.Pair} but implementing {@link Parcelable}.
 */
public class SolidPair<T1, T2> implements Parcelable {

    public final T1 first;
    public final T2 second;

    public SolidPair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    protected SolidPair(Parcel in) {
        first = (T1) in.readValue(SolidPair.class.getClassLoader());
        second = (T2) in.readValue(SolidPair.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(first);
        dest.writeValue(second);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SolidPair> CREATOR = new Creator<SolidPair>() {
        @Override
        public SolidPair createFromParcel(Parcel in) {
            return new SolidPair(in);
        }

        @Override
        public SolidPair[] newArray(int size) {
            return new SolidPair[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolidPair<?, ?> solidPair = (SolidPair<?, ?>) o;

        if (first != null ? !first.equals(solidPair.first) : solidPair.first != null) return false;
        return !(second != null ? !second.equals(solidPair.second) : solidPair.second != null);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SolidPair{" +
            "first=" + first +
            ", second=" + second +
            '}';
    }
}
