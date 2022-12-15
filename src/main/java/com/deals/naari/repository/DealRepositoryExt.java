package com.deals.naari.repository;

import com.deals.naari.domain.Deal;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DealRepositoryExt extends JpaRepository<Deal, Long>, JpaSpecificationExecutor<Deal> {
    @Query(value = "DELETE FROM deal WHERE id IN :ids", nativeQuery = true)
    Void deleteDeals(@Param("ids") String ids);
}
