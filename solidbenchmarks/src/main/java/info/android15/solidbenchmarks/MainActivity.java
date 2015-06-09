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

=== Galaxy S3, 4.3

-------- 1 --------
rx 7406 ms
solid 966 ms
plain 419 ms
rx / solid = 7.67
solid / plain = 2.31
-------- 10 --------
rx 9463 ms
solid 1829 ms
plain 783 ms
rx / solid = 5.17
solid / plain = 2.34
-------- 100 --------
rx 26820 ms
solid 12422 ms
plain 5672 ms
rx / solid = 2.16
solid / plain = 2.19


=== Galaxy S4, 5.0.1

-------- 1 --------
rx 5285 ms
solid 541 ms
plain 124 ms
rx / solid = 9.77
solid / plain = 4.36
-------- 10 --------
rx 6457 ms
solid 1101 ms
plain 298 ms
rx / solid = 5.86
solid / plain = 3.69
-------- 100 --------
rx 14961 ms
solid 4576 ms
plain 1746 ms
rx / solid = 3.27
solid / plain = 2.62


=== Galaxy S5, 5.0.1

-------- 1 --------
rx 4204 ms
solid 467 ms
plain 117 ms
rx / solid = 9.00
solid / plain = 3.99
-------- 10 --------
rx 5004 ms
solid 723 ms
plain 225 ms
rx / solid = 6.92
solid / plain = 3.21
-------- 100 --------
rx 11925 ms
solid 3503 ms
plain 1387 ms
rx / solid = 3.40
solid / plain = 2.53

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
        log("-------- " + items + " --------");
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

        log(format("rx / solid = %.2f", (float)rxTotal / solidTotal));
        log(format("solid / plain = %.2f", (float)solidTotal / plainTotal));
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
            .toList();
    }

    private void testSolidPerformance(List<Integer> source) {
        Stream.stream(source)
            .skip(1)
            .map(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            })
            .toList();
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
