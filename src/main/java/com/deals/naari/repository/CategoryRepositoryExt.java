package com.deals.naari.repository;

import com.deals.naari.domain.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepositoryExt extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
}
