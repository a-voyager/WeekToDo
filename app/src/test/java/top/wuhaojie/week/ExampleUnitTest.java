package top.wuhaojie.week;

import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import top.wuhaojie.week.data.ImageFactory;
import top.wuhaojie.week.utils.DateUtils;
import top.wuhaojie.week.utils.FileUtils;

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
    public void testList() {
        List<String> bgImgs = ImageFactory.createBgImgs();
        System.out.println(bgImgs);
    }


    @Test
    public void testRandom() {

        int i = new Random(System.currentTimeMillis()).nextInt(8);
        System.out.println(i);
    }


    @Test
    public void testDate() {

        String s = DateFormat.getInstance().format(new Date());
        String format = new SimpleDateFormat("yyyy/mm/dd").format(new Date());
        System.out.println(format);

    }


    @Test
    public void testDateUtils() {
        DateUtils.getFirstSundayTimeMillisOfWeek();

    }


    @Test
    public void testNextDay() {
        for (int i = 1; i < 8; i++) {
            int dayOfWeek = DateUtils.calNextDayDayOfWeek(i);
            System.out.println(i + " : " + dayOfWeek);
        }
    }


    @Test
    public void testFileCopy() {
        File src = new File("D://整数硬盘分区工具.exe");
        File des = new File("D://整数硬盘分区工具_copy.exe");

        FileUtils.copyFile(src, des);

    }


}