package org.hkmadao.core.base.utils;

import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.base.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EntityUtil {

    private static final Logger log = LoggerFactory.getLogger(EntityUtil.class);

    public static boolean setValueByName(Object entity, String name, Object value) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(name)) {
            throw new BusinessException("设置属性失败:" + name);
        }
        Class<?> clazz = entity.getClass();
        try {
            Field declaredField = clazz.getDeclaredField(name);
            declaredField.setAccessible(true);
            declaredField.set(entity, value);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BusinessException("设置属性失败:" + name, e.getCause());
        }
    }

    /**
     * 通过set方法名称设置值
     *
     * @param entity        实体值
     * @param setMethodName set方法名称
     * @param value         属性值
     * @return
     * @throws BusinessException
     */
    public static Object setValueBySetMethodName(Object entity, String setMethodName, Object value) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(setMethodName)) {
            throw new BusinessException("获取属性失败:" + setMethodName);
        }
        Class<?> clazz = entity.getClass();
        try {
            Method[] methods = clazz.getMethods();
            Method method = null;
            for (Method methodTemp : methods) {
                if (setMethodName.equals(methodTemp.getName())) {
                    method = methodTemp;
                }
            }
            if(null == method){
                log.warn("类型：" + entity.getClass().getTypeName() + ",设置属性失败:" + setMethodName);
            }
            method.setAccessible(true);
            Object o = method.invoke(entity, value);
            return o;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BusinessException("设置属性失败:" + setMethodName, e.getCause());
        }
    }

    public static String fetchStringValueByName(Object entity, String name) throws BusinessException {
        return (String) fetchValueByName(entity, name);
    }

    /**
     * 通过get方法名称获取值
     *
     * @param entity        实体值
     * @param getMethodName get方法名称
     * @return
     * @throws BusinessException
     */
    public static String fetchStringValueByGetMethodName(Object entity, String getMethodName) throws BusinessException {
        return (String) fetchValueByGetMethodName(entity, getMethodName);
    }

    public static Object fetchValueByName(Object entity, String name) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(name)) {
            throw new BusinessException("获取属性失败:" + name);
        }
        Class<?> clazz = entity.getClass();
        try {
            Field declaredField = clazz.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(entity);
            return o;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BusinessException("获取属性失败:" + name, e.getCause());
        }
    }

    /**
     * 通过get方法名称获取值
     *
     * @param entity        实体值
     * @param getMethodName get方法名称
     * @return
     * @throws BusinessException
     */
    public static Object fetchValueByGetMethodName(Object entity, String getMethodName) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(getMethodName)) {
            throw new BusinessException("获取属性失败:" + getMethodName);
        }
        Class<?> clazz = entity.getClass();
        try {
            Method method = clazz.getMethod(getMethodName);
            method.setAccessible(true);
            Object o = method.invoke(entity);
            return o;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BusinessException("获取属性失败:" + getMethodName, e.getCause());
//            log.warn("获取属性失败:" + getMethodName);
        } catch (NoSuchMethodException e) {
//            log.warn("类型：" + entity.getClass().getTypeName() + ",获取属性失败:" + getMethodName);
            return null;
        }
    }

    public static BaseEntity fetchBaseEntityValueByName(Object entity, String name) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(name)) {
            throw new BusinessException("获取属性失败:" + name);
        }
        Class<?> clazz = entity.getClass();
        try {
            Field declaredField = clazz.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(entity);
            return (BaseEntity) o;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BusinessException("获取属性失败:" + name, e.getCause());
        }
    }

    /**
     * 通过get方法名称获取值
     *
     * @param entity        实体值
     * @param getMethodName get方法名称
     * @return
     * @throws BusinessException
     */
    public static BaseEntity fetchBaseEntityValueByGetMethodName(Object entity, String getMethodName) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(getMethodName)) {
            throw new BusinessException("获取属性失败:" + getMethodName);
        }
        Class<?> clazz = entity.getClass();
        try {
            Method method = clazz.getMethod(getMethodName);
            method.setAccessible(true);
            Object o = method.invoke(entity);
            return (BaseEntity) o;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BusinessException("获取属性失败:" + getMethodName, e.getCause());
        } catch (NoSuchMethodException e) {
//            log.warn("类型：" + entity.getClass().getTypeName() + ",获取属性失败:" + getMethodName);
            return null;
        }
    }

    public static Set<BaseEntity> fetchSetValueByName(Object entity, String name) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(name)) {
            throw new BusinessException("获取属性失败:" + name);
        }
        Class<?> clazz = entity.getClass();
        String methodName = NameSwitchUtils.camelCase2GetMethodName(name);
        try {
            Field declaredField = clazz.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(entity);
            if (Objects.isNull(o)) {
                return new HashSet<>();
            }
            return (Set<BaseEntity>) o;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BusinessException("获取属性失败:" + name, e.getCause());
        }
    }

    /**
     * 通过get方法名称获取值
     *
     * @param entity        实体值
     * @param getMethodName get方法名称
     * @return
     * @throws BusinessException
     */
    public static Set<BaseEntity> fetchSetValueByGetMethodName(Object entity, String getMethodName) throws BusinessException {
        if (Objects.isNull(entity) || !StringUtils.hasLength(getMethodName)) {
            throw new BusinessException("获取属性失败:" + getMethodName);
        }
        Class<?> clazz = entity.getClass();
        try {
            Method method = clazz.getMethod(getMethodName);
            method.setAccessible(true);
            Object o = method.invoke(entity);
            if (Objects.isNull(o)) {
                return new HashSet<>();
            }
            return (Set<BaseEntity>) o;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BusinessException("获取属性失败:" + getMethodName, e.getCause());
        } catch (NoSuchMethodException e) {
//            log.warn("类型：" + entity.getClass().getTypeName() + ",获取属性失败:" + getMethodName);
            return null;
        }
    }
}
