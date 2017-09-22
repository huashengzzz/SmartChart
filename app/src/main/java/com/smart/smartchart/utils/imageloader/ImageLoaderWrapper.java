package com.smart.smartchart.utils.imageloader;

import android.widget.ImageView;

import java.io.File;

/**
 * Created by lm on 2016/5/10.
 */
public interface ImageLoaderWrapper {

    void displayImage(ImageView imageView, String url);

    void displayImage(ImageView imageView, String url, int placeHolderResourceId, int errorResourceId);

    void displayImage(ImageView imageView, int resId, int placeHolderResourceId, int errorResourceId);

    void displayImage(ImageView imageView, File file, int placeHolderResourceId, int errorResourceId);


}
