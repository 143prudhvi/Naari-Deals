package com.deals.naari.repository;

import com.deals.naari.domain.BioProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BioProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BioProfileRepository extends JpaRepository<BioProfile, Long> {}
