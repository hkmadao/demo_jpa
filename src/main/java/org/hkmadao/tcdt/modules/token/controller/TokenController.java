package org.hkmadao.tcdt.modules.token.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.token.dao.entity.Token;
import org.hkmadao.tcdt.modules.token.dto.vo.TokenVO;
import org.hkmadao.tcdt.modules.token.dto.po.TokenPO;
import org.hkmadao.tcdt.modules.token.service.ITokenService;

@RestController
@RequestMapping("token")
public class TokenController extends BaseController<Token, String, TokenPO, TokenVO, ITokenService> {

    @Override
    protected TokenVO afterFindProcess(Token entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("add")
    public TokenVO add(@RequestBody TokenPO entityPO) throws BusinessException {
        return super.add(entityPO);
    }

    @Override
    @PostMapping("update")
    public TokenVO update(@RequestBody TokenPO entityPO) throws BusinessException {
        return super.update(entityPO);
    }

    @Override
    @PostMapping("remove")
    public void remove(@RequestBody TokenPO entityPO) throws BusinessException {
        super.remove(entityPO);
    }

    @Override
    @PostMapping("batchRemove")
    public void batchRemove(@RequestBody List<Token> entities) throws BusinessException {
        super.batchRemove(entities);
    }

    @Override
    @GetMapping("getById/{id}")
    public TokenVO getById(@PathVariable(value = "id") String s) throws BusinessException {
        return super.getById(s);
    }

    @Override
    @PostMapping("getAll")
    public List<TokenVO> findAll(@RequestBody TokenPO entityPO) throws BusinessException {
        return super.findAll(entityPO);
    }

    @Override
    @PostMapping("aq")
    public List<TokenVO> aq(@RequestBody(required = false) AqCondition condition) throws BusinessException {
        return super.aq(condition);
    }

    @Override
    @PostMapping("aqPage")
    public AqPageInfo<TokenVO> aqPage(@RequestBody(required = false) AqPageInfoInput pageInfoInput) throws BusinessException {
        return super.aqPage(pageInfoInput);
    }
}