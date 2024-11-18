package org.hkmadao.core.utils;

import org.hkmadao.core.base.BusinessException;

import java.util.UUID;

/**
 * id生成工具
 */
public class IdGenerator {
    /**
     * 预置id前缀
     */
    public static String PRESET_ID_PREFIX = "@@@@-";
    /**
     * 预置id前缀
     */
    public static String ID_PREFIX = "0000-";

    /**
     * 生成id
     *
     * @return id
     */
    public static String generateId() {
        return ID_PREFIX + UUID.randomUUID().toString();
    }

    /**
     * 生成预置数据id
     *
     * @return id
     */
    public static String generatePresetId() {
        return PRESET_ID_PREFIX + UUID.randomUUID().toString();
    }

    /**
     * 根据类型获取生成id，目前暂支持string类型
     *
     * @return id
     */
    public static <ID> ID generateId(Class<ID> idClass) throws BusinessException {
        if (String.class.equals(idClass)) {
            return (ID) (ID_PREFIX + UUID.randomUUID().toString());
        }
        if (Long.class.equals(idClass)) {
            throw new BusinessException("尚未实现Long类型");
        }
        throw new BusinessException("不支持的id类型:" + idClass.getTypeName());
    }
}
