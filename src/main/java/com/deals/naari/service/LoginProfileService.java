package com.deals.naari.service;

import com.deals.naari.domain.LoginProfile;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LoginProfile}.
 */
public interface LoginProfileService {
    /**
     * Save a loginProfile.
     *
     * @param loginProfile the entity to save.
     * @return the persisted entity.
     */
    LoginProfile save(LoginProfile loginProfile);

    /**
     * Updates a loginProfile.
     *
     * @param loginProfile the entity to update.
     * @return the persisted entity.
     */
    LoginProfile update(LoginProfile loginProfile);

    /**
     * Partially updates a loginProfile.
     *
     * @param loginProfile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LoginProfile> partialUpdate(LoginProfile loginProfile);

    /**
     * Get all the loginProfiles.
     *
     * @return the list of entities.
     */
    List<LoginProfile> findAll();

    /**
     * Get the "id" loginProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoginProfile> findOne(Long id);

    /**
     * Delete the "id" loginProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
