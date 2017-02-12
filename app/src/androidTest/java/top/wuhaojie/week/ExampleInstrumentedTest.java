package top.wuhaojie.week;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("top.wuhaojie.week", appContext.getPackageName());
    }

    @Test
    public void testPath(){
        String name = InstrumentationRegistry.getTargetContext().getPackageName();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
//        if (!file.exists()) file.mkdirs();
    }


    @Test
    public void fresco(){
//        Fresco.initialize(InstrumentationRegistry.getTargetContext());
//        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(InstrumentationRegistry.getActivity());
//        View v = simpleDraweeView;
//        View v2 = new View(InstrumentationRegistry.getTargetContext());
//        boolean assignableFrom = SimpleDraweeView.class.isAssignableFrom(v.getClass());
//        boolean assignableFrom2 = SimpleDraweeView.class.isAssignableFrom(v2.getClass());
//        System.out.println(assignableFrom);
//        System.out.println(assignableFrom2);
    }

}
