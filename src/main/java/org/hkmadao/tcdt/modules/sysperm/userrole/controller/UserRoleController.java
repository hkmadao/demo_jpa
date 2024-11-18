package org.hkmadao.tcdt.modules.sysperm.userrole.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.entity.UserRole;
import org.hkmadao.tcdt.modules.sysperm.userrole.dto.vo.UserRoleVO;
import org.hkmadao.tcdt.modules.sysperm.userrole.dto.po.UserRolePO;
import org.hkmadao.tcdt.modules.sysperm.userrole.service.IUserRoleService;

@RestController
@RequestMapping("userRole")
public class UserRoleController extends BaseController<UserRole, String, UserRolePO, UserRoleVO, IUserRoleService> {

    @Override
    protected UserRoleVO afterFindProcess(UserRole entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("add")
    public UserRoleVO add(@RequestBody UserRolePO entityPO) throws BusinessException {
        return super.add(entityPO);
    }

    @Override
    @PostMapping("update")
    public UserRoleVO update(@RequestBody UserRolePO entityPO) throws BusinessException {
        return super.update(entityPO);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody UserRolePO entityPO) throws BusinessException {
        super.remove(entityPO);
    }

    @Override
    @PostMapping("batchRemove")
    public void batchRemove(@RequestBody List<UserRole> entities) throws BusinessException {
        super.batchRemove(entities);
    }

    @Override
    @GetMapping("getById/{id}")
    public UserRoleVO getById(@PathVariable(value = "id") String s) throws BusinessException {
        return super.getById(s);
    }

    @Override
    @PostMapping("getAll")
    public List<UserRoleVO> findAll(@RequestBody UserRolePO entityPO) throws BusinessException {
        return super.findAll(entityPO);
    }

    @Override
    @PostMapping("aq")
    public List<UserRoleVO> aq(@RequestBody(required = false) AqCondition condition) throws BusinessException {
        return super.aq(condition);
    }

    @Override
    @PostMapping("aqPage")
    public AqPageInfo<UserRoleVO> aqPage(@RequestBody(required = false) AqPageInfoInput pageInfoInput) throws BusinessException {
        return super.aqPage(pageInfoInput);
    }
}