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
import static java.util.Arrays.asList;

public class MainActivity extends Activity {

    public static final int ITERATIONS = 100000;
    public static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.rxJava) TextView rxJava;
    @InjectView(R.id.solid) TextView solid;
    @InjectView(R.id.plain) TextView plain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        List<Integer> source = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Solid
        // warming up: cache, JIT, etc
        v(TAG, "solid");

        for (int i = 0; i < ITERATIONS / 5; i++)
            testSolid(source);

        long time2 = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++)
            testSolid(source);
        solid.setText(format("%d ms", (System.nanoTime() - time2) / 1000000));

        // Rx
        // warming up: cache, JIT, etc
        v(TAG, "rx");

        for (int i = 0; i < ITERATIONS / 5; i++)
            testRx(source);

        long time1 = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++)
            testRx(source);
        rxJava.setText(format("%d ms", (System.nanoTime() - time1) / 1000000));

        // Plain
        // warming up: cache, JIT, etc
        v(TAG, "plain");

        for (int i = 0; i < ITERATIONS / 5; i++)
            testPlain(source);

        long time3 = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++)
            testPlain(source);
        plain.setText(format("%d ms", (System.nanoTime() - time3) / 1000000));
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
