package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.domain.LoginProfile;
import com.deals.naari.repository.LoginProfileRepository;
import com.deals.naari.service.criteria.LoginProfileCriteria;
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
 * Service for executing complex queries for {@link LoginProfile} entities in the database.
 * The main input is a {@link LoginProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoginProfile} or a {@link Page} of {@link LoginProfile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoginProfileQueryService extends QueryService<LoginProfile> {

    private final Logger log = LoggerFactory.getLogger(LoginProfileQueryService.class);

    private final LoginProfileRepository loginProfileRepository;

    public LoginProfileQueryService(LoginProfileRepository loginProfileRepository) {
        this.loginProfileRepository = loginProfileRepository;
    }

    /**
     * Return a {@link List} of {@link LoginProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoginProfile> findByCriteria(LoginProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoginProfile> specification = createSpecification(criteria);
        return loginProfileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LoginProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoginProfile> findByCriteria(LoginProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoginProfile> specification = createSpecification(criteria);
        return loginProfileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoginProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoginProfile> specification = createSpecification(criteria);
        return loginProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link LoginProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoginProfile> createSpecification(LoginProfileCriteria criteria) {
        Specification<LoginProfile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoginProfile_.id));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), LoginProfile_.userName));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), LoginProfile_.userId));
            }
            if (criteria.getMemberType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberType(), LoginProfile_.memberType));
            }
            if (criteria.getMemberId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberId(), LoginProfile_.memberId));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), LoginProfile_.phoneNumber));
            }
            if (criteria.getEmailId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailId(), LoginProfile_.emailId));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), LoginProfile_.password));
            }
            if (criteria.getActivationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationStatus(), LoginProfile_.activationStatus));
            }
            if (criteria.getActivationCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationCode(), LoginProfile_.activationCode));
            }
        }
        return specification;
    }
}
