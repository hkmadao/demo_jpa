package org.hkmadao.core.base.controller;

import org.hkmadao.core.advanquery.*;
import org.hkmadao.core.advanquery.*;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.core.base.utils.EntityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hkmadao.core.base.DtoClazzTree;
import org.hkmadao.core.base.service.IBaseService;
import org.hkmadao.core.base.utils.EntityConvertUtil;
import org.hkmadao.core.base.utils.NameSwitchUtils;
import jakarta.annotation.PostConstruct;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import jakarta.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class BaseController<T extends BaseEntity, ID, PO extends BaseEntity, VO extends BaseEntity,
        S extends IBaseService<T, ID>> {

    @Autowired
    protected S service;

    @Autowired
    protected EntityManager entityManager;

    private DtoClazzTree dtoClazzTree;

    protected Class<T> tClazz;

    public BaseController() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = null;
        if (genericSuperclass instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArray = parameterizedType.getActualTypeArguments();

            if (typeArray != null && typeArray.length > 0) {
                tClazz = (Class<T>) typeArray[0];
            }
        }
    }

    protected T poToEntity(PO po) throws BusinessException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String poString = null;
        try {
            poString = objectMapper.writeValueAsString(po);
            T t = objectMapper.readValue(poString, tClazz);
            return t;
        } catch (JsonProcessingException e) {
            throw new BusinessException("参数转换异常：", e);
        }
    }

    @PostConstruct
    private void init() {

    }

    public VO add(PO po) throws BusinessException {
        T entity = poToEntity(po);
        T save = service.noIdInsert(entity);
        //TODO 待优化
//        entityManager.clear();
        Session session = this.entityManager.unwrap(Session.class);
        session.evict(save);
        cacheStructTree();
        String getMethodName = NameSwitchUtils.camelCase2GetMethodName(dtoClazzTree.getIdName());
        T byId = service.findById((ID) EntityUtil.fetchValueByGetMethodName(save, getMethodName));
        return afterFindProcess(byId);
    }

    public VO update(PO po) throws BusinessException {
        T entity = poToEntity(po);
        T save = service.update(entity);
        //TODO 待优化
//        entityManager.clear();
        Session session = this.entityManager.unwrap(Session.class);
        session.evict(save);
        cacheStructTree();
        String getMethodName = NameSwitchUtils.camelCase2GetMethodName(dtoClazzTree.getIdName());
        T byId = service.findById((ID) EntityUtil.fetchValueByGetMethodName(save, getMethodName));
        return afterFindProcess(byId);
    }

    public void remove(PO po) throws BusinessException {
        T entity = poToEntity(po);
        service.deleteCheckRelation(entity);
    }

    public void batchRemove(List<T> entities) throws BusinessException {
        for (int i = 0; i < entities.size(); i++) {
            service.deleteCheckRelation(entities.get(i));
        }
    }

    public VO getById(ID id) throws BusinessException {
        return afterFindProcess(service.findById(id));
    }

    public List<VO> getAll() throws BusinessException {
        return afterFindProcess(service.findAll());
    }

    public List<VO> getAll(List<AqOrder> aqOrders) throws BusinessException {
        return afterFindProcess(service.findAll(aqOrders));
    }

    public List<VO> findAll(PO po) throws BusinessException {
        T entity = poToEntity(po);
        return afterFindProcess(service.findAll(entity));
    }

    public List<T> findAll(EntityCondition<T> entityCondition) throws BusinessException {
        return service.findAll(entityCondition);
    }

    /**
     * 高级查询
     *
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<VO> aq(AqCondition condition) throws BusinessException {
        List<T> all = service.findAll(condition);
        return afterFindProcess(all);
    }

    protected VO afterFindProcess(T entity) throws BusinessException {
        if (Objects.isNull(dtoClazzTree)) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = null;
            if (genericSuperclass instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) genericSuperclass;
                Type[] typeArray = parameterizedType.getActualTypeArguments();

                if (typeArray != null && typeArray.length > 0) {
                    Class clazz = (Class) typeArray[typeArray.length - 2];
                    dtoClazzTree = new DtoClazzTree();
                    dtoClazzTree.setFieldName(null);
                    dtoClazzTree.setDtoClazz(clazz);
                    Map<String, DtoClazzTree> clazzTreeMap = new HashMap<>();
                    EntityConvertUtil.buildClazzTree(clazz, dtoClazzTree, clazzTreeMap);
                }
            }
        }
        if (Objects.isNull(entity)) {
            return null;
        }
        Map<String, Map<String, BaseEntity>> cacheObjMap = new HashMap<>();
        try {
            BaseEntity dto = (BaseEntity) dtoClazzTree.getDtoClazz().getConstructor().newInstance();
            EntityConvertUtil.buildDtoTree(entity, dto, dtoClazzTree, cacheObjMap);
            return (VO) dto;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new BusinessException("转换VO失败", e);
        }
    }

    /**
     * 高级分页查询
     *
     * @param pageInfoInput 分页查询条件
     * @return 分页列表数据
     */
    public AqPageInfo<VO> aqPage(AqPageInfoInput pageInfoInput) throws BusinessException {
        AqPageInfo<T> page = service.page(pageInfoInput);
        List<VO> VOs = afterFindProcess(page.getDataList());
        AqPageInfo<VO> pageVO = new AqPageInfo<>();
        pageVO.setPageInfoInput(page.getPageInfoInput());
        pageVO.setDataList(VOs);
        return pageVO;
    }

    protected List<VO> afterFindProcess(List<T> entities) throws BusinessException {
        List<VO> ends = new ArrayList<>();
        if (CollectionUtils.isEmpty(entities)) {
            return ends;
        }
        for (T entity : entities) {
            ends.add(afterFindProcess(entity));
        }
        return ends;
    }

    private void cacheStructTree() {
        if (Objects.isNull(dtoClazzTree)) {
            Type genericSuperclass = getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = null;
            if (genericSuperclass instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) genericSuperclass;
                Type[] typeArray = parameterizedType.getActualTypeArguments();

                if (typeArray != null && typeArray.length > 0) {
                    Class clazz = (Class) typeArray[typeArray.length - 2];
                    dtoClazzTree = new DtoClazzTree();
                    dtoClazzTree.setFieldName(null);
                    dtoClazzTree.setDtoClazz(clazz);
                    Map<String, DtoClazzTree> clazzTreeMap = new HashMap<>();
                    EntityConvertUtil.buildClazzTree(clazz, dtoClazzTree, clazzTreeMap);
                }
            }
        }
    }

}
