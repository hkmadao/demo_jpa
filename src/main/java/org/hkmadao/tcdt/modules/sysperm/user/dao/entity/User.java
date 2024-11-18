package org.hkmadao.tcdt.modules.sysperm.user.dao.entity;

import jakarta.persistence.*;

import java.util.Set;
import org.hibernate.annotations.GenericGenerator;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.tcdt.modules.sysperm.user.dao.desc.UserDesc;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.desc.UserRoleDesc;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.entity.UserRole;
@NamedEntityGraph(name = UserDesc.CLASS_NAME_ENTITY + ".graph"
        ,
        attributeNodes = {
        }
        )
@Entity(name = "sys_user")
public class User extends BaseEntity {

    /**
     * 系统用户id
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id_user")
    private String idUser;
    /**
     * 登录账号 
     */
    @Column(name = "account" , length = 255 )
    private String account;
    /**
     * 用户密码 
     */
    @Column(name = "user_pwd" , length = 255 )
    private String userPwd;
    /**
     * 手机号码
     */
    @Column(name = "phone" , length = 255 )
    private String phone;
    /**
     * 邮箱
     */
    @Column(name = "email" , length = 255 )
    private String email;
    /**
     * 姓名 
     */
    @Column(name = "name" , length = 255 )
    private String name;
    /**
     * 昵称
     */
    @Column(name = "nick_name" , length = 255 )
    private String nickName;
    /**
     * 性别
     */
    @Column(name = "gender" , length = 255 )
    private String gender;
    /**
     * 启用标志
     */
    @Column(name = "fg_active" , length = 1 )
    private Boolean fgActive;
    /**
     * 用户角色关系:用户角色关系
     */
    @OneToMany(mappedBy = UserRoleDesc.USER_ATTR, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles;
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
