package org.hkmadao.core.cache;

import org.hkmadao.core.base.BaseDesc;
import org.hkmadao.core.base.BusinessException;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DescManager {
    private final static ConcurrentHashMap<String, BaseDesc> descs = new ConcurrentHashMap<>();

    public static void putBaseDesc(String key, BaseDesc value) {
        descs.put(key, value);
    }

    public static BaseDesc fetchBaseDesc(String key) throws BusinessException {
        if (Objects.nonNull(descs.get(key))) {
            return descs.get(key);
        }
        try {
            BaseDesc baseDesc = (BaseDesc) Class.forName(key).getConstructor().newInstance();
            descs.put(key, baseDesc);
            return baseDesc;
        } catch (InstantiationException e) {
            throw new BusinessException("Instantiation", e);
        } catch (IllegalAccessException e) {
            throw new BusinessException("IllegalAccess", e);
        } catch (InvocationTargetException e) {
            throw new BusinessException("InvocationTarget", e);
        } catch (NoSuchMethodException e) {
            throw new BusinessException("NoSuchMethod", e);
        } catch (ClassNotFoundException e) {
            throw new BusinessException("ClassNotFound", e);
        }
    }
}
