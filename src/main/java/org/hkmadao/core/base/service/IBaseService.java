package org.hkmadao.core.base.service;

import org.hkmadao.core.advanquery.*;
import org.hkmadao.core.advanquery.*;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.core.base.BusinessException;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IBaseService<T extends BaseEntity, ID> {

    T update(T entity) throws BusinessException;

    /**
     * id通过数据传入
     *
     * @param entity 实体信息
     * @return
     * @throws BusinessException
     */
    T insertById(T entity) throws BusinessException;

    /**
     * id通过后台生成
     *
     * @param entity 实体信息
     * @return
     * @throws BusinessException
     */
    T noIdInsert(T entity) throws BusinessException;

    /**
     * 不检查关联关系删除
     *
     * @param entity
     * @throws BusinessException
     */
    void deleteNoCheckRelation(T entity) throws BusinessException;

    /**
     * 检查关联关系然后删除
     *
     * @param entity
     * @throws BusinessException
     */
    void deleteCheckRelation(T entity) throws BusinessException;

    /**
     * 通过id删除数据，会触发直接关联数据的级联操作
     *
     * @param id
     * @throws BusinessException
     */
    void deleteById(ID id) throws BusinessException;

    void deleteAllInBatch(Iterable<T> entity) throws BusinessException;

    void deleteAllById(Iterable<ID> ids) throws BusinessException;

    /**
     * 根据ID查询记录,未找到数据抛异常
     *
     * @param id
     * @return 记录数据
     */
    T findById(ID id) throws BusinessException;

    /**
     * 根据ID查询记录,未找到数据返回null
     *
     * @param id
     * @return 记录数据
     */
    T getById(ID id) throws BusinessException;

    /**
     * 根据ID集合查询记录
     *
     * @param ids id集合
     * @return 记录数据
     */
    List<T> findAllById(Iterable<ID> ids) throws BusinessException;

    /**
     * 查询所有记录
     *
     * @return 记录数据
     */
    List<T> findAll() throws BusinessException;

    /**
     * 查询所有记录
     *
     * @return 记录数据
     */
    List<T> findAll(AqOrder... orders) throws BusinessException;

    /**
     * 查询所有记录
     *
     * @return 记录数据
     */
    List<T> findAll(List<AqOrder> orders) throws BusinessException;

    /**
     * 根据非空属性条件查询数据
     *
     * @param entity 条件实体
     * @return 记录数据
     */
    List<T> findAll(T entity) throws BusinessException;

    /**
     * 根据非空属性条件查询唯一数据
     *
     * @param entity 条件实体
     * @return 唯一记录
     */
    T findOne(T entity) throws BusinessException;

    /**
     * 根据非空属性条件查询数据
     *
     * @param condition 带排序的条件实体
     * @return 记录数据
     */
    List<T> findAll(EntityCondition<T> condition) throws BusinessException;

    /**
     * 根据非空属性条件查询唯一数据
     *
     * @param condition 带排序的条件实体
     * @return 唯一记录
     */
    T findOne(EntityCondition<T> condition) throws BusinessException;

    /**
     * 根据样例条件查询数据
     *
     * @param example 样例条件
     * @param sort    排序条件
     * @return 记录数据
     */
    List<T> findAll(Example<T> example, Sort sort) throws BusinessException;

    /**
     * 根据样例条件查询数据是否存在
     *
     * @param example 样例条件
     * @return 记录数据
     */
    boolean exists(Example<T> example) throws BusinessException;

    /**
     * 聚合分页查询
     *
     * @param pageInfoInput 查询条件
     * @return 分页对象信息
     */
    AqPageInfo<T> page(AqPageInfoInput pageInfoInput) throws BusinessException;

    /**
     * 聚合查询所有数据
     *
     * @param condition 查询条件
     * @return
     */
    List<T> findAll(AqCondition condition) throws BusinessException;

    /**
     * 查询数据记录数
     *
     * @param condition 查询条件
     * @return
     */
    long count(AqCondition condition) throws BusinessException;
}
