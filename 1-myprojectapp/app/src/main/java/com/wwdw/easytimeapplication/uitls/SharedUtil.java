package com.wwdw.easytimeapplication.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class SharedUtil {
    private String path = "config";
    private final SharedPreferences sp;
    private static SharedUtil sharedPreUtils;

    public SharedUtil(Context context) {
        this.sp = context.getSharedPreferences(this.path, context.MODE_PRIVATE);
    }

    public static SharedUtil create(Context context) {
        if (sharedPreUtils == null) {
            Class var1 = SharedUtil.class;
            synchronized (SharedUtil.class) {
                if (sharedPreUtils == null) {
                    sharedPreUtils = new SharedUtil(context);
                }
            }
        }
        return sharedPreUtils;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void putBoolean(String key, boolean value) {
        this.sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.sp.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        this.sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return this.sp.getString(key, defValue);
    }

    public String getString(String key) {
        return this.sp.getString(key,"");
    }

    public void putInt(String key, int value) {
        this.sp.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return this.sp.getInt(key, defValue);
    }


    public void remove(String key) {

        this.sp.edit().remove(key).apply();
    }

    public void clear() {
        if (this.sp != null) {
            this.sp.edit().clear().apply();
        }
    }


    /**
     * 存储List集合
     *
     * @param key     存储的键
     * @param list    存储的集合
     */
    public void putList(String key, List<? extends Serializable> list) {
        try {
            put( key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     *
     * @param key     键
     * @param <E>     指定泛型
     * @return List集合
     */
    public  <E extends Serializable> List<E> getList(String key) {
        try {
            return (List<E>) get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储对象
     */
    private  void put(String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString(key, objectStr);
    }

    /**
     * 获取对象
     */
    private Object get(String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }

}
