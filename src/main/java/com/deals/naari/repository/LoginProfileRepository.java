package com.deals.naari.repository;

import com.deals.naari.domain.LoginProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoginProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginProfileRepository extends JpaRepository<LoginProfile, Long>, JpaSpecificationExecutor<LoginProfile> {}
