package top.wuhaojie.week;

import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import top.wuhaojie.week.utils.DateUtils;
import top.wuhaojie.week.utils.FileUtils;

/**
 * Created by wuhaojie on 2017/1/17 14:28.
 */

public class ExportFileTest {

    @Test
    public void testCheckNull() {
        Object file = new File("C://");
        String name = file.getClass().getSimpleName();
        System.out.println(name);
    }

    static class Student {
        long id;
        String name;

        public Student(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }


    @Test
    public void testExportFile() {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(new Student(i, "张三"));
            list.add(new Student(i, "李四"));
            list.add(new Student(i, "王五"));
        }
        FileUtils.ItemStringCreator<Student> itemStringCreator = (position, student) -> "第" + (position + 1) + "条记录\n" + student.id + "\t" + student.name + "\n";
        boolean b = FileUtils.exportToFile(new File("G://test.txt"), list, itemStringCreator);
        System.out.println(b);

    }

    @Test
    public void dateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        String format = dateFormat.format(date);
        System.out.println(format);
        date.setTime(System.currentTimeMillis());
        format = dateFormat.format(date);
        System.out.println(format);
    }


    @Test
    public void date() {
        String s = DateUtils.formatDateTime(System.currentTimeMillis());
        System.out.println(s);
    }


    @Test
    public void week() {
        System.out.println(DateUtils.weekNumberToChinese(Calendar.MONDAY));
    }


}
