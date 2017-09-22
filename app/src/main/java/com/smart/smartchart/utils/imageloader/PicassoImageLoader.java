package com.smart.smartchart.utils.imageloader;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by lm on 2016/5/10.
 */
class PicassoImageLoader implements ImageLoaderWrapper {


    @Override
    public void displayImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .into(imageView);
        }

    }

    @Override
    public void displayImage(ImageView imageView, String url, int placeHolderResourceId, int errorResourceId) {
        if (TextUtils.isEmpty(url)) {
            Picasso.with(imageView.getContext()).load(placeHolderResourceId)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(placeHolderResourceId)
                    .error(errorResourceId)
                    .into(imageView);
        } else {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(placeHolderResourceId)
                    .error(errorResourceId)
                    .into(imageView);
        }
    }

    @Override
    public void displayImage(ImageView imageView, int resId, int placeHolderResourceId, int errorResourceId) {
        Picasso.with(imageView.getContext())
                .load(resId)
                .config(Bitmap.Config.RGB_565)
                .placeholder(placeHolderResourceId)
                .error(errorResourceId)
                .into(imageView);
    }

    @Override
    public void displayImage(ImageView imageView, File file, int placeHolderResourceId, int errorResourceId) {
        Picasso.with(imageView.getContext())
                .load(file)
                .config(Bitmap.Config.RGB_565)
                .placeholder(placeHolderResourceId)
                .error(errorResourceId)
                .into(imageView);
    }
}
