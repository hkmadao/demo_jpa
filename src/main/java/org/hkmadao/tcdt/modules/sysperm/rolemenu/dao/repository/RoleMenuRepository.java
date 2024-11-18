package org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.repository;

import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.desc.RoleMenuDesc;
import org.hkmadao.tcdt.modules.sysperm.rolemenu.dao.entity.RoleMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, String>, JpaSpecificationExecutor<RoleMenu> {

    @EntityGraph(value = RoleMenuDesc.CLASS_NAME_ENTITY + ".graph")
    Page<RoleMenu> findAll(@Nullable Specification<RoleMenu> spec, Pageable pageable);

}
