package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.EmailSubscription;
import com.deals.naari.repository.EmailSubscriptionRepository;
import com.deals.naari.service.criteria.EmailSubscriptionCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmailSubscription} entities in the database.
 * The main input is a {@link EmailSubscriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailSubscription} or a {@link Page} of {@link EmailSubscription} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailSubscriptionQueryService extends QueryService<EmailSubscription> {

    private final Logger log = LoggerFactory.getLogger(EmailSubscriptionQueryService.class);

    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public EmailSubscriptionQueryService(EmailSubscriptionRepository emailSubscriptionRepository) {
        this.emailSubscriptionRepository = emailSubscriptionRepository;
    }

    /**
     * Return a {@link List} of {@link EmailSubscription} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailSubscription> findByCriteria(EmailSubscriptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmailSubscription> specification = createSpecification(criteria);
        return emailSubscriptionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmailSubscription} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailSubscription> findByCriteria(EmailSubscriptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmailSubscription> specification = createSpecification(criteria);
        return emailSubscriptionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailSubscriptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmailSubscription> specification = createSpecification(criteria);
        return emailSubscriptionRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailSubscriptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmailSubscription> createSpecification(EmailSubscriptionCriteria criteria) {
        Specification<EmailSubscription> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmailSubscription_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), EmailSubscription_.email));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), EmailSubscription_.country));
            }
        }
        return specification;
    }
}
