package com.jianmu.tools;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数值拷贝
 *
 * @author kong
 */
public class BeanTools {

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> target) {
        if (null == source) {
            return null;
        }
        T t = BeanUtils.instantiateClass(target);
        BeanUtils.copyProperties(source, t);
        return t;
    }

    /**
     * 复制对象属性到另外一个对象
     *
     * @param source
     * @param target
     * @return
     */
    public static Object copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T newObj = map(sourceObject, target);
            destinationList.add(newObj);
        }
        return destinationList;
    }


    /**
     * 拷贝忽略指定的属性集
     *
     * @param source
     * @param target
     * @param ignoreProperties
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        if (null == source || null == target) {
            return;
        }
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 转换List source to List target
     *
     * @param source
     * @param clazz
     * @param <A>
     * @param <B>
     * @return List<B>
     */
    public static <A, B> List<B> convertList(List<A> source, Class<B> clazz) {
        List<B> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(source)) {
            return list;
        }
        source.forEach(item -> list.add(map(item, clazz)));
        return list;
    }
}
