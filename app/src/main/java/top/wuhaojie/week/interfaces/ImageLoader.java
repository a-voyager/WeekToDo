package top.wuhaojie.week.interfaces;

import android.support.annotation.CallSuper;
import android.view.View;

/**
 * Created by wuhaojie on 17-2-7.
 */

public abstract class ImageLoader {

    private static final int FLAG = 0x01;
    private static ImageLoader mLoader;
    private int mOptions;

    public static void init(ImageLoader loader) {
        mLoader = loader;
    }

    public static ImageLoader get() {
        if (mLoader == null)
            throw new NullPointerException("call ImageLoader.init(...) before use it");
        return mLoader;
    }

    public static void shutdown() {
        if (mLoader != null) mLoader.close();
        mLoader = null;
    }

    @CallSuper
    protected void close() {
    }

    public abstract void load(String uri, View view);

    @CallSuper
    public void load(String uri, View view, int options) {
        this.mOptions = options;
    }


    public interface OPTIONS {
        int OPTION_CENTER_CROP = FLAG << 1;
        int OPTION_CIRCLE_CROP = FLAG << 2;
    }

    protected final boolean hasOption(int option) {
        // 0010 & 1101 == 0
        return (mOptions & (~option)) == 0;
    }

}
