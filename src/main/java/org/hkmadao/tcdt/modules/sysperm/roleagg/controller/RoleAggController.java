package org.hkmadao.tcdt.modules.sysperm.roleagg.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseAggController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
import org.hkmadao.tcdt.modules.sysperm.roleagg.dto.agg.vo.RoleVO;
import org.hkmadao.tcdt.modules.sysperm.roleagg.dto.agg.po.RolePO;
import org.hkmadao.tcdt.modules.sysperm.roleagg.service.IRoleAggService;

@RestController
@RequestMapping("roleAgg")
public class RoleAggController extends BaseAggController<Role, String, RolePO, RoleVO, IRoleAggService> {

    @Override
    protected RoleVO afterFindProcess(Role entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("save")
    public RoleVO save(@RequestBody RolePO entityPO) throws BusinessException {
        return super.save(entityPO);
    }

    @Override
    @PostMapping("update")
    public RoleVO update(@RequestBody RolePO entityPO) throws BusinessException {
        return super.update(entityPO);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody RolePO entityPO) throws BusinessException {
        super.remove(entityPO);
    }

    @Override
    @PostMapping("batchRemove")
    public void batchRemove(@RequestBody List<Role> entities) throws BusinessException {
        super.batchRemove(entities);
    }

    @Override
    @GetMapping("getById/{id}")
    public RoleVO getById(@PathVariable(value = "id") String s) throws BusinessException {
        return super.getById(s);
    }

    @Override
    @PostMapping("findAll")
    public List<RoleVO> findAll(@RequestBody RolePO entityPO) throws BusinessException {
        return super.findAll(entityPO);
    }

    @Override
    @PostMapping("aq")
    public List<RoleVO> aq(@RequestBody(required = false) AqCondition condition) throws BusinessException {
        return super.aq(condition);
    }

    @Override
    @PostMapping("aqPage")
    public AqPageInfo<RoleVO> aqPage(@RequestBody(required = false) AqPageInfoInput pageInfoInput) throws BusinessException {
        return super.aqPage(pageInfoInput);
    }
}
