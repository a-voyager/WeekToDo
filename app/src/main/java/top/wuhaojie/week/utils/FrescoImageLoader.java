package top.wuhaojie.week.utils;

import android.content.Context;
import android.view.View;

/**
 * Created by wuhaojie on 17-2-7.
 */
@Deprecated
public class FrescoImageLoader extends ImageLoader {

    private FrescoImageLoader(Context context) {
//        Fresco.initialize(context);
    }

    public static FrescoImageLoader create(Context context) {
        return new FrescoImageLoader(context);
    }

    @Override
    public void load(String uri, View view) {
//        Preconditions.checkArgument(!TextUtils.isEmpty(uri), "uri == null");
//        Preconditions.checkArgument(view != null, "view == null");
//        Preconditions.checkArgument(SimpleDraweeView.class.isAssignableFrom(view.getClass()), "DraweeView is not assignable from view");
//
//        SimpleDraweeView dv = (SimpleDraweeView) view;
//        dv.setImageURI("asset:///" + uri);

    }

//    @Override
//    protected void close() {
//        super.close();
//        Fresco.shutDown();
//    }
}
