package com.deals.naari.repository;

import com.deals.naari.domain.BioProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BioProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BioProfileRepositoryExt extends JpaRepository<BioProfile, String>, JpaSpecificationExecutor<BioProfile> {
    @Query(value = "SELECT * FROM bio_profile c WHERE c.user_id = :username", nativeQuery = true)
    BioProfile findByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM bio_profile c WHERE c.user_id = :userId", nativeQuery = true)
    Optional<BioProfile> findByUserId(@Param("userId") String userId);
}
