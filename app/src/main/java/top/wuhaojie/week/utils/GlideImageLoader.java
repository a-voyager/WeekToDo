package top.wuhaojie.week.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by wuhaojie on 17-2-7.
 */

public class GlideImageLoader extends ImageLoader {


    private final RequestManager mRequestManager;

    private final GlideCircleTransform GLIDE_CIRCLE_TRANSFORM;


    private GlideImageLoader(Context context) {
        mRequestManager = Glide.with(context);
        GLIDE_CIRCLE_TRANSFORM = new GlideCircleTransform(context);
    }


    public static GlideImageLoader create(Context context) {
        return new GlideImageLoader(context);
    }

    @Override
    public void load(String uri, View view) {
        load(uri, view, OPTION_CENTER_CROP);
    }


    @Override
    protected void loadWithOptions(String uri, View view, int options) {
        if (TextUtils.isEmpty(uri) || view == null)
            throw new IllegalArgumentException("uri == null or view == null");
        if (!(view instanceof ImageView))
            throw new IllegalArgumentException("view is not a ImageView");
        ImageView iv = (ImageView) view;
        // 兼容旧版本
        if (uri.startsWith("asset:///")) uri = uri.substring(9);
        DrawableRequestBuilder<String> builder = mRequestManager
                .load("file:///android_asset/" + uri);
        if (hasOption(OPTION_CENTER_CROP)) builder.centerCrop();
        if (hasOption(OPTION_CIRCLE_CROP)) builder.transform(GLIDE_CIRCLE_TRANSFORM);
        builder.into(iv);
    }

    @Override
    protected void close() {
        super.close();
    }


    private static class GlideCircleTransform extends BitmapTransformation {


        GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
