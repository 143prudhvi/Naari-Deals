package com.deals.naari.service;

import com.deals.naari.domain.DealType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DealType}.
 */
public interface DealTypeService {
    /**
     * Save a dealType.
     *
     * @param dealType the entity to save.
     * @return the persisted entity.
     */
    DealType save(DealType dealType);

    /**
     * Updates a dealType.
     *
     * @param dealType the entity to update.
     * @return the persisted entity.
     */
    DealType update(DealType dealType);

    /**
     * Partially updates a dealType.
     *
     * @param dealType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DealType> partialUpdate(DealType dealType);

    /**
     * Get all the dealTypes.
     *
     * @return the list of entities.
     */
    List<DealType> findAll();

    /**
     * Get the "id" dealType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DealType> findOne(Long id);

    /**
     * Delete the "id" dealType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
