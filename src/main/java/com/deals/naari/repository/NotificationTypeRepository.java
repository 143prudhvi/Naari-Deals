package com.deals.naari.repository;

import com.deals.naari.domain.NotificationType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotificationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long>, JpaSpecificationExecutor<NotificationType> {}
