package org.hkmadao.tcdt.modules.sysperm.useragg.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseAggController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.hkmadao.tcdt.modules.sysperm.useragg.dto.agg.vo.UserVO;
import org.hkmadao.tcdt.modules.sysperm.useragg.dto.agg.po.UserPO;
import org.hkmadao.tcdt.modules.sysperm.useragg.service.IUserAggService;

@RestController
@RequestMapping("userAgg")
public class UserAggController extends BaseAggController<User, String, UserPO, UserVO, IUserAggService> {

    @Override
    protected UserVO afterFindProcess(User entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("save")
    public UserVO save(@RequestBody UserPO entityPO) throws BusinessException {
        return super.save(entityPO);
    }

    @Override
    @PostMapping("update")
    public UserVO update(@RequestBody UserPO entityPO) throws BusinessException {
        return super.update(entityPO);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody UserPO entityPO) throws BusinessException {
        super.remove(entityPO);
    }

    @Override
    @PostMapping("batchRemove")
    public void batchRemove(@RequestBody List<User> entities) throws BusinessException {
        super.batchRemove(entities);
    }

    @Override
    @GetMapping("getById/{id}")
    public UserVO getById(@PathVariable(value = "id") String s) throws BusinessException {
        return super.getById(s);
    }

    @Override
    @PostMapping("findAll")
    public List<UserVO> findAll(@RequestBody UserPO entityPO) throws BusinessException {
        return super.findAll(entityPO);
    }

    @Override
    @PostMapping("aq")
    public List<UserVO> aq(@RequestBody(required = false) AqCondition condition) throws BusinessException {
        return super.aq(condition);
    }

    @Override
    @PostMapping("aqPage")
    public AqPageInfo<UserVO> aqPage(@RequestBody(required = false) AqPageInfoInput pageInfoInput) throws BusinessException {
        return super.aqPage(pageInfoInput);
    }
}
