package top.wuhaojie.week.utils;

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

    public final void load(String uri, View view, int options) {
        this.mOptions = options;
        loadWithOptions(uri, view, options);
    }

    protected void loadWithOptions(String uri, View view, int options) {
        load(uri, view);
    }


    public static final int OPTION_CENTER_CROP = FLAG << 1;
    public static final int OPTION_CIRCLE_CROP = FLAG << 2;

    protected final boolean hasOption(int option) {
        // ( 0011 & 0001 ) ^ 0001 == 0 ok
        // ( 0011 & 1000 ) ^ 1000 != 0 not ok
        // 0 & 0 = 0 ; 00 ^ 10 != 0
        return ((mOptions & option) ^ option) == 0;
    }

}
