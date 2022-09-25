package com.deals.naari.service;

import com.deals.naari.domain.Merchant;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Merchant}.
 */
public interface MerchantService {
    /**
     * Save a merchant.
     *
     * @param merchant the entity to save.
     * @return the persisted entity.
     */
    Merchant save(Merchant merchant);

    /**
     * Updates a merchant.
     *
     * @param merchant the entity to update.
     * @return the persisted entity.
     */
    Merchant update(Merchant merchant);

    /**
     * Partially updates a merchant.
     *
     * @param merchant the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Merchant> partialUpdate(Merchant merchant);

    /**
     * Get all the merchants.
     *
     * @return the list of entities.
     */
    List<Merchant> findAll();

    /**
     * Get the "id" merchant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Merchant> findOne(Long id);

    /**
     * Delete the "id" merchant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
