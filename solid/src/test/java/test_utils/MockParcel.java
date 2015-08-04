package test_utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockParcel {

    public static <T extends Parcelable> T writeRead(T parcelable, Parcelable.Creator<T> creator) {
        Parcel parcel = obtain();
        parcelable.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return creator.createFromParcel(parcel);
    }

    public static Parcel obtain() {
        return new MockParcel().getMockedParcel();
    }

    private Parcel mocked = mock(Parcel.class);
    private List<Object> objects = new ArrayList<>();
    private int position;

    public Parcel getMockedParcel() {
        return mocked;
    }

    public MockParcel() {
        setupWrites();
        setupReads();
        setupOthers();
    }

    private void setupWrites() {
        Answer<Void> writeValueAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object parameter = invocation.getArguments()[0];
                objects.add(parameter);
                return null;
            }
        };
        doAnswer(writeValueAnswer).when(mocked).writeLong(anyLong());
        doAnswer(writeValueAnswer).when(mocked).writeString(anyString());
        doAnswer(writeValueAnswer).when(mocked).writeList(anyList());
        doAnswer(writeValueAnswer).when(mocked).writeValue(any());
        doAnswer(writeValueAnswer).when(mocked).writeMap(anyMap());
    }

    private void setupReads() {
        Answer<Object> readValueAnswer = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return objects.get(position++);
            }
        };
        when(mocked.readLong()).thenAnswer(readValueAnswer);
        when(mocked.readString()).thenAnswer(readValueAnswer);
        when(mocked.readArrayList(any(ClassLoader.class))).thenAnswer(readValueAnswer);
        when(mocked.readValue(any(ClassLoader.class))).thenAnswer(readValueAnswer);
        when(mocked.readHashMap(any(ClassLoader.class))).thenAnswer(readValueAnswer);
    }

    private void setupOthers() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                position = ((Integer)invocation.getArguments()[0]);
                return null;
            }
        }).when(mocked).setDataPosition(0);
    }
}
