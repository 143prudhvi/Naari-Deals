package com.deals.naari.service.impl;

import com.deals.naari.domain.Notification;
import com.deals.naari.repository.NotificationRepository;
import com.deals.naari.service.NotificationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Notification notification) {
        log.debug("Request to update Notification : {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> partialUpdate(Notification notification) {
        log.debug("Request to partially update Notification : {}", notification);

        return notificationRepository
            .findById(notification.getId())
            .map(existingNotification -> {
                if (notification.getUserId() != null) {
                    existingNotification.setUserId(notification.getUserId());
                }
                if (notification.getTitle() != null) {
                    existingNotification.setTitle(notification.getTitle());
                }
                if (notification.getMessage() != null) {
                    existingNotification.setMessage(notification.getMessage());
                }
                if (notification.getStatus() != null) {
                    existingNotification.setStatus(notification.getStatus());
                }
                if (notification.getType() != null) {
                    existingNotification.setType(notification.getType());
                }
                if (notification.getDateOfRead() != null) {
                    existingNotification.setDateOfRead(notification.getDateOfRead());
                }
                if (notification.getImageUrl() != null) {
                    existingNotification.setImageUrl(notification.getImageUrl());
                }
                if (notification.getOriginalPrice() != null) {
                    existingNotification.setOriginalPrice(notification.getOriginalPrice());
                }
                if (notification.getCurrentPrice() != null) {
                    existingNotification.setCurrentPrice(notification.getCurrentPrice());
                }
                if (notification.getDiscount() != null) {
                    existingNotification.setDiscount(notification.getDiscount());
                }
                if (notification.getDiscountType() != null) {
                    existingNotification.setDiscountType(notification.getDiscountType());
                }

                return existingNotification;
            })
            .map(notificationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        log.debug("Request to get all Notifications");
        return notificationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notification> findOne(Long id) {
        log.debug("Request to get Notification : {}", id);
        return notificationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.deleteById(id);
    }
}
