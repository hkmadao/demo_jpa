package org.hkmadao.core.base.service;

import org.hkmadao.core.advanquery.*;
import org.hkmadao.core.advanquery.*;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.core.cache.DescManager;
import org.hkmadao.core.ApplicationContextUtil;

import org.hkmadao.core.base.AggBaseDesc;
import org.hkmadao.core.base.BaseDesc;
import org.hkmadao.core.base.desc.AttributeInfo;
import org.hkmadao.core.base.desc.EntityInfo;
import org.hkmadao.core.base.rule.RuleManager;
import org.hkmadao.core.base.utils.EntityUtil;
import org.hkmadao.core.base.utils.NameSwitchUtils;
import org.hkmadao.core.constant.DOStatus;
import org.hkmadao.core.utils.IdGenerator;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseAggService<T extends BaseEntity, ID,
        Repository extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>,
        D extends AggBaseDesc>
        implements IBaseAggService<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(BaseAggService.class);

    @Autowired
    protected Repository repository;

    @Autowired
    protected EntityManager entityManager;

    protected D desc = null;

    protected Class<ID> idClazz = null;

    public BaseAggService() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = null;
        if (genericSuperclass instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArray = parameterizedType.getActualTypeArguments();

            if (typeArray != null && typeArray.length > 0) {
                idClazz = (Class<ID>) typeArray[1];

                Class descClazz = (Class) typeArray[typeArray.length - 1];
                try {
                    desc = (D) descClazz.getConstructor().newInstance();
                    DescManager.putBaseDesc(descClazz.getTypeName(), desc);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected void addSaveRule(RuleManager<T> ruleManager) {
        this.addBeforeSaveRule(ruleManager);
        this.addAfterSaveRule(ruleManager);
    }

    protected void addBeforeSaveRule(RuleManager<T> ruleManager) {

    }

    protected void addAfterSaveRule(RuleManager<T> ruleManager) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T save(T entity) throws BusinessException {
        if (entity.getAction() == DOStatus.UNCHANGED) {
            return null;
        }
        RuleManager<T> ruleManager = new RuleManager<T>();
        addSaveRule(ruleManager);
        T before = ruleManager.before(entity);
        if (entity.getAction() == DOStatus.DELETED) {
            throw new BusinessException("不能调用更新方法删除主实体！");
        }

        if (entity.getAction() == DOStatus.NEW) {
            T save = saveForInsert(before);
            T after = ruleManager.after(save);
            return after;
        }

        T save = saveForUpdate(before);
        T after = ruleManager.after(save);
        return after;
    }

    @Override
    @Transactional
    public T insertById(T entity) throws BusinessException {
        RuleManager<T> ruleManager = new RuleManager<T>();
        addInsertRule(ruleManager);
        T before = ruleManager.before(entity);
        T save = saveForInsert(before);
        T after = ruleManager.after(save);
        return after;
    }

    @Override
    public T noIdInsert(T entity) throws BusinessException {
        ID mainEntityId = IdGenerator.generateId(idClazz);
        EntityUtil.setValueByName(entity, desc.getPkAttributeInfo().getName(), mainEntityId);
        T save = saveForInsert(entity);
        return save;
    }

    protected void addInsertRule(RuleManager<T> ruleManager) {
        this.addBeforeInsertRule(ruleManager);
        this.addAfterInsertRule(ruleManager);
    }

    protected void addBeforeInsertRule(RuleManager<T> ruleManager) {

    }

    protected void addAfterInsertRule(RuleManager<T> ruleManager) {

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(T entity) throws BusinessException {
        RuleManager<T> ruleManager = new RuleManager<T>();
        addUpdateRule(ruleManager);
        T before = ruleManager.before(entity);
        T update = saveForUpdate(before);
        T after = ruleManager.after(update);
        return after;
    }

    protected void addUpdateRule(RuleManager<T> ruleManager) {
        this.addBeforeUpdateRule(ruleManager);
        this.addAfterUpdateRule(ruleManager);
    }

    protected void addBeforeUpdateRule(RuleManager<T> ruleManager) {

    }

    protected void addAfterUpdateRule(RuleManager<T> ruleManager) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(T entity) throws BusinessException {
        RuleManager<T> ruleManager = new RuleManager<T>();
        addDeleteRule(ruleManager);
        T before = ruleManager.before(entity);

        String methodName = NameSwitchUtils.camelCase2GetMethodName(desc.getPkAttributeInfo().getName());
        ID id = (ID) EntityUtil.fetchValueByGetMethodName(entity, methodName);
        if (null == id) {
            return;
        }
        Session session = this.entityManager.unwrap(Session.class);
        session.evict(entity);
        Optional<T> repositoryOptional = repository.findById(id);
        if (!repositoryOptional.isPresent()) {
            return;
        }
        T entityManage = repositoryOptional.get();

        checkChildEntity(entityManage, desc);

        deleteAggChild(entityManage);

        repository.delete(entityManage);
        ruleManager.after(before);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(ID id) throws BusinessException {
        Optional<T> repositoryOptional = repository.findById(id);
        if (!repositoryOptional.isPresent()) {
            return;
        }
        T entityManage = repositoryOptional.get();
        RuleManager<T> ruleManager = new RuleManager<T>();
        addDeleteRule(ruleManager);
        T before = ruleManager.before(entityManage);

        checkChildEntity(entityManage, desc);

        deleteAggChild(entityManage);

        repository.delete(entityManage);
        ruleManager.after(before);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllInBatch(List<T> entity) throws BusinessException {
        for (T t : entity) {
            this.delete(t);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllById(List<ID> ids) throws BusinessException {
        for (ID id : ids) {
            this.deleteById(id);
        }
    }

    protected void addDeleteRule(RuleManager<T> ruleManager) {
        this.addBeforeDeleteRule(ruleManager);
        this.addAfterDeleteRule(ruleManager);
    }

    protected void addBeforeDeleteRule(RuleManager<T> ruleManager) {

    }

    protected void addAfterDeleteRule(RuleManager<T> ruleManager) {

    }

    /**
     * 新增实体
     *
     * @param entity 主实体
     */
    protected T saveForInsert(T entity) throws BusinessException {
        if (Objects.isNull(desc)) {
            throw new BusinessException("描述信息未设置！");
        }
        ID mainEntityId = IdGenerator.generateId(idClazz);
        EntityUtil.setValueByName(entity, desc.getPkAttributeInfo().getName(), mainEntityId);
        EntityInfo entityInfo = desc.getEntityInfo();
        EntityIsolation<T> entityIsolation = isolation(entity, mainEntityId);

        T save = entitySave(entityIsolation);

        return save;
    }

    private EntityIsolation<T> isolation(T entity, ID mainEntityId) throws BusinessException {
        Map<String, Set<BaseEntity>> aggChildrenMap = new HashMap<>();
        //设置子属性信息
        for (AttributeInfo attributeInfo : desc.getAggChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            //获取子实体信息
            String childMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
            Set<BaseEntity> subEntities = EntityUtil.fetchSetValueByGetMethodName(entity, childMethodName);
            for (BaseEntity subEntity : subEntities) {
                if (null != subEntity.getAction() && subEntity.getAction() == DOStatus.NEW) {
                    ID childId = IdGenerator.generateId(idClazz);
                    EntityUtil.setValueByName(subEntity, subDesc.getPkAttributeInfo().getName(), childId);
                }
                EntityUtil.setValueByName(subEntity, subDesc.getAggFkIdAttributeInfo().getName(), mainEntityId);
            }
            aggChildrenMap.put(attributeInfo.getName(), subEntities);
            //置空子属性
            String childSetMethodName = NameSwitchUtils.camelCase2SetMethodName(attributeInfo.getName());
            EntityUtil.setValueBySetMethodName(entity, childSetMethodName, new HashSet<>());
        }
        Map<String, BaseEntity> aggOne2OneChildrenMap = new HashMap<>();
        for (AttributeInfo attributeInfo : desc.getAggOne2OneChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            //获取子实体信息
            String childMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
            BaseEntity subEntity = EntityUtil.fetchBaseEntityValueByGetMethodName(entity, childMethodName);
            if (null != subEntity.getAction() && subEntity.getAction() == DOStatus.NEW) {
                ID childId = IdGenerator.generateId(idClazz);
                EntityUtil.setValueByName(subEntity, subDesc.getPkAttributeInfo().getName(), childId);
            }
            EntityUtil.setValueByName(subEntity, subDesc.getAggFkIdAttributeInfo().getName(), mainEntityId);
            aggOne2OneChildrenMap.put(attributeInfo.getName(), subEntity);
            //置空子属性
            String childSetMethodName = NameSwitchUtils.camelCase2SetMethodName(attributeInfo.getName());
            EntityUtil.setValueBySetMethodName(entity, childSetMethodName, null);
        }

        EntityIsolation<T> entityIsolation = new EntityIsolation<>();
        entityIsolation.setMainEntity(entity);
        entityIsolation.setChildEntityMap(aggChildrenMap);
        entityIsolation.setChildOne2OneEntityMap(aggOne2OneChildrenMap);
        return entityIsolation;
    }

    /**
     * 处理关联实体关系
     *
     * @param entity     原实体
     * @param entityDesc 原实体描述信息
     * @throws BusinessException
     */
    private void checkChildEntity(BaseEntity entity, BaseDesc entityDesc) throws BusinessException {
        //设置外键关联实体
        List<AttributeInfo> childAttributeInfos = entityDesc.getNormalChildren();
        String mainIdAttributeName = entityDesc.getPkAttributeInfo().getName();
        String methodName = NameSwitchUtils.camelCase2GetMethodName(mainIdAttributeName);
        ID idMain = (ID) EntityUtil.fetchValueByGetMethodName(entity, methodName);
        if (null != idMain) {
            Optional<T> pEntityOption = this.repository.findById(idMain);
            if (pEntityOption.isPresent()) {
                T pEntity = pEntityOption.get();
                for (AttributeInfo attributeInfo : childAttributeInfos) {
                    String childAttrMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
                    Set<BaseEntity> childEntities = EntityUtil.fetchSetValueByGetMethodName(pEntity,
                            childAttrMethodName);
                    if (!CollectionUtils.isEmpty(childEntities)) {
                        throw new BusinessException(attributeInfo.getName() + ":数据被引用，不能删除！");
                    }
                }
            }
        }
    }

    /**
     * 处理关联实体关系
     *
     * @param entity     原实体
     * @param entityDesc 原实体描述信息
     * @throws BusinessException
     */
    private void subCheckChildEntity(BaseEntity entity, BaseDesc entityDesc) throws BusinessException {
        //设置外键关联实体
        List<AttributeInfo> childAttributeInfos = entityDesc.getNormalChildren();
        String mainIdAttributeName = entityDesc.getPkAttributeInfo().getName();
        String methodName = NameSwitchUtils.camelCase2GetMethodName(mainIdAttributeName);
        ID idMain = (ID) EntityUtil.fetchValueByGetMethodName(entity, methodName);
        if (null != idMain) {
            for (AttributeInfo attributeInfo : childAttributeInfos) {
                String lower = NameSwitchUtils.firstChar2Lower(attributeInfo.getOutEntityName());
                String repoName = lower + "Repository";
                JpaRepository<BaseEntity, ID> refRepository =
                        (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(repoName);
                Optional<BaseEntity> pEntityOption = refRepository.findById(idMain);
                if (pEntityOption.isPresent()) {
                    BaseEntity pEntity = pEntityOption.get();
                    String childAttrMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
                    Set<BaseEntity> childEntities = EntityUtil.fetchSetValueByGetMethodName(pEntity,
                            childAttrMethodName);
                    if (!CollectionUtils.isEmpty(childEntities)) {
                        throw new BusinessException(attributeInfo.getName() + ":数据被引用，不能删除！");
                    }
                }
            }
        }
    }

    /**
     * 更新实体
     *
     * @param entity 主实体
     */
    protected T saveForUpdate(T entity) throws BusinessException {
        String methodName = NameSwitchUtils.camelCase2GetMethodName(desc.getPkAttributeInfo().getName());
        ID idMain = (ID) EntityUtil.fetchValueByGetMethodName(entity, methodName);
        if (null == idMain) {
            return null;
        }

        deleteAggChildByAction(entity);

        EntityIsolation<T> entityIsolation = isolation(entity, idMain);

        T save = entitySave(entityIsolation);

        return save;
    }

    private T entitySave(EntityIsolation<T> entityIsolation) throws BusinessException {
        T save = repository.save(entityIsolation.getMainEntity());

        //设置子属性信息
        for (AttributeInfo attributeInfo : desc.getAggChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            JpaRepository<BaseEntity, ID> refRepository =
                    (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(subDesc
                            .getEntityInfo().getName() + "Repository");
            Set<BaseEntity> subEntities = entityIsolation.getChildEntityMap().get(attributeInfo.getName());
            if (!CollectionUtils.isEmpty(subEntities)) {
                refRepository.saveAll(subEntities);
            }
        }

        for (AttributeInfo attributeInfo : desc.getAggOne2OneChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            JpaRepository<BaseEntity, ID> refRepository =
                    (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(subDesc
                            .getEntityInfo().getName() + "Repository");
            BaseEntity child = entityIsolation.getChildOne2OneEntityMap().get(attributeInfo.getName());
            if (Objects.nonNull(child)) {
                refRepository.save(child);
            }
        }
        return save;
    }

    private void deleteAggChildByAction(T entity) throws BusinessException {
        for (AttributeInfo attributeInfo : desc.getAggChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            String childMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
            Set<BaseEntity> aggSubEntities = EntityUtil.fetchSetValueByGetMethodName(entity, childMethodName);
            if (CollectionUtils.isEmpty(aggSubEntities)) {
                continue;
            }
            Set<BaseEntity> deleteBaseEntities = new HashSet<>();
            for (BaseEntity aggSubEntity : aggSubEntities) {
                if (aggSubEntity.getAction() == DOStatus.DELETED) {
                    String subPkName = subDesc.getPkAttributeInfo().getName();
                    String subPkMethodName = NameSwitchUtils.camelCase2GetMethodName(subPkName);
                    ID subPk = (ID) EntityUtil.fetchValueByGetMethodName(aggSubEntity, subPkMethodName);
                    JpaRepository<BaseEntity, ID> refRepository =
                            (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(subDesc
                                    .getEntityInfo().getName() + "Repository");
                    subCheckChildEntity(aggSubEntity, subDesc);
                    refRepository.delete(aggSubEntity);
                    deleteBaseEntities.add(aggSubEntity);
                }
            }
            aggSubEntities.removeAll(deleteBaseEntities);
            aggSubEntities.remove(null);
            EntityUtil.setValueByName(entity, attributeInfo.getName(), aggSubEntities);
        }
        for (AttributeInfo attributeInfo : desc.getAggOne2OneChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            String childMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
            BaseEntity aggSingleChild = EntityUtil.fetchBaseEntityValueByGetMethodName(entity, childMethodName);
            if (null == aggSingleChild) {
                continue;
            }
            if (aggSingleChild.getAction() == DOStatus.DELETED) {
                String subPkName = subDesc.getPkAttributeInfo().getName();
                String subPkMethodName = NameSwitchUtils.camelCase2GetMethodName(subPkName);
                ID subPk = (ID) EntityUtil.fetchValueByGetMethodName(aggSingleChild, subPkMethodName);
                JpaRepository<BaseEntity, ID> refRepository =
                        (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(subDesc
                                .getEntityInfo().getName() + "Repository");
                subCheckChildEntity(aggSingleChild, subDesc);
                refRepository.delete(aggSingleChild);
                EntityUtil.setValueByName(entity, subPkName, null);
            }
        }
    }

    private void deleteAggChild(T entity) throws BusinessException {
        for (AttributeInfo attributeInfo : desc.getAggChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            String subMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
            Set<BaseEntity> aggSubEntities = EntityUtil.fetchSetValueByGetMethodName(entity, subMethodName);
            if (CollectionUtils.isEmpty(aggSubEntities)) {
                continue;
            }
            Set<BaseEntity> deleteBaseEntities = new HashSet<>();
            for (BaseEntity aggSubEntity : aggSubEntities) {
                String subPkName = subDesc.getPkAttributeInfo().getName();
                ID subPk = (ID) EntityUtil.fetchValueByGetMethodName(aggSubEntity,
                        NameSwitchUtils.camelCase2GetMethodName(subPkName));
                JpaRepository<BaseEntity, ID> refRepository =
                        (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(subDesc
                                .getEntityInfo().getName() + "Repository");
                subCheckChildEntity(aggSubEntity, subDesc);
                refRepository.delete(aggSubEntity);
                deleteBaseEntities.add(aggSubEntity);
            }
            aggSubEntities.removeAll(deleteBaseEntities);
            EntityUtil.setValueByName(entity, attributeInfo.getName(), new HashSet<>());
        }
        for (AttributeInfo attributeInfo : desc.getAggOne2OneChildren()) {
            String refDescClassName =
                    desc.getClass().getPackage().getName() + "." + attributeInfo.getAggRefDescClassName();
            //子实体的描述信息
            AggBaseDesc subDesc = (AggBaseDesc) DescManager.fetchBaseDesc(refDescClassName);
            String childMethodName = NameSwitchUtils.camelCase2GetMethodName(attributeInfo.getName());
            BaseEntity aggSingleChild = EntityUtil.fetchBaseEntityValueByGetMethodName(entity, childMethodName);
            if (null == aggSingleChild) {
                continue;
            }
            String subPkName = subDesc.getPkAttributeInfo().getName();
            ID subPk = (ID) EntityUtil.fetchValueByGetMethodName(aggSingleChild,
                    NameSwitchUtils.camelCase2GetMethodName(subPkName));
            JpaRepository<BaseEntity, ID> refRepository =
                    (JpaRepository<BaseEntity, ID>) ApplicationContextUtil.getBeanByName(subDesc
                            .getEntityInfo().getName() + "Repository");
            subCheckChildEntity(aggSingleChild, subDesc);
            refRepository.delete(aggSingleChild);
            EntityUtil.setValueByName(entity, attributeInfo.getName(), null);
        }
    }

    @Override
    public T findById(ID id) throws BusinessException {
        Optional<T> optionalT = repository.findById(id);
        if (optionalT.isPresent()) {
            T entity = optionalT.get();
            RuleManager<T> ruleManager = new RuleManager<T>();
            addAfterFindRule(ruleManager);
            T after = ruleManager.after(entity);
            return after;
        }
        throw new BusinessException("未找到数据");
    }

    @Override
    public List<T> findAllById(List<ID> ids) throws BusinessException {
        return repository.findAllById(ids);
    }

    @Override
    public T getById(ID id) throws BusinessException {
        Optional<T> optionalT = repository.findById(id);
        if (optionalT.isPresent()) {
            T entity = optionalT.get();
            RuleManager<T> ruleManager = new RuleManager<T>();
            addAfterFindRule(ruleManager);
            T after = ruleManager.after(entity);
            return after;
        }
        return null;
    }

    protected void addAfterFindRule(RuleManager<T> ruleManager) {

    }

    @Override
    public List<T> findAll() throws BusinessException {
        Sort sort = Sort.by(Sort.Order.desc(desc.getPkAttributeInfo().getName()));
        List<T> all = repository.findAll(sort);
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        return ruleManager.after(all);
    }

    @Override
    public List<T> findAll(AqOrder... aqOrderArr) throws BusinessException {
        if (aqOrderArr == null) {
            aqOrderArr = new AqOrder[0];
        }
        List<AqOrder> aqOrders = Arrays.asList(aqOrderArr);
        List<Sort.Order> orders = new ArrayList<>();
        Sort sort;
        if (CollectionUtils.isEmpty(aqOrders)) {
            sort = Sort.by(Sort.Order.desc(desc.getPkAttributeInfo().getName()));
        } else {
            aqOrders.forEach(aqOrder -> {
                if (Sort.Direction.ASC.name().equals(aqOrder.getDirection())) {
                    orders.add(Sort.Order.asc(aqOrder.getProperty()));
                    return;
                }
                orders.add(Sort.Order.desc(aqOrder.getProperty()));
            });
            sort = Sort.by(orders);
        }
        List<T> all = repository.findAll(sort);
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        return ruleManager.after(all);
    }

    @Override
    public List<T> findAll(List<AqOrder> aqOrders) throws BusinessException {
        List<Sort.Order> orders = new ArrayList<>();
        Sort sort;
        if (CollectionUtils.isEmpty(aqOrders)) {
            sort = Sort.by(Sort.Order.desc(desc.getPkAttributeInfo().getName()));
        } else {
            aqOrders.forEach(aqOrder -> {
                if (Sort.Direction.ASC.name().equals(aqOrder.getDirection())) {
                    orders.add(Sort.Order.asc(aqOrder.getProperty()));
                    return;
                }
                orders.add(Sort.Order.desc(aqOrder.getProperty()));
            });
            sort = Sort.by(orders);
        }
        List<T> all = repository.findAll(sort);
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        return ruleManager.after(all);
    }

    @Override
    public List<T> findAll(T entity) throws BusinessException {
        Sort sort = Sort.by(Sort.Order.desc(desc.getPkAttributeInfo().getName()));
        List<T> all = repository.findAll(Example.of(entity), sort);
        return all;
    }

    @Override
    public List<T> findAll(EntityCondition<T> condition) throws BusinessException {
        List<Sort.Order> orders = new ArrayList<>();
        Sort sort;
        if (CollectionUtils.isEmpty(condition.getOrders())) {
            sort = Sort.by(Sort.Order.desc(desc.getPkAttributeInfo().getName()));
        } else {
            condition.getOrders().forEach(aqOrder -> {
                if (Sort.Direction.ASC.name().equals(aqOrder.getDirection())) {
                    orders.add(Sort.Order.asc(aqOrder.getProperty()));
                    return;
                }
                orders.add(Sort.Order.desc(aqOrder.getProperty()));
            });
            sort = Sort.by(orders);
        }
        List<T> all = repository.findAll(Example.of(condition.getEntity()), sort);
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        return ruleManager.after(all);
    }

    @Override
    public List<T> findAll(Example<T> example, Sort sort) throws BusinessException {
        if (Objects.isNull(sort)) {
            sort = Sort.by(Sort.Order.desc(desc.getPkAttributeInfo().getName()));
        }
        List<T> all = repository.findAll(example, sort);
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        return ruleManager.after(all);
    }

    @Override
    public boolean exists(Example<T> example) throws BusinessException {
        return repository.exists(example);
    }

    @Override
    public AqPageInfo<T> page(AqPageInfoInput pageInfoInput) throws BusinessException {
        Page<T> page = repository.findAll((Specification<T>) (root, query, criteriaBuilder) -> {
            if (Objects.isNull(pageInfoInput.getLogicNode()) || !StringUtils.hasLength(pageInfoInput.getLogicNode().getLogicOperatorCode())) {
                List<AqOrder> aqOrders = pageInfoInput.getOrders();
                Map<String, From<T, T>> fromMap = new HashMap<>();
                //加入排序条件的连表信息
                joinOrderFromMap(root, fromMap, aqOrders);
                List<Order> orders = getOrders(root, aqOrders, fromMap, criteriaBuilder);
                return query.distinct(true).orderBy(orders).getRestriction();
            }
            List<AqOrder> aqOrders = pageInfoInput.getOrders();
            //构建连表信息
            Map<String, From<T, T>> fromMap = createFromMap(root, pageInfoInput.getLogicNode());
            //加入排序条件的连表信息
            joinOrderFromMap(root, fromMap, aqOrders);
            List<Predicate> predicates = AqLogicNode.recursionNode(fromMap, root, criteriaBuilder,
                    pageInfoInput.getLogicNode());
            if (CollectionUtils.isEmpty(predicates)) {
                return query.getRestriction();
            }
            List<Order> orders = getOrders(root, aqOrders, fromMap, criteriaBuilder);
            return query
                    .distinct(true)
                    .where(predicates.get(0))
                    .orderBy(orders)
                    .getRestriction();
        }, PageRequest.of(pageInfoInput.getPageIndex() - 1, pageInfoInput.getPageSize()));
        AqPageInfo pageInfo = pageToPageInfo(page, pageInfoInput);
        return pageInfo;
    }

    @Override
    public List<T> findAll(AqCondition condition) throws BusinessException {
        List<T> dataList = repository.findAll((Specification<T>) (root, query, criteriaBuilder) -> {
            if (Objects.isNull(condition)) {
                return query.distinct(true).getRestriction();
            }
            AqLogicNode logicNode = condition.getLogicNode();
            List<AqOrder> aqOrders = condition.getOrders();
            if (Objects.isNull(logicNode) || !StringUtils.hasLength(logicNode.getLogicOperatorCode())) {
                Map<String, From<T, T>> fromMap = new HashMap<>();
                //加入排序条件的连表信息
                joinOrderFromMap(root, fromMap, aqOrders);
                List<Order> orders = getOrders(root, aqOrders, fromMap, criteriaBuilder);
                return query.distinct(true).orderBy(orders).getRestriction();
            }
            //构建连表信息
            Map<String, From<T, T>> fromMap = createFromMap(root, logicNode);
            //加入排序条件的连表信息
            joinOrderFromMap(root, fromMap, aqOrders);
            List<Predicate> predicates = AqLogicNode.recursionNode(fromMap, root, criteriaBuilder, logicNode);
            if (CollectionUtils.isEmpty(predicates)) {
                return query.distinct(true).getRestriction();
            }
            List<Order> orders = getOrders(root, aqOrders, fromMap, criteriaBuilder);
            return query
                    .distinct(true)
                    .where(predicates.get(0))
                    .orderBy(orders)
                    .getRestriction();
        });
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        return ruleManager.after(dataList);
    }

    @Override
    public T findOne(T entity) throws BusinessException {
        List<T> list = this.findAll(entity);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("未找到数据");
        }
        if (list.size() > 1) {
            throw new BusinessException("找到多条数据");
        }
        return list.get(0);
    }

    @Override
    public T findOne(EntityCondition<T> condition) throws BusinessException {
        List<T> list = this.findAll(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("未找到数据");
        }
        if (list.size() > 1) {
            throw new BusinessException("找到多条数据");
        }
        return list.get(0);
    }

    @Override
    public long count(AqCondition condition) throws BusinessException {
        return repository.count((Specification<T>) (root, query, criteriaBuilder) -> {
            if (Objects.isNull(condition)) {
                return query.distinct(true).getRestriction();
            }
            AqLogicNode logicNode = condition.getLogicNode();
            List<AqOrder> aqOrders = condition.getOrders();
            if (Objects.isNull(logicNode) || !StringUtils.hasLength(logicNode.getLogicOperatorCode())) {
                return query.distinct(true).getRestriction();
            }
            //构建连表信息
            Map<String, From<T, T>> fromMap = createFromMap(root, logicNode);
            //加入排序条件的连表信息
            joinOrderFromMap(root, fromMap, aqOrders);
            List<Predicate> predicates = AqLogicNode.recursionNode(fromMap, root, criteriaBuilder, logicNode);
            if (CollectionUtils.isEmpty(predicates)) {
                return query.distinct(true).getRestriction();
            }
            List<Order> orders = getOrders(root, aqOrders, fromMap, criteriaBuilder);
            return query
                    .distinct(true)
                    .where(predicates.get(0))
                    .orderBy(orders)
                    .getRestriction();
        });
    }

    /**
     * 获取排序条件
     *
     * @param root     聚合根
     * @param aqOrders 排序条件
     * @param fromMap  连表信息
     * @return
     */
    private List<Order> getOrders(Root<T> root, List<AqOrder> aqOrders, Map<String, From<T, T>> fromMap,
                                  CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new ArrayList<>();
        if (!CollectionUtils.isEmpty(aqOrders)) {
            aqOrders.forEach(aqOrder -> {
                if (aqOrder.getProperty().contains(".")) {
                    String subEntityLinkName = aqOrder.getProperty().substring(0,
                            aqOrder.getProperty().lastIndexOf("."));
                    String property = aqOrder.getProperty().substring(aqOrder.getProperty().lastIndexOf(".") + 1);
                    Order order;
                    if (Sort.Direction.DESC.name().equalsIgnoreCase(aqOrder.getDirection())) {
                        order = criteriaBuilder.desc(fromMap.get(subEntityLinkName).get(property));
                    } else {
                        order = criteriaBuilder.asc(fromMap.get(subEntityLinkName).get(property));
                    }
                    orders.add(order);
                    return;
                }
                Order order;
                if (Sort.Direction.DESC.name().equalsIgnoreCase(aqOrder.getDirection())) {
                    order = criteriaBuilder.desc(root.get(aqOrder.getProperty()));
                } else {
                    order = criteriaBuilder.asc(root.get(aqOrder.getProperty()));
                }
                orders.add(order);
            });

        }
        return orders;
    }

    /**
     * 根据查询条件构建连表信息
     *
     * @param root 聚合根
     * @return
     */
    private Map<String, From<T, T>> createFromMap(Root<T> root, AqLogicNode aqLogicNode) {
        List<AqFilterNode> filterNodes = new ArrayList<>();
        selectFilterNodes(aqLogicNode, filterNodes);
        //过滤掉主表的查询条件
        filterNodes =
                filterNodes.stream().filter(aqFilterNode -> aqFilterNode.getName().contains(".")).collect(Collectors.toList());
        //根据属性名中含有的”.“升序排序
        filterNodes.sort((o1, o2) -> o1.getName().split(".").length > o2.getName().split(".").length ? 0 : 1);

        Map<String, From<T, T>> fromMap = new HashMap<>();
        filterNodes.forEach(aqFilterNode -> {
            String entityLinkName = aqFilterNode.getName().substring(0, aqFilterNode.getName().lastIndexOf("."));
            entityLinkNameToFromMap(root, fromMap, entityLinkName);
        });
        return fromMap;
    }

    /**
     * 加入排序条件的连表信息
     *
     * @param root    聚合根
     * @param fromMap 连表信息
     * @param orders  排序条件
     */
    private void joinOrderFromMap(Root<T> root, Map<String, From<T, T>> fromMap, List<AqOrder> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }
        orders.sort((o1, o2) -> o1.getProperty().split(".").length > o2.getProperty().split(".").length ? 0 : 1);
        orders.forEach(aqOrder -> {
            if (Objects.isNull(aqOrder.getProperty()) || !aqOrder.getProperty().contains(".")) {
                return;
            }
            String entityLinkName = aqOrder.getProperty().substring(0, aqOrder.getProperty().lastIndexOf("."));
            entityLinkNameToFromMap(root, fromMap, entityLinkName);
        });
        return;
    }

    /**
     * 帅选出过滤条件
     *
     * @param aqLogicNode 查询条件
     * @param results     查询过滤条件列表
     */
    private void selectFilterNodes(AqLogicNode aqLogicNode, List<AqFilterNode> results) {
        results.addAll(aqLogicNode.getFilterNodes());
        if (Objects.nonNull(aqLogicNode.getLogicNode())) {
            selectFilterNodes(aqLogicNode.getLogicNode(), results);
        }
    }

    /**
     * 从单个过滤条件构建连表信息
     *
     * @param root           聚合根
     * @param fromMap        连表信息map
     * @param entityLinkName 过滤条件名称
     */
    private void entityLinkNameToFromMap(Root<T> root, Map<String, From<T, T>> fromMap, String entityLinkName) {
        if (!fromMap.containsKey(entityLinkName)) {
            if (entityLinkName.contains(".")) {
                String startEntityLinkName = entityLinkName.substring(0, entityLinkName.lastIndexOf("."));
                String endEntityLinkName = entityLinkName.substring(entityLinkName.lastIndexOf(".") + 1);
                entityLinkNameToFromMap(root, fromMap, startEntityLinkName);
                fromMap.put(entityLinkName,
                        fromMap.get(startEntityLinkName).join(endEntityLinkName,
                                JoinType.LEFT));
                return;
            }
            fromMap.put(entityLinkName, root.join(entityLinkName, JoinType.LEFT));
        }
    }

    /**
     * 分页信息转换
     *
     * @param page          原始分页对象
     * @param pageInfoInput 目标分页对象
     */
    protected AqPageInfo pageToPageInfo(Page<T> page, AqPageInfoInput pageInfoInput) throws BusinessException {
        pageInfoInput.setTotalCount(page.getTotalElements());
        RuleManager<T> ruleManager = new RuleManager<T>();
        addAfterFindRule(ruleManager);
        List<T> after = ruleManager.after(page.getContent());
        AqPageInfo<T> pageInfo = new AqPageInfo<>();
        pageInfo.setPageInfoInput(pageInfoInput);
        pageInfo.setDataList(after);
        return pageInfo;
    }

}
