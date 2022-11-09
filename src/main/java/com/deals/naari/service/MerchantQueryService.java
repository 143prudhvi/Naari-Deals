package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.Merchant;
import com.deals.naari.repository.MerchantRepository;
import com.deals.naari.service.criteria.MerchantCriteria;
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
 * Service for executing complex queries for {@link Merchant} entities in the database.
 * The main input is a {@link MerchantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Merchant} or a {@link Page} of {@link Merchant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MerchantQueryService extends QueryService<Merchant> {

    private final Logger log = LoggerFactory.getLogger(MerchantQueryService.class);

    private final MerchantRepository merchantRepository;

    public MerchantQueryService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    /**
     * Return a {@link List} of {@link Merchant} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Merchant> findByCriteria(MerchantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Merchant> specification = createSpecification(criteria);
        return merchantRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Merchant} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Merchant> findByCriteria(MerchantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Merchant> specification = createSpecification(criteria);
        return merchantRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MerchantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Merchant> specification = createSpecification(criteria);
        return merchantRepository.count(specification);
    }

    /**
     * Function to convert {@link MerchantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Merchant> createSpecification(MerchantCriteria criteria) {
        Specification<Merchant> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Merchant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Merchant_.name));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Merchant_.country));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Merchant_.city));
            }
            if (criteria.getStoreIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStoreIcon(), Merchant_.storeIcon));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Merchant_.type));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Merchant_.location));
            }
        }
        return specification;
    }
}
