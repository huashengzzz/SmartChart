package com.smart.smartchart.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class CommonUtils {
    private static InputMethodManager imm;


    public static void clearUserData(Context mContext) {
        SPManager.setAuthCode(mContext, "");
    }


    public static void clearFocus(EditText editText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    public static boolean isSingleActivity(Context var0) {
        ActivityManager var1 = (ActivityManager) var0.getSystemService("activity_message");
        List var2 = null;

        try {
            var2 = var1.getRunningTasks(1);
        } catch (SecurityException var4) {
            var4.printStackTrace();
        }

        return var2 != null && var2.size() >= 1 ? ((ActivityManager.RunningTaskInfo) var2.get(0)).numRunning == 1 : false;
    }

    /**
     * 给字加下划线
     *
     * @param ct
     * @param v
     * @param color
     */
    public static void setUNDERLINE(Context ct, TextView v, int color) {
        v.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
        v.setTextColor(ct.getResources().getColor(color));
    }

    /**
     * 往手机通讯录插入联系人
     *
     * @param ct
     * @param name
     * @param tel
     * @param email
     */
    public static void insertContact(Context ct, String name, String tel, String email) {
        ContentValues values = new ContentValues();
        // 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = ct.getContentResolver().insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        // 往data表入姓名数据
        if (!TextUtils.isEmpty(tel)) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);// 内容类型
            values.put(StructuredName.GIVEN_NAME, name);
            ct.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        // 往data表入电话数据
        if (!TextUtils.isEmpty(tel)) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);// 内容类型
            values.put(Phone.NUMBER, tel);
            values.put(Phone.TYPE, Phone.TYPE_MOBILE);
            ct.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        // 往data表入Email数据
        if (!TextUtils.isEmpty(email)) {
            values.clear();
            values.put(Data.RAW_CONTACT_ID, rawContactId);
            values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);// 内容类型
            values.put(Email.DATA, email);
            values.put(Email.TYPE, Email.TYPE_WORK);
            ct.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
    }


    /**
     * 发短信
     *
     * @param ct
     * @param tel
     */
    public static void sendMessage(Context ct, String tel) {
        Intent localIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
        ct.startActivity(localIntent);
    }


    /**
     * 显示软键盘
     */
    public static void showSoft(EditText view, Context context) {
        if (null == imm)
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        imm.showSoftInput(view, 0);
    }

    /**
     * 打开键盘
     */
    public static void openKeyBoard(final Context context) {
        openKeyBoard(context, 50);
    }

    /**
     * 打开键盘
     *
     * @param context
     * @param delay   延迟（毫秒）
     */
    public static void openKeyBoard(final Context context, int delay) {
        if (null == imm)
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, delay);
    }

    public static void closeKeyBoard(Activity activity) {
        if (null == imm)
            imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoft(EditText view, Context context) {
        if (null == imm)
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.clearFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 将字符串转换成md5
     *
     * @param paramString
     * @return
     */
    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        String str;
        StringBuilder hexString = new StringBuilder();
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        str = hexString.toString();
        return str;
    }

    /**
     * 计算整个listview所占的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    /**
     * 格式化日期
     *
     * @return
     */
    public static String formatDateSimple(long data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(data);
    }


    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$|17[0768]{1}[0-9]{8}$|14[57]{1}[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNOSimple(@NonNull String mobiles) {
        return mobiles.length() == 11;
    }


    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 判断是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    /**
     * 获取格式化后的金额字符串
     *
     * @param money 金额
     * @return 格式化后的金额字符串
     */
    public static String getFormatMoney(double money) {
        NumberFormat nf;
        int tmp = (int) money;
        if (money - tmp != 0) {
            nf = new DecimalFormat("##0.00");
            String str = nf.format(money);
            str = str.replaceAll("0+?$", "");//去掉后面无用的零
            return str;
        } else {
            nf = new DecimalFormat("##0");
            return nf.format(money);
        }
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return mNetworkInfo != null && mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }


    public static int getCurrentYear(Calendar calendar) {

        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth(Calendar calendar) {

        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrrentDay(Calendar calendar) {

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Point getScreenHeightAndWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;

    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /*
    * 将时间转换为时间戳
    */
    public static long dateToStamp(String dateStr, String format) throws ParseException {
        long res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(dateStr);
        res = date.getTime();
        return res;


    }

    /*****************************************************************************************************************************************************************************************/


    /**
     * 中文正则校验
     *
     * @return
     */
    public static boolean verifyChinese(String value) {
        String str = "^[\u4e00-\u9fa5]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(value);
        return m.matches(); //是否含有中文字符
    }

    /**
     * 通行证及护照正则校验
     *
     * @return
     */
    public static boolean verifyPassport(String value) {
        String str = "^([a-zA-Z]{1}[0-9]{8})$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 身份证校验
     *
     * @return
     */
    public static boolean verifydentityICard(String value) {
        return value.length() == 15 || value.length() == 18;
    }


    /**
     * 拼音姓名校验
     *
     * @return
     */
    public static boolean verifyCharacters(String value) {
        String str = "^[a-zA-Z]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(value);
        return m.matches();
    }


    /**
     * @param value
     * @return
     */
    public static boolean isIllegalCharacter(String value) {
        String str = "^[a-zA-Z\u4e00-\u9fa5]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(value);
        return m.matches(); //是否含有中文字符

    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断应用前后台
     */

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                    /*
                    BACKGROUND=400 EMPTY=500 FOREGROUND=100
                    GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                     */
                Log.i("info", "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                    Log.i("info", "处于后台"
//                            + appProcess.processName);
                    return true;
                } else {
//                    Log.i("info", "处于前台"
//                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 获取控件宽度
     */
    public static float getViewWidth(View view, boolean type) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        if (type)
            return view.getMeasuredWidth();
        else
            return view.getMeasuredHeight();

    }


    public static ArrayList<Calendar> getCurrentMonthCalendar(Calendar c, int offset) {
        ArrayList<Calendar> calendars = new ArrayList<>();
        Calendar currentMonth = (Calendar) c.clone();
        currentMonth.add(Calendar.MONTH, offset);


        Calendar cal = (Calendar) c.clone();
        cal.add(Calendar.MONTH, offset);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int off = cal.getFirstDayOfWeek() - firstDayOfWeek;
        if (off > 0) {
            off -= 7;
        }
        cal.add(Calendar.DATE, off);

        while ((cal.get(MONTH) < currentMonth.get(Calendar.MONTH) + 1 || cal.get(YEAR) < currentMonth.get(Calendar.YEAR)) //
                && cal.get(YEAR) <= currentMonth.get(Calendar.YEAR)) {
            for (int i = 0; i < 7; i++) {
                calendars.add((Calendar) cal.clone());
                cal.add(Calendar.DATE, 1);
                Log.i("aaa", cal.get(Calendar.MONTH) + "--" + cal.get(Calendar.DAY_OF_MONTH));
            }
        }
        return calendars;
    }


    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {//闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
//            Logger.i("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 转化不同大小颜色字体
     *
     * @param start
     * @param end
     * @param size
     * @param color
     * @param isChangeSize
     * @param isChanegColor
     * @param content
     * @param context
     * @return
     */
    public static SpannableString setTextStyle(int start, int end, int size, int color, boolean isChangeSize, boolean isChanegColor, String content, Context context) {
        SpannableString ss = new SpannableString(content);
        if (isChangeSize) {
            ss.setSpan(new AbsoluteSizeSpan(DisplayUtil.dp2px(context, size)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (isChanegColor) {
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    /**
     * 给字符串加*号
     *
     * @param code
     * @param start
     * @param end
     * @return
     */
    public static String encryptCode(String code, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            if (i >= start && i < end) {
                stringBuilder.append("*");
            } else {
                stringBuilder.append(code.charAt(i));
            }

        }
        return stringBuilder.toString();
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                if (packageInfos.get(i).packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;

    }

    public static void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (null == layoutParams)
            return;
        if (width > 0) {
            layoutParams.width = width;
        }
        if (height > 0) {
            layoutParams.height = height;
        }

        view.setLayoutParams(layoutParams);
    }

    /**
     * dip转为PX
     */
    public static int dp2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度px
     *
     * @param context 上下文
     * @return 屏幕宽px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度px
     *
     * @param context 上下文
     * @return 屏幕高px
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
        return outMetrics.heightPixels;
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    //解决水平滑动布局和scrollview的滑动冲突
    public static void solveScrollConflict(View view, final ScrollView scrollView) {
        view.setOnTouchListener(new View.OnTouchListener() {
            float ratio = 1.2f; //水平和竖直方向滑动的灵敏度,偏大是水平方向灵敏
            float x0 = 0f;
            float y0 = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x0 = event.getX();
                        y0 = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = Math.abs(event.getX() - x0);
                        float dy = Math.abs(event.getY() - y0);
                        x0 = event.getX();
                        y0 = event.getY();
                        scrollView.requestDisallowInterceptTouchEvent(dx * ratio > dy);
                        break;
                }
                return false;
            }
        });
    }

}