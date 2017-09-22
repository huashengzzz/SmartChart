package com.smart.smartchart.utils.imageloader;

/**
 * Created by lm on 2016/5/10.
 */
public class ImageLoaderFactory {
    private static ImageLoaderWrapper sInstance ;
    private ImageLoaderFactory(){}

    public static ImageLoaderWrapper getLoader(){
        if(sInstance == null){
            synchronized (ImageLoaderFactory.class){
                if(sInstance == null){
                    sInstance = new PicassoImageLoader();
                }
            }
        }

        return sInstance;
    }
}
