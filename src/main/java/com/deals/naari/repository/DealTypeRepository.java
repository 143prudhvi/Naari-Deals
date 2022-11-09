package com.deals.naari.repository;

import com.deals.naari.domain.DealType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DealType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealTypeRepository extends JpaRepository<DealType, Long>, JpaSpecificationExecutor<DealType> {}
