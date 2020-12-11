package com.pdsu.csc.utils;

import com.pdsu.csc.bean.EsBlobInformation;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author 半梦
 * @create 2020-11-26 19:48
 *
 */
@SuppressWarnings("all")
public abstract class BeanUtils {

    /**
     * <bean name = "a" class = "com.pdsu.csc.bean.A">
     *      <property name = "b" ref = "b"></>
     * </>
     * <bean name = "b" class = "com.pdsu.csc.bean.B">
     *      <property name = "a" ref = "a"></>
     * </>
     * @see AbstractApplicationContext#refresh()
     * 三重缓存解决循环依赖问题。
     * 一级缓存放: 成品对象
     * 二级缓存放: 半成品对象
     * 三级缓存放: lambda表达式
     * 1. 三级缓存解决循环依赖问题的关键是什么？为什么提前暴露对象能解决？
     *      实例化和初始化进行分开操作, 在中间过程中给其他对象赋值时, 使用的是半成品对象
     * 2. 如果只使用一级缓存, 可以解决循环依赖问题吗？
     *      不能。 在整个处理过程中, 缓存中存放的是半成品对象以及成品对象, 如果只有一级
     *      缓存, 那么区分成品和半成品对象将会提升过多的代价。
     * 3. 如果只适用二级缓存, 可以解决循环依赖问题吗？
     *      能。 如果我们保证所有的 bean 都不调用 getEarlyBeanReference 方法, 则二级
     *      缓存足以解决循环依赖问题。
     * 4. 使用三级缓存是为了什么？
     *      本质是在于解决 AOP 代理问题。
     * 5. 如果某一个 bean 明确知道其为代理对象, 还会创建普通的 bean 吗？
     *      会。 所有的 bean 在 IOC 中均平等的走过创建流程
     * 6. 为什么使用三级缓存可以解决 AOP 代理问题？
     *      当一个 bean 对象需要代理的时候, 整个创建过程会生成两个对象, 即原生 bean 和
     *      代理对象, bean 默认为单例, 在整个生命周期的处理缓解中, 一个 beanName 只能
     *      对应一个对象（源码为使用Map）, 所以需要在 getEarlyBeanReference 方法里进
     *      行相应的判断, 判断是否需要进行代理。
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#getEarlyBeanReference
     * 7. 我怎么知道什么时候使用？
     *      正因为不知道什么时候会调用, 所以才通过一个匿名内部类（源码为使用 lambda 表
     *      达式）, 在相关判断成立后, 在使用时直接对普通对象进行覆盖, 以保证全局唯一。
     *
     *
     */


    /**
     * 获取该类的无参构造器
     */
    @NonNull
    public static <T> Constructor<T> getConstructorByClass(@NonNull Class<T> clazz) throws NoSuchMethodException, SecurityException{
        Constructor<T> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return constructor;
    }

    /**
     * 根据指定参数获取类的实例
     * @param map 成员变量的 key 和 value
     * @param clazz 需要生成对象的类
     * @param <T> 泛型, 返回值的类型
     * @return
     *  实例化且被初始化的对象
     * @throws NoSuchMethodException 没找到对应方法
     * @throws NoSuchFieldException 没找到参数相应的 set 方法, 请确保 map 的 key 为需要赋值的成员变量名
     * @throws IllegalAccessException
     */
    @NonNull
    public static <T> T getObjectByMapAndClass(@NonNull Map<String, Object> map, @NonNull Class<T> clazz) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        // 获取构造器
        Constructor<T> constructor = getConstructorByClass(clazz);
        // 实例化 bean
        T bean = getObjectByConstructor(constructor);
        // 初始化 bean
        populateBean(map, bean);
        return bean;
    }

    /**
     * 根据构造器获取对象
     * @see Constructor
     */
    @NonNull
    public static <T> T getObjectByConstructor(@NonNull Constructor<T> constructor, Object ... args) throws IllegalArgumentException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        return org.springframework.beans.BeanUtils.instantiateClass(constructor, args);
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
     * 使用 set 方法, 为属性赋值
     * @see Field
     */
    public static void doSetBeanAttribute(Object bean, String key, Object value, Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = clazz.getDeclaredField(key);
        declaredField.setAccessible(true);
        declaredField.set(bean, value);
    }

}
