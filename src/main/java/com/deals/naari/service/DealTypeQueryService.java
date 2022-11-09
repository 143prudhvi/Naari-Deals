package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.DealType;
import com.deals.naari.repository.DealTypeRepository;
import com.deals.naari.service.criteria.DealTypeCriteria;
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
 * Service for executing complex queries for {@link DealType} entities in the database.
 * The main input is a {@link DealTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealType} or a {@link Page} of {@link DealType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealTypeQueryService extends QueryService<DealType> {

    private final Logger log = LoggerFactory.getLogger(DealTypeQueryService.class);

    private final DealTypeRepository dealTypeRepository;

    public DealTypeQueryService(DealTypeRepository dealTypeRepository) {
        this.dealTypeRepository = dealTypeRepository;
    }

    /**
     * Return a {@link List} of {@link DealType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealType> findByCriteria(DealTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DealType> specification = createSpecification(criteria);
        return dealTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DealType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealType> findByCriteria(DealTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DealType> specification = createSpecification(criteria);
        return dealTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DealType> specification = createSpecification(criteria);
        return dealTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link DealTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DealType> createSpecification(DealTypeCriteria criteria) {
        Specification<DealType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DealType_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), DealType_.title));
            }
            if (criteria.getSubTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubTitle(), DealType_.subTitle));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), DealType_.icon));
            }
            if (criteria.getBgColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBgColor(), DealType_.bgColor));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), DealType_.country));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), DealType_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), DealType_.status));
            }
        }
        return specification;
    }
}
