package org.hkmadao.tcdt.modules.sysperm.user.dao.repository;

import org.hkmadao.tcdt.modules.sysperm.user.dao.desc.UserDesc;
import org.hkmadao.tcdt.modules.sysperm.user.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @EntityGraph(value = UserDesc.CLASS_NAME_ENTITY + ".graph")
    Page<User> findAll(@Nullable Specification<User> spec, Pageable pageable);

}
