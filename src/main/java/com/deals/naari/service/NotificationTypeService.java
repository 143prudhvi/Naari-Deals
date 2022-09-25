package com.deals.naari.service;

import com.deals.naari.domain.NotificationType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NotificationType}.
 */
public interface NotificationTypeService {
    /**
     * Save a notificationType.
     *
     * @param notificationType the entity to save.
     * @return the persisted entity.
     */
    NotificationType save(NotificationType notificationType);

    /**
     * Updates a notificationType.
     *
     * @param notificationType the entity to update.
     * @return the persisted entity.
     */
    NotificationType update(NotificationType notificationType);

    /**
     * Partially updates a notificationType.
     *
     * @param notificationType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotificationType> partialUpdate(NotificationType notificationType);

    /**
     * Get all the notificationTypes.
     *
     * @return the list of entities.
     */
    List<NotificationType> findAll();

    /**
     * Get the "id" notificationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificationType> findOne(Long id);

    /**
     * Delete the "id" notificationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
