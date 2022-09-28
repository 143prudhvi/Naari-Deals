package com.deals.naari.repository;

import com.deals.naari.domain.LoginProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LoginProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginProfileRepositoryExt extends JpaRepository<LoginProfile, Long>, JpaSpecificationExecutor<LoginProfile> {
    @Query(value = "SELECT * FROM login_profile lp WHERE lp.user_id = :memberId", nativeQuery = true)
    LoginProfile findByMemberId(@Param("memberId") String memberId);

    @Query(value = "SELECT * FROM login_profile lp WHERE lp.user_name = :username", nativeQuery = true)
    LoginProfile findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM login_profile", nativeQuery = true)
    Optional<List<LoginProfile>> getAllUsers();
}
