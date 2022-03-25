package com.jianmu.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author lime
 */
public class StreamTools {
    /**
     * example:
     * 根据对象属性去重
     * List list = new ArrayList();
     * list.stream().filter(distinct(Object::getClass)).collect(Collectors.toList())
     *
     * @param keyExtractor 泛型对象对应属性
     * @param <T>          泛型
     * @return 去重后的
     */
    public static <T> Predicate<T> distinct(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(3);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static void main(String[] args) {

    }
}
