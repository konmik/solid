package info.android15.solidbenchmarks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.functions.Func1;
import solid.functions.SolidFunc1;
import solid.stream.Stream;

import static android.util.Log.v;
import static java.lang.String.format;

/*

=== Galaxy S1, 4.4.4

-------- 1 item --------
rx 16580 ms
solid 1895 ms
plain 718 ms
rx / plain = 23.09
solid / plain = 2.64
rx / solid = 8.75
-------- 10 items --------
rx 22602 ms
solid 4006 ms
plain 1788 ms
rx / plain = 12.64
solid / plain = 2.24
rx / solid = 5.64
-------- 100 items --------
rx 69458 ms
solid 21597 ms
plain 10644 ms
rx / plain = 6.53
solid / plain = 2.03
rx / solid = 3.22



=== Galaxy S3, 4.3

-------- 1 item --------
rx 7238 ms
solid 1102 ms
plain 545 ms
rx / plain = 13.28
solid / plain = 2.02
rx / solid = 6.57
-------- 10 items --------
rx 9747 ms
solid 1758 ms
plain 827 ms
rx / plain = 11.79
solid / plain = 2.13
rx / solid = 5.54
-------- 100 items --------
rx 27899 ms
solid 9657 ms
plain 5996 ms
rx / plain = 4.65
solid / plain = 1.61
rx / solid = 2.89



=== Galaxy S4, 5.0.1

-------- 1 item --------
rx 5092 ms
solid 591 ms
plain 126 ms
rx / plain = 40.41
solid / plain = 4.69
rx / solid = 8.62
-------- 10 items --------
rx 6156 ms
solid 888 ms
plain 316 ms
rx / plain = 19.48
solid / plain = 2.81
rx / solid = 6.93
-------- 100 items --------
rx 15190 ms
solid 3394 ms
plain 1700 ms
rx / plain = 8.94
solid / plain = 2.00
rx / solid = 4.48



=== A NOTE ABOUT THE IMPLEMENTATION ===

During the development of the library there was an idea to pass the stream size with an iterator to avoid allocations on a large data set.
While such implementation gives performance benefits for large data set, it works significantly slower on small data sets.
So, now we have Stream.<T>toList(initialCapacity) and Stream.<T>toSolidList(initialCapacity) operators
that allow to have great performance on large AND small data sets.

-- 1 item --
plain: 760 ms
size passed: 1150 ms

-- 10 items ---
plain: 1332 ms
size passed: 1760 ms

-- 100 items ---
plain: 9696 ms
size passed: 6759 ms

*/

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.log) TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        items(1, 100000);
        items(10, 100000);
        items(100, 100000);
    }

    private void log(String text) {
        log.setText((log.getText().length() == 0 ? "" : log.getText() + "\n") + text);
        v(TAG, text);
    }

    private void items(int items, int iterations) {
        log("-------- " + items + " item" + (items == 1 ? "" : "s") + " --------");
        List<Integer> source100 = new ArrayList<>();
        for (int i = 0; i < items; i++)
            source100.add(i);
        test(source100, iterations);
    }

    private void test(List<Integer> source, int iterations) {
        // Rx
        // warming up: cache, JIT, etc
        for (int i = 0; i < iterations / 5; i++)
            testRx(source);

        long time1 = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            testRx(source);
        long rxTotal = (System.nanoTime() - time1) / 1000000;
        log(format("rx %d ms", rxTotal));

        // Solid
        // warming up: cache, JIT, etc
        for (int i = 0; i < iterations / 5; i++)
            testSolid(source);

        long time2 = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            testSolid(source);
        long solidTotal = (System.nanoTime() - time2) / 1000000;
        log(format("solid %d ms", solidTotal));

        // Plain
        // warming up: cache, JIT, etc
        for (int i = 0; i < iterations / 5; i++)
            testPlain(source);

        long time3 = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            testPlain(source);
        long plainTotal = (System.nanoTime() - time3) / 1000000;
        log(format("plain %d ms", plainTotal));

        log(format("rx / plain = %.2f", (float)rxTotal / plainTotal));
        log(format("solid / plain = %.2f", (float)solidTotal / plainTotal));
        log(format("rx / solid = %.2f", (float)rxTotal / solidTotal));
    }

    private void testRx(List<Integer> source) {
        Observable.from(source)
            .skip(1)
            .map(new Func1<Integer, String>() {
                @Override
                public String call(Integer integer) {
                    return integer.toString();
                }
            })
            .toList()
            .toBlocking()
            .single();
    }

    private void testSolid(List<Integer> source) {
        Stream.stream(source)
            .skip(1)
            .map(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            })
            .toList(source.size() - 1);
    }

    private void testPlain(List<Integer> source) {
        int skip = 1;
        ArrayList<String> list = new ArrayList<>(source.size());
        for (Integer integer : source) {
            if (skip-- > 0)
                continue;
            list.add(integer.toString());
        }
    }
}
