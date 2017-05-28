package com.shishicai.app.utils;

/**
 * Created by Administrator on 2017/4/29 0029.
 */

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shishicai.app.Constant;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ImageLoaderUtils {
    private static ImageLoader imageLoader;
    private volatile static ImageLoaderUtils instance;

    private ImageLoaderUtils() {
        imageLoader = ImageLoader.getInstance();
    }

    public static ImageLoaderUtils getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderUtils.class) {
                if (instance == null) {
                    instance = new ImageLoaderUtils();
                }
            }
        }
        return instance;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageNetResource(ImageView iv, String url) {
        imageLoader.displayImage(url, iv, Constant.options, Constant.animateFirstListener);
    }

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public void clearCache() {
        imageLoader.clearDiskCache();// 本地缓存
        imageLoader.clearMemoryCache();// 内存缓存
    }
}
