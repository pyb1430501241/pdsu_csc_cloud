package com.pdsu.csc.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/**
 * @author 半梦
 * @create 2020-11-26 19:48
 */
public final class ConstructorUtils {

    /**
     * 获取该类的构造器
     */
    @Nullable
    protected static Constructor<?> getConstructorByClass(@NonNull Class<?> clazz) throws NoSuchMethodException, SecurityException{
        String name = StringUtils.getSuffixName(clazz.getName()).replaceFirst("[.]", "");
        Constructor<?> constructor;
        switch (name) {
            case "EsUserInformation":
                constructor = clazz.getDeclaredConstructor(Integer.class, Integer.class, String.class, Integer.class, String.class);
                break;
            case "EsBlobInformation":
                constructor = clazz.getDeclaredConstructor(Integer.class, String.class, String.class);
                break;
            case "EsFileInformation":
                constructor = clazz.getDeclaredConstructor(String.class, String.class, Integer.class);
                break;
            default:
                return clazz.getConstructors()[0];
        }
        constructor.setAccessible(true);
        return constructor;
    }

    /**
     * 根据构造器把 map 封装成对象
     */
    @NonNull
    protected static Object getObjectByMap(@NonNull Map<String, Object> map, @NonNull Constructor<?> constructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Set<String> keySet = map.keySet();
        Object [] objects = new Object[keySet.size()];
        int i = 0;
        for(String key : keySet) {
            objects[i] = map.get(key);
            i++;
        }
        return constructor.newInstance(objects);
    }

}
