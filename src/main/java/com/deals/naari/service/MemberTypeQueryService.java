package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.MemberType;
import com.deals.naari.repository.MemberTypeRepository;
import com.deals.naari.service.criteria.MemberTypeCriteria;
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
 * Service for executing complex queries for {@link MemberType} entities in the database.
 * The main input is a {@link MemberTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberType} or a {@link Page} of {@link MemberType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberTypeQueryService extends QueryService<MemberType> {

    private final Logger log = LoggerFactory.getLogger(MemberTypeQueryService.class);

    private final MemberTypeRepository memberTypeRepository;

    public MemberTypeQueryService(MemberTypeRepository memberTypeRepository) {
        this.memberTypeRepository = memberTypeRepository;
    }

    /**
     * Return a {@link List} of {@link MemberType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberType> findByCriteria(MemberTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MemberType> specification = createSpecification(criteria);
        return memberTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MemberType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberType> findByCriteria(MemberTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MemberType> specification = createSpecification(criteria);
        return memberTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MemberType> specification = createSpecification(criteria);
        return memberTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberType> createSpecification(MemberTypeCriteria criteria) {
        Specification<MemberType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberType_.id));
            }
            if (criteria.getMemberType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberType(), MemberType_.memberType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MemberType_.description));
            }
        }
        return specification;
    }
}
