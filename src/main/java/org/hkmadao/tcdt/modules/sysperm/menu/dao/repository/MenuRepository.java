package org.hkmadao.tcdt.modules.sysperm.menu.dao.repository;

import org.hkmadao.tcdt.modules.sysperm.menu.dao.desc.MenuDesc;
import org.hkmadao.tcdt.modules.sysperm.menu.dao.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {

    @EntityGraph(value = MenuDesc.CLASS_NAME_ENTITY + ".graph")
    Page<Menu> findAll(@Nullable Specification<Menu> spec, Pageable pageable);

}
