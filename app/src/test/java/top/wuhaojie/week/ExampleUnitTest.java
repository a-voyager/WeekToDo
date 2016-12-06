package top.wuhaojie.week;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import top.wuhaojie.week.data.ImageFactory;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testList(){
        List<String> bgImgs = ImageFactory.createBgImgs();
        System.out.println(bgImgs);
    }


    @Test
    public void testRandom(){

        int i = new Random(System.currentTimeMillis()).nextInt(8);
        System.out.println(i);
    }


    @Test
    public void testDate(){

        String s = DateFormat.getInstance().format(new Date());
        String format = new SimpleDateFormat("yyyy/mm/dd").format(new Date());
        System.out.println(format);

    }
    
}