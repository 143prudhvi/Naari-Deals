package com.deals.naari.service;

import com.deals.naari.domain.CategoryType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CategoryType}.
 */
public interface CategoryTypeService {
    /**
     * Save a categoryType.
     *
     * @param categoryType the entity to save.
     * @return the persisted entity.
     */
    CategoryType save(CategoryType categoryType);

    /**
     * Updates a categoryType.
     *
     * @param categoryType the entity to update.
     * @return the persisted entity.
     */
    CategoryType update(CategoryType categoryType);

    /**
     * Partially updates a categoryType.
     *
     * @param categoryType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoryType> partialUpdate(CategoryType categoryType);

    /**
     * Get all the categoryTypes.
     *
     * @return the list of entities.
     */
    List<CategoryType> findAll();

    /**
     * Get the "id" categoryType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryType> findOne(Long id);

    /**
     * Delete the "id" categoryType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
