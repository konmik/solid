package solid.converters;

import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import solid.stream.Empty;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;
import static solid.converters.ToJoinedString.toJoinedString;
import static solid.stream.Stream.stream;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, ToJoinedString.class})
public class ToJoinedStringTest {

    public static final String RESULT_STRING = "result";

    @Test
    public void testToJoinedString() throws Exception {
        PowerMockito.mockStatic(TextUtils.class);
        when(TextUtils.join(anyString(), any(Iterable.class))).thenReturn(RESULT_STRING);

        assertEquals(RESULT_STRING, stream(asList("1", "2", "3")).collect(toJoinedString(",")));
        assertEquals(RESULT_STRING, stream(asList("1", "2", "3")).collect(toJoinedString()));
        assertEquals(RESULT_STRING, stream(asList("1", "2", "3")).collect(new ToJoinedString()));
        assertEquals(RESULT_STRING, stream(asList("1", "2", "3")).collect(new ToJoinedString(",")));
        assertEquals(RESULT_STRING, stream(new Empty<String>()).collect(new ToJoinedString(",")));
    }
}
