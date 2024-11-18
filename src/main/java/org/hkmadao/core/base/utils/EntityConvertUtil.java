package org.hkmadao.core.base.utils;

import org.hkmadao.core.base.*;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hkmadao.core.base.*;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 只用于用于hibernate实体转换为dto
 * dto的唯一键字段必须标志 @DtoIdentify{@link DtoIdentify}
 * hibernate的主键值必须全局唯一
 */
public class EntityConvertUtil {

    /**
     * 用于hibernate实体转换为dto
     *
     * @param dtoClazz dto类型
     * @param entity   实体数据
     * @return 转换后的数据
     * @throws BusinessException
     */
    public static <DTO extends BaseEntity> DTO entity2Dto(Class<DTO> dtoClazz, BaseEntity entity) throws BusinessException {
        DtoClazzTree dtoClazzTree = new DtoClazzTree();
        dtoClazzTree.setFieldName(null);
        dtoClazzTree.setDtoClazz(dtoClazz);
        Map<String, DtoClazzTree> clazzTreeMap = new HashMap<>();
        buildClazzTree(dtoClazz, dtoClazzTree, clazzTreeMap);
        if (Objects.isNull(entity)) {
            return null;
        }
        Map<String, Map<String, BaseEntity>> cacheObjMap = new HashMap<>();
        try {
            BaseEntity dto = (BaseEntity) dtoClazzTree.getDtoClazz().getConstructor().newInstance();
            EntityConvertUtil.buildDtoTree(entity, dto, dtoClazzTree, cacheObjMap);
            return (DTO) dto;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new BusinessException("转换dto失败", e);
        }
    }

    /**
     * 用于hibernate批量实体转换为dto
     *
     * @param dtoClazz dto类型
     * @param entities 实体数据集合
     * @return 转换后的数据
     * @throws BusinessException
     */
    public static <DTO extends BaseEntity> List<DTO> batchEntity2Dto(Class<DTO> dtoClazz,
                                                                     List<BaseEntity> entities) throws BusinessException {
        DtoClazzTree dtoClazzTree = new DtoClazzTree();
        dtoClazzTree.setFieldName(null);
        dtoClazzTree.setDtoClazz(dtoClazz);
        Map<String, DtoClazzTree> clazzTreeMap = new HashMap<>();
        buildClazzTree(dtoClazz, dtoClazzTree, clazzTreeMap);

        List<DTO> dtoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entities)) {
            return dtoList;
        }
        Map<String, Map<String, BaseEntity>> cacheObjMap = new HashMap<>();
        try {
            for (BaseEntity entity : entities) {
                BaseEntity dto = (BaseEntity) dtoClazzTree.getDtoClazz().getConstructor().newInstance();
                EntityConvertUtil.buildDtoTree(entity, dto, dtoClazzTree, cacheObjMap);
                dtoList.add((DTO) dto);
            }
            return dtoList;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new BusinessException("转换dto失败", e);
        }
    }

    private static BaseEntity getCacheData(String className, String idData,
                                           Map<String, Map<String, BaseEntity>> cacheObjMap) {
        if (Objects.nonNull(cacheObjMap.get(className))) {
            return cacheObjMap.get(className).get(idData);
        }
        return null;
    }

    private static boolean containsKey(String className, String idData,
                                       Map<String, Map<String, BaseEntity>> cacheObjMap) {
        if (!cacheObjMap.containsKey(className)) {
            return false;
        }
        return cacheObjMap.get(className).containsKey(idData);
    }

    private static BaseEntity putCacheData(String className, String idData, BaseEntity data, Map<String,
            Map<String, BaseEntity>> cacheObjMap) {
        if (Objects.isNull(cacheObjMap.get(className))) {
            cacheObjMap.put(className, new HashMap<>());
        }
        return cacheObjMap.get(className).put(idData, data);
    }

    /**
     * 将entity转换为DTO
     *
     * @param entity       实体数据
     * @param dto          DTO的数据
     * @param dtoClazzTree DTO的类型树信息
     * @param cacheObjMap  DTO的数据缓存，防止触发懒加载无限循环
     * @throws BusinessException
     */
    public static void buildDtoTree(BaseEntity entity, BaseEntity dto, DtoClazzTree dtoClazzTree,
                                    Map<String, Map<String, BaseEntity>> cacheObjMap) throws BusinessException {
        try {
            for (DtoClazzTree baseField : dtoClazzTree.getBaseClazzSet()) {
                if (baseField.isFgIgnore()) {
                    continue;
                }
                try {
                    Object sourceValue = EntityUtil.fetchValueByGetMethodName(entity,
                            NameSwitchUtils.camelCase2GetMethodName(baseField.getFieldName()));
                    EntityUtil.setValueByName(dto, baseField.getFieldName(), sourceValue);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }

            for (DtoClazzTree refClazzTree : dtoClazzTree.getRefDtoClazzSet()) {
                if (refClazzTree.isFgIgnore()) {
                    continue;
                }
                try {
                    Object sourceValue = EntityUtil.fetchValueByGetMethodName(entity,
                            NameSwitchUtils.camelCase2GetMethodName(refClazzTree.getFieldName()));
                    if (Objects.isNull(sourceValue)) {
                        continue;
                    }
                    BaseEntity refEntity = unProxyEntity(sourceValue);
                    Object idValue = EntityUtil.fetchValueByGetMethodName(refEntity,
                            NameSwitchUtils.camelCase2GetMethodName(refClazzTree.getIdName()));
                    if (containsKey(refClazzTree.getDtoClazz().getName(), idValue.toString(), cacheObjMap)) {
                        EntityUtil.setValueByName(dto, refClazzTree.getFieldName(),
                                getCacheData(refClazzTree.getDtoClazz().getName(), idValue.toString(), cacheObjMap));
                        continue;
                    }
                    BaseEntity refDto = (BaseEntity) refClazzTree.getDtoClazz().getConstructor().newInstance();
                    putCacheData(refClazzTree.getDtoClazz().getName(), idValue.toString(), refDto, cacheObjMap);
                    buildDtoTree(refEntity, refDto, refClazzTree, cacheObjMap);
                    EntityUtil.setValueByName(dto, refClazzTree.getFieldName(), refDto);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
            for (DtoClazzTree childClazzTree : dtoClazzTree.getChildrenDtoClazzSet()) {
                if (childClazzTree.isFgIgnore()) {
                    continue;
                }
                try {
                    Set<BaseEntity> childrenEntities = EntityUtil.fetchSetValueByGetMethodName(entity,
                            NameSwitchUtils.camelCase2GetMethodName(childClazzTree.getFieldName()));
                    if (CollectionUtils.isEmpty(childrenEntities)) {
                        EntityUtil.setValueByName(dto, childClazzTree.getFieldName(), new ArrayList<>());
                        continue;
                    }
                    List<BaseEntity> childrenDtos = new ArrayList<>();
                    for (BaseEntity childEntity : childrenEntities) {
                        try {
                            Object idValue = EntityUtil.fetchValueByGetMethodName(childEntity,
                                    NameSwitchUtils.camelCase2GetMethodName(childClazzTree.getIdName()));
                            BaseEntity refEntity = unProxyEntity(childEntity);
                            if (containsKey(childClazzTree.getDtoClazz().getName(), idValue.toString(), cacheObjMap)) {
                                childrenDtos.add(getCacheData(childClazzTree.getDtoClazz().getName(),
                                        idValue.toString(),
                                        cacheObjMap));
                                continue;
                            }
                            BaseEntity childDto =
                                    (BaseEntity) childClazzTree.getDtoClazz().getConstructor().newInstance();
                            putCacheData(childClazzTree.getDtoClazz().getName(), idValue.toString(), childDto,
                                    cacheObjMap);
                            buildDtoTree(refEntity, childDto, childClazzTree, cacheObjMap);
                            childrenDtos.add(childDto);
                        } catch (BusinessException e) {
                            e.printStackTrace();
                        }
                    }
                    EntityUtil.setValueByName(dto, childClazzTree.getFieldName(), childrenDtos);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BusinessException("获取DTO数据异常：", e);
        }
    }

    private static BaseEntity unProxyEntity(Object sourceValue) throws BusinessException {
        if (sourceValue instanceof HibernateProxy) {
            try {
                return (BaseEntity) Hibernate.unproxy(sourceValue);
            } catch (EntityNotFoundException e) {
                throw new BusinessException("未找到数据：", e);
            }
        }
        return (BaseEntity) sourceValue;
    }

    /**
     * 获取dto类型树
     *
     * @param clazz        dto类型
     * @param dtoClazzTree 类型树信息
     * @param clazzTreeMap 类型树信息缓存，防止嵌套死循环
     */
    public static void buildClazzTree(Class clazz, DtoClazzTree dtoClazzTree, Map<String, DtoClazzTree> clazzTreeMap) {
        for (Field field : clazz.getDeclaredFields()) {
            DtoClazzTree childDtoClazzTree = new DtoClazzTree();
            //是否是引用属性
            boolean isRef =
                    Objects.nonNull(field.getType().getGenericSuperclass()) && field.getType().getGenericSuperclass().getTypeName().contains(BaseEntity.class.getTypeName());
            //是否是集合属性
            boolean isChild = field.getType().getTypeName().contains(List.class.getTypeName());
            DtoIgnoreProperty dtoIgnoreProperty = field.getAnnotation(DtoIgnoreProperty.class);
            if (!(isRef || isChild)) {
                DtoIdentify annotation = field.getAnnotation(DtoIdentify.class);
                if (Objects.nonNull(annotation)) {
                    dtoClazzTree.setIdName(field.getName());
                }
                if (Objects.nonNull(dtoIgnoreProperty)) {
                    childDtoClazzTree.setFgIgnore(true);
                }
                childDtoClazzTree.setFieldName(field.getName());
                childDtoClazzTree.setDtoClazz(field.getType());
                dtoClazzTree.getBaseClazzSet().add(childDtoClazzTree);
                continue;
            }
            if (isRef) {
                Class refClazz = field.getType();
                String clazzName = refClazz.getName();
                if (clazzTreeMap.containsKey(clazzName)) {
                    DtoClazzTree cacheDdtoClazzTree = clazzTreeMap.get(clazzName);
                    childDtoClazzTree.setFieldName(field.getName());
                    if (Objects.nonNull(dtoIgnoreProperty)) {
                        childDtoClazzTree.setFgIgnore(true);
                    }
                    childDtoClazzTree.setDtoClazz(cacheDdtoClazzTree.getDtoClazz());
                    childDtoClazzTree.setRefDtoClazzSet(cacheDdtoClazzTree.getRefDtoClazzSet());
                    childDtoClazzTree.setChildrenDtoClazzSet(cacheDdtoClazzTree.getChildrenDtoClazzSet());
                    childDtoClazzTree.setBaseClazzSet(cacheDdtoClazzTree.getBaseClazzSet());
                    childDtoClazzTree.setIdName(cacheDdtoClazzTree.getIdName());
                    dtoClazzTree.getRefDtoClazzSet().add(childDtoClazzTree);
                    continue;
                }
                clazzTreeMap.put(clazzName, childDtoClazzTree);
                childDtoClazzTree.setFieldName(field.getName());
                if (Objects.nonNull(dtoIgnoreProperty)) {
                    childDtoClazzTree.setFgIgnore(true);
                }
                childDtoClazzTree.setDtoClazz(refClazz);
                buildClazzTree(refClazz, childDtoClazzTree, clazzTreeMap);
                dtoClazzTree.getRefDtoClazzSet().add(childDtoClazzTree);
                continue;
            }
            if (isChild) {
                if (field.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    Type[] childrenTypeArray = parameterizedType.getActualTypeArguments();
                    if (childrenTypeArray != null && childrenTypeArray.length > 0) {
                        Class childClazz = (Class) childrenTypeArray[0];
                        String clazzName = childClazz.getName();
                        if (clazzTreeMap.containsKey(clazzName)) {
                            DtoClazzTree cacheDdtoClazzTree = clazzTreeMap.get(clazzName);
                            childDtoClazzTree.setFieldName(field.getName());
                            if (Objects.nonNull(dtoIgnoreProperty)) {
                                childDtoClazzTree.setFgIgnore(true);
                            }
                            childDtoClazzTree.setDtoClazz(cacheDdtoClazzTree.getDtoClazz());
                            childDtoClazzTree.setRefDtoClazzSet(cacheDdtoClazzTree.getRefDtoClazzSet());
                            childDtoClazzTree.setChildrenDtoClazzSet(cacheDdtoClazzTree.getChildrenDtoClazzSet());
                            childDtoClazzTree.setBaseClazzSet(cacheDdtoClazzTree.getBaseClazzSet());
                            childDtoClazzTree.setIdName(cacheDdtoClazzTree.getIdName());
                            dtoClazzTree.getChildrenDtoClazzSet().add(childDtoClazzTree);
                            continue;
                        }
                        if (Objects.nonNull(dtoIgnoreProperty)) {
                            childDtoClazzTree.setFgIgnore(true);
                        }
                        childDtoClazzTree.setFieldName(field.getName());
                        childDtoClazzTree.setDtoClazz(childClazz);
                        buildClazzTree(childClazz, childDtoClazzTree, clazzTreeMap);
                        dtoClazzTree.getChildrenDtoClazzSet().add(childDtoClazzTree);
                    }
                }
                continue;
            }
        }
    }
}
