package solid.filters;

import java.util.ArrayList;

import solid.functions.SolidFunc1;

public class DistinctFilter<T> implements SolidFunc1<T, Boolean> {

    private ArrayList<T> approved = new ArrayList<>();

    @Override
    public Boolean call(T value) {
        if (approved.contains(value))
            return false;
        approved.add(value);
        return true;
    }
}
