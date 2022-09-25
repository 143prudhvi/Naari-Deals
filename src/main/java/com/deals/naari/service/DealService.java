package com.deals.naari.service;

import com.deals.naari.domain.Deal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Deal}.
 */
public interface DealService {
    /**
     * Save a deal.
     *
     * @param deal the entity to save.
     * @return the persisted entity.
     */
    Deal save(Deal deal);

    /**
     * Updates a deal.
     *
     * @param deal the entity to update.
     * @return the persisted entity.
     */
    Deal update(Deal deal);

    /**
     * Partially updates a deal.
     *
     * @param deal the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Deal> partialUpdate(Deal deal);

    /**
     * Get all the deals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Deal> findAll(Pageable pageable);

    /**
     * Get the "id" deal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Deal> findOne(Long id);

    /**
     * Delete the "id" deal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
