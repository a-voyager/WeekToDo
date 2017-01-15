package top.wuhaojie.week.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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


}
