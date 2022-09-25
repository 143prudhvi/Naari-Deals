package com.deals.naari.repository;

import com.deals.naari.domain.EmailSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmailSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Long> {}
