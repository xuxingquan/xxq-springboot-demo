package com.xxq.common.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author jerry.xu.coc
 */
public class WrappedBeanCopier {

    private static final ConcurrentMap<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Constructor> constructorAccessCache = new ConcurrentHashMap<>();

    private static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        return beanCopierCache.computeIfAbsent(beanKey,
                (key) -> BeanCopier.create(sourceClass, targetClass, false));
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
            return class1.toString() + class2.toString();
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (!ObjectUtils.anyNotNull(source, targetClass)) {
            return null;
        }
        T t;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            String message = "Create new instance of %s failed: %s";
            throw new RuntimeException(String.format(message, targetClass, e.getMessage()));
        }
        copyProperties(source, t);
        return t;
    }

    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        Constructor<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        sourceList.stream().forEach(o -> {
                    try {
                        T t = constructorAccess.newInstance();
                        copyProperties(o, t);
                        resultList.add(t);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return resultList;
    }

    private static <T> Constructor<T> getConstructorAccess(Class<T> targetClass) {

        Constructor<T> constructorAccess = constructorAccessCache.get(targetClass.toString());
        if (Objects.nonNull(constructorAccess)) {
            return constructorAccess;
        }

        try {
            constructorAccess = targetClass.getConstructor();
            constructorAccess.newInstance();
            constructorAccessCache.putIfAbsent(targetClass.toString(), constructorAccess);
        } catch (Exception e) {
            String message = "Create new instance of %s failed: %s";
            throw new RuntimeException(String.format(message, targetClass, e.getMessage()));
        }
        return constructorAccess;
    }

}