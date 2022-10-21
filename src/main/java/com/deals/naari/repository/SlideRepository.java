package com.deals.naari.repository;

import com.deals.naari.domain.Slide;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Slide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {}
