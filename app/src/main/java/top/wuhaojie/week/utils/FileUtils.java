package top.wuhaojie.week.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by wuhaojie on 2017/1/15 18:48.
 */

public class FileUtils {

    private FileUtils() {
    }

    public static boolean copyFile(File src, File des) {
        if (src == null || des == null) throw new NullPointerException("file is null");
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (IOException ignored) {
            }
        }

        return true;
    }


    public static <T> boolean exportToFile(File des, List<T> list, ItemStringCreator<T> creator) {
        if (des == null || list == null || list.isEmpty())
            throw new IllegalArgumentException("Des file or list is null");
        BufferedWriter bw = null;
        if (creator == null) creator = (position, t) -> t.toString();
        try {
            bw = new BufferedWriter(new FileWriter(des));
            for (int i = 0; i < list.size(); i++) {
                String line = creator.create(i, list.get(i));
                bw.write(line);
                bw.newLine();
                bw.flush();
            }


        } catch (IOException e) {
            Log.e(FileUtils.class.getSimpleName(), e.getMessage());
            return false;
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException ignored) {
            }
        }
        return true;
    }

    public interface ItemStringCreator<T> {
        String create(int position, T t);
    }

}
