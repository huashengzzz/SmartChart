package com.smart.smartchart.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {
    // private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String NAME = "videorecord";

    /**
     * 设置视频录制的目录路径 android/data/包名/cash/videorecord
     *
     * @param context
     * @return
     */
    public static File getVideoDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(context.getApplicationContext().getExternalCacheDir(), NAME);
        }
        return null;
    }

    /**
     * 获得最后一个文件
     *
     * @param dirPath
     * @return
     */

    public static String getLastVideoPath(String dirPath) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(dirPath);
            if (file.exists()) {
                String[] list = file.list();
                if (list.length > 0)
                    return dirPath + list[list.length - 1];
            }
        }
        return "";
    }

    public static String[] getVideoName(String dirPath) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(dirPath);
            if (file.exists()) {
                if (file.list() != null && file.length() > 0)
                    return file.list();
            }
        }
        return null;
    }

    /**
     * 获取视频存储的文件目录路径
     *
     * @param context
     * @return
     */
    public static String getVideoDirPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return context.getApplicationContext().getExternalCacheDir().getPath() + "/" + NAME;
        }
        return "";
    }

    public static int getVideoCount(String dirPath) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(dirPath);
            if (file.exists()) {
                return file.list().length;
            }
        }
        return 0;
    }

    public static void deleteFile(String filePath) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    String[] filePaths = file.list();
                    for (String path : filePaths) {
                        deleteFile(filePath + File.separator + path);
                    }
                    file.delete();
                }
            }
        }
    }

}
