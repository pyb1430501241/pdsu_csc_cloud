package com.pdsu.csc.utils;

import com.pdsu.csc.bean.EsBlobInformation;
import com.pdsu.csc.bean.EsFileInformation;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 半梦
 * @create 2020-11-26 19:48
 */
public abstract class ConstructorUtils {

    /**
     * 获取该类的构造器
     */
    @NonNull
    protected static <T> Constructor<T> getConstructorByClass(@NonNull Class<T> clazz) throws NoSuchMethodException, SecurityException{
        Constructor<T> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return constructor;
    }

    /**
     * 根据构造器把 map 封装成对象
     */
    @NonNull
    protected static <T> T getObjectByMapAndClass(@NonNull Map<String, Object> map, @NonNull Class<T> clazz) throws IllegalArgumentException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        Constructor<T> constructor = getConstructorByClass(clazz);
        T bean = BeanUtils.instantiateClass(constructor);
        populateBean(map, bean);
        return bean;
    }

    /**
     * 为属性赋值
     */
    public static void populateBean(@NonNull Map<String, Object> map, @NonNull Object bean) throws NoSuchFieldException, IllegalAccessException {
        Set<String> keySet = map.keySet();
        Class<?> clazz = bean.getClass();
        for(String key : keySet) {
            doSetBeanAttribute(bean, key, map.get(key), clazz);
        }
    }

    /**
     * 为属性赋值
     */
    private static void doSetBeanAttribute(Object bean, String key, Object value, Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = clazz.getDeclaredField(key);
        declaredField.setAccessible(true);
        declaredField.set(bean, value);
    }

}
