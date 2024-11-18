package org.hkmadao.tcdt.modules.sysperm.user.controller;

import org.hkmadao.core.advanquery.AqCondition;
import org.hkmadao.core.advanquery.AqPageInfo;
import org.hkmadao.core.advanquery.AqPageInfoInput;

import java.util.List;

import org.hkmadao.tcdt.conf.security.TcdtPasswordEncoder;
import org.hkmadao.tcdt.conf.security.info.TcdtUserInfo;
import org.hkmadao.tcdt.conf.security.tokenmgr.ITokenManager;
import org.hkmadao.tcdt.modules.sysperm.user.dto.chpw.UserPwPO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.hkmadao.core.base.controller.BaseController;
import org.hkmadao.core.base.BusinessException;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.hkmadao.tcdt.modules.sysperm.user.dto.vo.UserVO;
import org.hkmadao.tcdt.modules.sysperm.user.dto.po.UserPO;
import org.hkmadao.tcdt.modules.sysperm.user.service.IUserService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<User, String, UserPO, UserVO, IUserService> {

    @Value("${tcdt.sys.authorized}")
    private boolean fgAuthorized;

    @Autowired
    private TcdtPasswordEncoder tcdtPasswordEncoder;

    @Resource(name = "dbTokenManager")
    private ITokenManager tokenManager;

    @Override
    protected UserVO afterFindProcess(User entity) throws BusinessException {
        return super.afterFindProcess(entity);
    }

    @Override
    @PostMapping("add")
    public UserVO add(@RequestBody UserPO entityPO) throws BusinessException {
        entityPO.setUserPwd(tcdtPasswordEncoder.encode(entityPO.getUserPwd()));
        return super.add(entityPO);
    }

    @Override
    @PostMapping("update")
    public UserVO update(@RequestBody UserPO entityPO) throws BusinessException {
        User user = this.service.getById(entityPO.getIdUser());
        entityPO.setUserPwd(user.getUserPwd());
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
    @PostMapping("getAll")
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

    @PostMapping("updatePw")
    public void updatePw(@RequestBody UserPwPO entityPO) throws BusinessException {
        User userParam = new User();
        userParam.setAccount(entityPO.getUsername());
        User user = this.service.findOne(userParam);
        //验证旧密码
        if (fgAuthorized) {
            boolean matches = tcdtPasswordEncoder.matches(entityPO.getOldPassword(), user.getUserPwd());
            if (!matches) {
                throw new BusinessException("旧密码有误！");
            }
        }
        user.setUserPwd(tcdtPasswordEncoder.encode(entityPO.getPassword()));
        this.service.update(user);
        //作废用户token
        tokenManager.removeTokenByUsername(user.getAccount());
    }

    @GetMapping("getProfile")
    public UserVO getProfile(HttpServletRequest request) throws BusinessException {
        String token = request.getHeader(ITokenManager.TCDT_TOKEN);
        if (!StringUtils.hasLength(token)) {
            throw new BusinessException("获取token失败！");
        }
        try {
            TcdtUserInfo tcdtUserInfoByToken = tokenManager.getTcdtUserInfoByToken(token);
            User userParam = new User();
            userParam.setAccount(tcdtUserInfoByToken.getUsername());
            userParam.setFgActive(true);
            User user = this.service.findOne(userParam);
            return this.afterFindProcess(user);
        } catch (BusinessException e) {
            e.printStackTrace();
            throw new BusinessException("token信息有误！");
        }
    }
}