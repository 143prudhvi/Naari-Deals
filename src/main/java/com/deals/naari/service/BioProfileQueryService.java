package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.BioProfile;
import com.deals.naari.repository.BioProfileRepository;
import com.deals.naari.service.criteria.BioProfileCriteria;
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
 * Service for executing complex queries for {@link BioProfile} entities in the database.
 * The main input is a {@link BioProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BioProfile} or a {@link Page} of {@link BioProfile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BioProfileQueryService extends QueryService<BioProfile> {

    private final Logger log = LoggerFactory.getLogger(BioProfileQueryService.class);

    private final BioProfileRepository bioProfileRepository;

    public BioProfileQueryService(BioProfileRepository bioProfileRepository) {
        this.bioProfileRepository = bioProfileRepository;
    }

    /**
     * Return a {@link List} of {@link BioProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BioProfile> findByCriteria(BioProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BioProfile> specification = createSpecification(criteria);
        return bioProfileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BioProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BioProfile> findByCriteria(BioProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BioProfile> specification = createSpecification(criteria);
        return bioProfileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BioProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BioProfile> specification = createSpecification(criteria);
        return bioProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link BioProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BioProfile> createSpecification(BioProfileCriteria criteria) {
        Specification<BioProfile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BioProfile_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), BioProfile_.userId));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), BioProfile_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), BioProfile_.lastName));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDob(), BioProfile_.dob));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), BioProfile_.gender));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), BioProfile_.imageUrl));
            }
        }
        return specification;
    }
}
