package com.deals.naari.service.impl;

import com.deals.naari.domain.NotificationType;
import com.deals.naari.repository.NotificationTypeRepository;
import com.deals.naari.service.NotificationTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotificationType}.
 */
@Service
@Transactional
public class NotificationTypeServiceImpl implements NotificationTypeService {

    private final Logger log = LoggerFactory.getLogger(NotificationTypeServiceImpl.class);

    private final NotificationTypeRepository notificationTypeRepository;

    public NotificationTypeServiceImpl(NotificationTypeRepository notificationTypeRepository) {
        this.notificationTypeRepository = notificationTypeRepository;
    }

    @Override
    public NotificationType save(NotificationType notificationType) {
        log.debug("Request to save NotificationType : {}", notificationType);
        return notificationTypeRepository.save(notificationType);
    }

    @Override
    public NotificationType update(NotificationType notificationType) {
        log.debug("Request to update NotificationType : {}", notificationType);
        return notificationTypeRepository.save(notificationType);
    }

    @Override
    public Optional<NotificationType> partialUpdate(NotificationType notificationType) {
        log.debug("Request to partially update NotificationType : {}", notificationType);

        return notificationTypeRepository
            .findById(notificationType.getId())
            .map(existingNotificationType -> {
                if (notificationType.getType() != null) {
                    existingNotificationType.setType(notificationType.getType());
                }
                if (notificationType.getDescription() != null) {
                    existingNotificationType.setDescription(notificationType.getDescription());
                }

                return existingNotificationType;
            })
            .map(notificationTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationType> findAll() {
        log.debug("Request to get all NotificationTypes");
        return notificationTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationType> findOne(Long id) {
        log.debug("Request to get NotificationType : {}", id);
        return notificationTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotificationType : {}", id);
        notificationTypeRepository.deleteById(id);
    }
}
