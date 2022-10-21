package com.deals.naari.service;

import com.deals.naari.domain.Slide;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Slide}.
 */
public interface SlideService {
    /**
     * Save a slide.
     *
     * @param slide the entity to save.
     * @return the persisted entity.
     */
    Slide save(Slide slide);

    /**
     * Updates a slide.
     *
     * @param slide the entity to update.
     * @return the persisted entity.
     */
    Slide update(Slide slide);

    /**
     * Partially updates a slide.
     *
     * @param slide the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Slide> partialUpdate(Slide slide);

    /**
     * Get all the slides.
     *
     * @return the list of entities.
     */
    List<Slide> findAll();

    /**
     * Get the "id" slide.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Slide> findOne(Long id);

    /**
     * Delete the "id" slide.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
