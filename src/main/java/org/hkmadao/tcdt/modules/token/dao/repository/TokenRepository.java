package org.hkmadao.tcdt.modules.token.dao.repository;

import org.hkmadao.tcdt.modules.token.dao.desc.TokenDesc;
import org.hkmadao.tcdt.modules.token.dao.entity.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String>, JpaSpecificationExecutor<Token> {

    @EntityGraph(value = TokenDesc.CLASS_NAME_ENTITY + ".graph")
    Page<Token> findAll(@Nullable Specification<Token> spec, Pageable pageable);

}
