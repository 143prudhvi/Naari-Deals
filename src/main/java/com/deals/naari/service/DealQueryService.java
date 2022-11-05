package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.Deal;
import com.deals.naari.repository.DealRepository;
import com.deals.naari.service.criteria.DealCriteria;
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
 * Service for executing complex queries for {@link Deal} entities in the database.
 * The main input is a {@link DealCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Deal} or a {@link Page} of {@link Deal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealQueryService extends QueryService<Deal> {

    private final Logger log = LoggerFactory.getLogger(DealQueryService.class);

    private final DealRepository dealRepository;

    public DealQueryService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    /**
     * Return a {@link List} of {@link Deal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Deal> findByCriteria(DealCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Deal> specification = createSpecification(criteria);
        return dealRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Deal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Deal> findByCriteria(DealCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Deal> specification = createSpecification(criteria);
        return dealRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Deal> specification = createSpecification(criteria);
        return dealRepository.count(specification);
    }

    /**
     * Function to convert {@link DealCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Deal> createSpecification(DealCriteria criteria) {
        Specification<Deal> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Deal_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Deal_.title));
            }
            if (criteria.getPostedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedBy(), Deal_.postedBy));
            }
            if (criteria.getPostedDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostedDate(), Deal_.postedDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDate(), Deal_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDate(), Deal_.endDate));
            }
            if (criteria.getOriginalPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginalPrice(), Deal_.originalPrice));
            }
            if (criteria.getCurrentPrice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentPrice(), Deal_.currentPrice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscount(), Deal_.discount));
            }
            if (criteria.getDiscountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountType(), Deal_.discountType));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActive(), Deal_.active));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), Deal_.approved));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Deal_.country));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Deal_.city));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), Deal_.pinCode));
            }
            if (criteria.getMerchant() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMerchant(), Deal_.merchant));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTags(), Deal_.tags));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), Deal_.brand));
            }
            if (criteria.getExpired() != null) {
                specification = specification.and(buildSpecification(criteria.getExpired(), Deal_.expired));
            }
        }
        return specification;
    }
}
