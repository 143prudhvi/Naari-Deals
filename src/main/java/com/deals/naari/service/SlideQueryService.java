package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.Slide;
import com.deals.naari.repository.SlideRepository;
import com.deals.naari.service.criteria.SlideCriteria;
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
 * Service for executing complex queries for {@link Slide} entities in the database.
 * The main input is a {@link SlideCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Slide} or a {@link Page} of {@link Slide} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SlideQueryService extends QueryService<Slide> {

    private final Logger log = LoggerFactory.getLogger(SlideQueryService.class);

    private final SlideRepository slideRepository;

    public SlideQueryService(SlideRepository slideRepository) {
        this.slideRepository = slideRepository;
    }

    /**
     * Return a {@link List} of {@link Slide} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Slide> findByCriteria(SlideCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Slide> specification = createSpecification(criteria);
        return slideRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Slide} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Slide> findByCriteria(SlideCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Slide> specification = createSpecification(criteria);
        return slideRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SlideCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Slide> specification = createSpecification(criteria);
        return slideRepository.count(specification);
    }

    /**
     * Function to convert {@link SlideCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Slide> createSpecification(SlideCriteria criteria) {
        Specification<Slide> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Slide_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Slide_.title));
            }
            if (criteria.getSubTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubTitle(), Slide_.subTitle));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Slide_.status));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Slide_.country));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartDate(), Slide_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndDate(), Slide_.endDate));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Slide_.imageUrl));
            }
            if (criteria.getDealUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealUrl(), Slide_.dealUrl));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTags(), Slide_.tags));
            }
        }
        return specification;
    }
}
