package org.hkmadao.tcdt.modules.sysperm.rolemenu.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.entity.RoleMenu;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dto.vo.RoleMenuVO;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dto.po.RoleMenuPO;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.service.IRoleMenuService;

@RestController
@RequestMapping("roleMenu")
public class RoleMenuController extends BaseController<RoleMenu, String, RoleMenuPO, RoleMenuVO, IRoleMenuService> {

    @Override
    protected RoleMenuVO afterFindProcess(RoleMenu entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("add")
    public RoleMenuVO add(@RequestBody RoleMenuPO entityPO) throws BusinessException {
        return super.add(entityPO);
    }

    @Override
    @PostMapping("update")
    public RoleMenuVO update(@RequestBody RoleMenuPO entityPO) throws BusinessException {
        return super.update(entityPO);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody RoleMenuPO entityPO) throws BusinessException {
        super.remove(entityPO);
    }

    @Override
    @PostMapping("batchRemove")
    public void batchRemove(@RequestBody List<RoleMenu> entities) throws BusinessException {
        super.batchRemove(entities);
    }

    @Override
    @GetMapping("getById/{id}")
    public RoleMenuVO getById(@PathVariable(value = "id") String s) throws BusinessException {
        return super.getById(s);
    }

    @Override
    @PostMapping("getAll")
    public List<RoleMenuVO> findAll(@RequestBody RoleMenuPO entityPO) throws BusinessException {
        return super.findAll(entityPO);
    }

    @Override
    @PostMapping("aq")
    public List<RoleMenuVO> aq(@RequestBody(required = false) AqCondition condition) throws BusinessException {
        return super.aq(condition);
    }

    @Override
    @PostMapping("aqPage")
    public AqPageInfo<RoleMenuVO> aqPage(@RequestBody(required = false) AqPageInfoInput pageInfoInput) throws BusinessException {
        return super.aqPage(pageInfoInput);
    }
}