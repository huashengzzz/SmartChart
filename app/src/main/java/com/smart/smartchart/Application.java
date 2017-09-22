package com.smart.smartchart;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.smart.smartchart.http.OkHttp3Downloader;
import com.smart.smartchart.http.OkHttpUtils;
import com.smart.smartchart.http.cookie.persistentcookiejar.ClearableCookieJar;
import com.smart.smartchart.http.cookie.persistentcookiejar.PersistentCookieJar;
import com.smart.smartchart.http.cookie.persistentcookiejar.cache.SetCookieCache;
import com.smart.smartchart.http.cookie.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.smart.smartchart.http.log.HttpLoggingInterceptor;
import com.smart.smartchart.utils.CrashHandler;
import com.smart.smartchart.utils.SimpleActivityLifecycleCallbacks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * Created by Gs on 2016/8/23.
 */
public class Application extends android.app.Application {
    private static final String SMARTCHART_LOGGER = "SMARTCHART_LOGGER";
    private static Application instance;
    private List<Activity> activityList;

    public static Application getInstance() {
        return instance;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // 多进程导致多次初始化Application,这里只初始化App主进程的Application
        int pid = android.os.Process.myPid();
        String curProcessName = getAppName(pid);
        if (TextUtils.isEmpty(curProcessName) || !curProcessName.equals(getPackageName())) {
            return;
        }
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(SMARTCHART_LOGGER)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        Logger.d("执行初始化");
        instance = this;
        performInit();
        //配置picasso的本地缓存
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
        Picasso.setSingletonInstance(picasso);

        activityList = Collections.synchronizedList(new ArrayList<Activity>());
        registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityList.add(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityList.remove(activity);
            }
        });
        Logger.d("初始化结束");

    }
    /**
     * 执行初始化
     */
    private void performInit() {

        //set ButterKnife Debug
        ButterKnife.setDebug(BuildConfig.DEBUG);

        //初始化okhttp的工具
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.i(message);
                }
            });

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
//            builder.addInterceptor(new LoggerInterceptor("TAG", BuildConfig.DEBUG));
        }
        builder.connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar);
        OkHttpClient okHttpClient = builder.build();
        OkHttpUtils.initClient(okHttpClient);

        // init crash helper
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this.getApplicationContext()));
        //设置全局的下拉上拉加载
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
                header.setEnableLastTime(false);
                header.setPrimaryColors(ContextCompat.getColor(context, R.color.color_f6f6f6), ContextCompat.getColor(context, android.R.color.black));
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableLoadmoreWhenContentNotFull(true);
                layout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setBackgroundResource(R.color.color_f6f6f6);
                footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });

    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return null;
    }


    public void exit() {
        Iterator<Activity> iterator = activityList.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            activity.finish();
        }
        activityList.clear();
    }
}
