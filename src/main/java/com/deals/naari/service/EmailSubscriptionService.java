package com.deals.naari.service;

import com.deals.naari.domain.EmailSubscription;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EmailSubscription}.
 */
public interface EmailSubscriptionService {
    /**
     * Save a emailSubscription.
     *
     * @param emailSubscription the entity to save.
     * @return the persisted entity.
     */
    EmailSubscription save(EmailSubscription emailSubscription);

    /**
     * Updates a emailSubscription.
     *
     * @param emailSubscription the entity to update.
     * @return the persisted entity.
     */
    EmailSubscription update(EmailSubscription emailSubscription);

    /**
     * Partially updates a emailSubscription.
     *
     * @param emailSubscription the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmailSubscription> partialUpdate(EmailSubscription emailSubscription);

    /**
     * Get all the emailSubscriptions.
     *
     * @return the list of entities.
     */
    List<EmailSubscription> findAll();

    /**
     * Get the "id" emailSubscription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailSubscription> findOne(Long id);

    /**
     * Delete the "id" emailSubscription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
