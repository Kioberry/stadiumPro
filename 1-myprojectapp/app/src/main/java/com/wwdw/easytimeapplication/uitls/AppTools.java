package com.wwdw.easytimeapplication.uitls;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * app中各种判断问题类
 */
@SuppressLint("SimpleDateFormat")
public class AppTools {

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取当前日期
     */
    public static int getDate() {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.DATE);
    }


    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }


    // 32MD5加密
    public static String getMD5(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param timestamp 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String timestampTotime(long timestamp, String type) {
        timestamp = timestamp * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = new Date(timestamp);
        return sdf.format(date);
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param timestamp 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String timestampTotimeData(long timestamp, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            long a = sdf.parse(date_str).getTime() / 1000;
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long dateTimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            long a = sdf.parse(date_str).getTime();
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将当前指定格式转换为时间戳转
     *
     * @param
     * @return
     */
    public static long getCurrentTimeStamp(String date_str) {
        //设置时间格式
        SimpleDateFormat formatter = new SimpleDateFormat(date_str);//得到当前时间
        String currentTime = formatter.format(new Date());
        Date date = null;
        try {
            date = formatter.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当前时间戳
        return date.getTime();
    }

    // 获取接口名,读取asset文件名
    public static String urlToFilename(String url) {
        String[] apiName = url.split("/");
        String api_h = apiName[apiName.length - 1];
        return api_h.replace("html", "xml");
    }

    // 判断字符串是否为空
    public static boolean checkStringNoNull(String paramString) {
        return paramString != null && paramString.length() > 0;
    }

    // 验证邮箱
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }


    // 科学计数法转换成数字 保留两位小数
    public static String scienceTwoNum(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    // 科学计数法转换成数字 保留一位小数
    public static String scienceOne(Double value) {
        DecimalFormat df = new DecimalFormat("##0.0");
        return df.format(value);
    }

    /**
     * 文件转化为字节数组
     *
     * @EditTime 2007-8-13 上午11:45:28
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }


    // 判断手机号是否合法
    public static boolean checkIphoneNumber(String name) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
         */
        String telRegex = "[1][12345789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            return name.matches(telRegex);
        }
    }

    // 固定电话正则
    public static boolean checkTelephoneNumber(String name) {

        String telRegex1 = "[0]\\d{11}";
        String telRegex2 = "[0]\\d{10}";// [1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            return name.matches(telRegex1) || name.matches(telRegex2);
        }
    }

    // 邮编的正则
    public static boolean checkPostcode(String name) {

        String telRegex = "[1-9]\\d{5}";//
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            return name.matches(telRegex);
        }
    }

    // 正则表达式判断用户名是否合法
    public static boolean checkPwd(String name) {
        if (name.length() >= 8 && name.length() <= 16) {
            Pattern p1 = Pattern.compile("[a-zA-Z]+");
            Pattern p2 = Pattern.compile("[0-9]+");
            Matcher m = p1.matcher(name);
            if (!m.find()) {
                return false;
            } else {
                // m = p2.matcher(name);
                m.reset().usePattern(p2);
                return m.find();
            }
        } else {
            return false;
        }
    }

    // 正则表达式判断用户名是否合法
    public static boolean checkrealPwd(String name) {
        if (name.length() >= 5 && name.length() <= 15) {
            Pattern p1 = Pattern.compile("[a-zA-Z]+");
            Pattern p2 = Pattern.compile("[0-9]+");
            Matcher m = p1.matcher(name);
            if (!m.find()) {
                return false;
            } else {
                // m = p2.matcher(name);
                m.reset().usePattern(p2);
                return m.find();
            }
        } else {
            return false;
        }
    }

    // 判断名字是否正确
    public static boolean checkRealName(String email) {
        String regex = "[\u4E00-\u9FA5]{2,}";
        return Pattern.matches(regex, email);

    }

    // 获取转换后的img
    public static Drawable getDrawable(Context context, int Rid) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = 2;
        InputStream is = context.getResources().openRawResource(Rid);
        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
        return bd;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {

        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;

        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();

        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;

        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;

    }


    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    //base64编码
    public static String makeUidToBase64(long uid) {

        String strUid = String.valueOf(uid);
        String enUid = new String(Base64.encode(strUid.getBytes(), Base64.DEFAULT));

        return enUid;

    }


    //解码
    public static long getUidFromBase64(String base64Id) {
        long uid = -1L;
        if (!TextUtils.isEmpty(base64Id)) {

            String result = "";
            if (!TextUtils.isEmpty(base64Id)) {
                result = new String(Base64.decode(base64Id.getBytes(), Base64.DEFAULT));
                uid = Long.parseLong(result);
            }
        }

        return uid;
    }


    //提取出字符串里的数字改变数字的大小和颜色
    public static SpannableStringBuilder setNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                style.setSpan(new ForegroundColorSpan(Color.RED), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                style.setSpan(new RelativeSizeSpan(3.0f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
        }
        return style;
    }

    /**
     * 获取屏幕的宽度
     */

    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕的高度
     */

    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    public static int getScreenHeightTotal(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context mContext, String packageName) {
        PackageManager manager = mContext.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }


    /*
     * 复制到剪切板
     * */
    public static void copy(Context context, String txt) {

        try {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", txt);
            cm.setPrimaryClip(mClipData);
            Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "复制失败,请重新尝试", Toast.LENGTH_SHORT).show();
        }

    }

    /*
     * 拨打电话
     * */
    public static void callTel(Context context, String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            context.startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(context, "拨打失败", Toast.LENGTH_SHORT).show();
        }

    }


    public static Date strToDate(String time) {
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginTime = CurrentTime.parse(time);
            return beginTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getYMD() {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String dateString = dff.format(new Date());
        return dateString;
    }

    public static int getM() {
        SimpleDateFormat dff = new SimpleDateFormat("MM");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String dateString = dff.format(new Date());
        return Integer.parseInt(dateString);
    }

    /**
     * @author stf
     * @time 2019-03-14 15:39
     * @remark 时间点比较
     * 相等 0；
     * date1 在date2 前，-1；
     * date1 在date2 后，1；
     */
    public static int timeCompare(Date d1, Date d2) {

        int i = 0;
        if (d1.compareTo(d2) > 0) {
            i = 1;
        } else if (d1.compareTo(d2) < 0) {
            i = -1;
        } else if (d1.compareTo(d2) == 0) {
            i = 0;
        }
        return i;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Activity context) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图标 bitmap
     *
     * @param context
     */
    public static synchronized Bitmap getBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName(context.getPackageName());
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    /**
     * 1 man 2 girl 果是奇数性别为男，偶数则为女。
     */
    public static int isSex(String idCard) {
        if (!TextUtils.isEmpty(idCard) && idCard.length() == 18) {
            if (Integer.parseInt(idCard.substring(16, 17)) % 2 == 0) {
                return 2;
            } else {
                return 1;
            }
        }
        return 0;
    }



    public static String getRunningActivityName(Activity context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }


    /**
     * ArrayList 去重
     */
    public static ArrayList singleElement(ArrayList al) {
        ArrayList newAl = new ArrayList();

        for (Iterator it = al.iterator(); it.hasNext(); ) {
            Object obj = it.next();
            if (!newAl.contains(obj)) {
                newAl.add(obj);
            }
        }
        return newAl;
    }


    /**
    * @Description: (描述)获取演示设备的型号
    * @author Bowen
    * @date 2021/5/14
    */
    public static Boolean getModelXDL() {
        String model = Build.MODEL;
        if (model.indexOf("3280") != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: (描述)是否使用X5WebView
     * @author Bowen
     * @date 2021/5/14
     */
    private static final Boolean useX5Webview=false;
    public static Boolean isUseX5Webview() {

        if ((Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)&&useX5Webview) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * 判断某activity是否处于栈顶
     * @return true在栈顶 false不在栈顶
     */
    public static boolean isActivityTop(Context context,Class cls){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }


    /**
     * @Description: (描述)
     * @author stf  判断设备是不是平板
     * @date 2021/9/2
     */

    public static boolean isTabletDevice(Context context) {
        boolean isFlag = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
        return isFlag;
    }

    public static void setTvIcn(Context mContext, TextView tv, int id) {
        try {
            Drawable drawable = mContext.getResources().getDrawable(id);
            tv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            tv.setCompoundDrawablePadding(dip2px(mContext, 10));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
            lp.topMargin = AppTools.dip2px(mContext, 10);
            tv.setLayoutParams(lp);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static int strIncludeMsgCount(String str, String s) {
        int count = 0;  // 初始值
        //一共有str.length()的循环次数
        for (int i = 0; i < str.length(); ) {
            int c = -1;
            c = str.indexOf(s);
            //如果有S这样的子串。则C的值不是-1.
            if (c != -1) {  // 如果c=-1则说明不在在
                //这里的c+1 而不是 c+ s.length();这是因为。如果str的字符串是“aaaa”， s = “aa”，则结果是2个。但是实际上是3个子字符串
                //将剩下的字符冲洗取出放到str中
                str = str.substring(c + 1);  // 从存在的那个下标后一位开始
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
