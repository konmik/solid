package solid.functions;

public class SolidConst {

    public static <T1, T2> SolidFunc2<T1, T2, Boolean> equalsComparator() {
        //noinspection unchecked
        return (SolidFunc2<T1, T2, Boolean>)equalsComparator;
    }

    public static <T> SolidAction1<T> noAction1() {
        //noinspection unchecked
        return (SolidAction1<T>)noAction1;
    }

    public static <T1, T2> SolidAction2<T1, T2> noAction2() {
        //noinspection unchecked
        return (SolidAction2<T1, T2>)noAction2;
    }

    private static SolidFunc2<Object, Object, Boolean> equalsComparator = new SolidFunc2<Object, Object, Boolean>() {
        @Override
        public Boolean call(Object value1, Object value2) {
            return value1 == null ? value2 == null : value1.equals(value2);
        }
    };

    private static SolidAction1<Object> noAction1 = new SolidAction1<Object>() {
        @Override
        public void call(Object value) {
        }
    };

    private static SolidAction2<Object, Object> noAction2 = new SolidAction2<Object, Object>() {
        @Override
        public void call(Object value1, Object value2) {
        }
    };
}
