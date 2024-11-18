package org.hkmadao.tcdt.modules.sysperm.role.dao.repository;

import org.hkmadao.tcdt.modules.sysperm.role.dao.desc.RoleDesc;
import org.hkmadao.tcdt.modules.sysperm.role.dao.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    @EntityGraph(value = RoleDesc.CLASS_NAME_ENTITY + ".graph")
    Page<Role> findAll(@Nullable Specification<Role> spec, Pageable pageable);

}
