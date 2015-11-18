package test_utils;

import android.util.SparseArray;

import org.mockito.stubbing.Answer;

import java.util.HashMap;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class MockSparseArray {

    private SparseArray<Object> mocked = mock(SparseArray.class);
    private HashMap<Integer, Object> map = new HashMap<>();

    public static void powerMockNew() throws Exception {
        whenNew(SparseArray.class).withAnyArguments().thenAnswer(invocation -> new MockSparseArray().getMockedSparseArray());
    }

    public MockSparseArray() {
        Answer<Void> writeValueAnswer = invocation -> {
            int key = (int) invocation.getArguments()[0];
            Object value = invocation.getArguments()[1];
            map.put(key, value);
            return null;
        };
        doAnswer(writeValueAnswer).when(mocked).put(anyInt(), anyObject());
        when(mocked.get(anyInt())).thenAnswer(invocation -> {
            int key = (int) invocation.getArguments()[0];
            return map.get(key);
        });
        when(mocked.size()).thenAnswer(invocation -> map.size());
    }

    private SparseArray getMockedSparseArray() {
        return mocked;
    }
}
