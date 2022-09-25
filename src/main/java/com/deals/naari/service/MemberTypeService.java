package com.deals.naari.service;

import com.deals.naari.domain.MemberType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MemberType}.
 */
public interface MemberTypeService {
    /**
     * Save a memberType.
     *
     * @param memberType the entity to save.
     * @return the persisted entity.
     */
    MemberType save(MemberType memberType);

    /**
     * Updates a memberType.
     *
     * @param memberType the entity to update.
     * @return the persisted entity.
     */
    MemberType update(MemberType memberType);

    /**
     * Partially updates a memberType.
     *
     * @param memberType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MemberType> partialUpdate(MemberType memberType);

    /**
     * Get all the memberTypes.
     *
     * @return the list of entities.
     */
    List<MemberType> findAll();

    /**
     * Get the "id" memberType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MemberType> findOne(Long id);

    /**
     * Delete the "id" memberType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
