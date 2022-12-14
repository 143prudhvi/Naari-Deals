package com.deals.naari.service;

import com.deals.naari.domain.Feedback;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Feedback}.
 */
public interface FeedbackService {
    /**
     * Save a feedback.
     *
     * @param feedback the entity to save.
     * @return the persisted entity.
     */
    Feedback save(Feedback feedback);

    /**
     * Updates a feedback.
     *
     * @param feedback the entity to update.
     * @return the persisted entity.
     */
    Feedback update(Feedback feedback);

    /**
     * Partially updates a feedback.
     *
     * @param feedback the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Feedback> partialUpdate(Feedback feedback);

    /**
     * Get all the feedbacks.
     *
     * @return the list of entities.
     */
    List<Feedback> findAll();

    /**
     * Get the "id" feedback.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Feedback> findOne(Long id);

    /**
     * Delete the "id" feedback.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
