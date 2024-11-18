package org.hkmadao.tcdt.modules.sysperm.userrole.dao.repository;

import org.hkmadao.tcdt.modules.sysperm.userrole.dao.desc.UserRoleDesc;
import org.hkmadao.tcdt.modules.sysperm.userrole.dao.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String>, JpaSpecificationExecutor<UserRole> {

    @EntityGraph(value = UserRoleDesc.CLASS_NAME_ENTITY + ".graph")
    Page<UserRole> findAll(@Nullable Specification<UserRole> spec, Pageable pageable);

}
