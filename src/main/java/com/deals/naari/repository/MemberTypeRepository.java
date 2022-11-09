package com.deals.naari.repository;

import com.deals.naari.domain.MemberType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberTypeRepository extends JpaRepository<MemberType, Long>, JpaSpecificationExecutor<MemberType> {}
