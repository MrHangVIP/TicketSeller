package com.hoge.android.library.baidumap.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 百度地图Library通用工具类
 * 创建人：wuguojin
 * 时间：17/9/21
 */

public class CommonUtil {

    public static String CONFIGURATION_CLASS = "com.hoge.android.factory.util.ConfigureUtils";
    public static String TEMPLATE_CLASS = "com.hoge.android.factory.constants.TemplateConstants";
    public static String VARIABLE_CLASS = "com.hoge.android.factory.variable.Variable";
    public static String CONSTANT_CLASS = "com.hoge.android.factory.constants.Constants";

    private static CommonUtil mInstance;

    public static CommonUtil getInstance() {
        if (mInstance == null) {
            mInstance = new CommonUtil();
        }
        return mInstance;
    }

    /**
     * 静态方法反射
     *
     * @param name
     * @param methodName
     * @param types
     * @param args
     * @return
     */
    public static Object invokeByStaticMethod(String name,
                                              String methodName, Class<?>[] types, Object[] args) {
        try {
            Class<?> threadClazz = Class.forName(name);
            Method method = threadClazz.getMethod(methodName, types);
            return method.invoke(null, args);
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 给静态变量赋值
     *
     * @param className
     * @param variableName
     * @param value
     */
    public static void set(String className, String variableName, String value) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(variableName);
            field.set(null, value);
        } catch (Exception e) {
        }
    }

    /**
     * 获取静态string
     *
     * @param className
     * @param variableName
     * @return
     */
    public static String getString(String className, String variableName) {
        String s = "";
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(variableName);
            s = field.get(null).toString();
        } catch (Exception e) {
        }
        return s;
    }

    /**
     * 获取静态object
     *
     * @param fieldName
     * @return
     */
    public static Object getStaticField(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            return field.get(clazz);
        } catch (Exception e) {
        }
        return null;
    }


    SharedPreferences sp;
    SharedPreferences.Editor editor;

    /**
     * sp存储
     *
     * @param context
     * @param key
     * @param value
     */
    public void putIntoSp(Context context, String key, String value) {
        if (sp == null || editor == null) {
            sp = context.getSharedPreferences("setting", 0);
            editor = sp.edit();
        }
        editor.putString(key, value);
        editor.commit();
    }

}
