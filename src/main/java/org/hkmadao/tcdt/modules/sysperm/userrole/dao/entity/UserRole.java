package org.hkmadao.tcdt.modules.sysperm.userrole.dao.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hkmadao.core.base.BaseEntity;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.desc.UserRoleDesc;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
@NamedEntityGraph(name = UserRoleDesc.CLASS_NAME_ENTITY + ".graph"
        )
@Entity(name = "sys_user_role")
public class UserRole extends BaseEntity {

    /**
     * 用户角色关系主属性
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id_sys_user_role")
    private String idUserRole;
    /**
     * 系统用户id:系统用户id
     */
    @Column(name = "id_user" , length = 255 )
    private String idUser;
    /**
     * 角色id:角色id
     */
    @Column(name = "id_role" , length = 255 )
    private String idRole;
    /**
     * 系统用户:系统用户
     */
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;
    /**
     * 角色:角色
     */
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private Role role;
    public String getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(String idUserRole) {
        this.idUserRole = idUserRole;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
