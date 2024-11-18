package org.hkmadao.tcdt.modules.sysperm.role.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
import org.hkmadao.tcdt.modules.sysperm.role.dto.vo.RoleVO;
import org.hkmadao.tcdt.modules.sysperm.role.dto.po.RolePO;
import org.hkmadao.tcdt.modules.sysperm.role.service.IRoleService;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController<Role, String, RolePO, RoleVO, IRoleService> {

    @Override
    protected RoleVO afterFindProcess(Role entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("add")
    public RoleVO add(@RequestBody RolePO entityPO) throws BusinessException {
        return super.add(entityPO);
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
    @PostMapping("getAll")
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