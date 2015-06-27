package solid.converters;

import android.text.TextUtils;

import solid.functions.SolidFunc1;

/**
 * This class is a syntax enhancement around {@link TextUtils#join(CharSequence, Iterable)} method
 * to chain it with {@link solid.stream.Stream<String>} streams.
 */
public class ToJoinedString implements SolidFunc1<Iterable<String>, String> {

    private static ToJoinedString TO_JOINED_STRING = new ToJoinedString();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream of {@link String} type into a joined string.
     *
     * @return a method that converts an iterable stream of {@link String} type into a joined string.
     */
    public static ToJoinedString toJoinedString() {
        return TO_JOINED_STRING;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream of {@link String} type into a joined string.
     *
     * @param delimiter a delimiter
     * @return a method that converts an iterable stream of {@link String} type into a joined string.
     */

    public static ToJoinedString toJoinedString(String delimiter) {
        return new ToJoinedString(delimiter);
    }

    private String delimiter;

    public ToJoinedString() {
        this("");
    }

    public ToJoinedString(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String call(Iterable<String> value) {
        return TextUtils.join(delimiter, value);
    }
}
