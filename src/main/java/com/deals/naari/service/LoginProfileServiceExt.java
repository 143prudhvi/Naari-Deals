package com.deals.naari.service;

import com.deals.naari.domain.*; // for static metamodels
import com.deals.naari.repository.AuthorityRepository;
import com.deals.naari.repository.LoginProfileRepositoryExt;
import com.deals.naari.repository.UserRepository;
import com.deals.naari.security.AuthoritiesConstants;
import com.deals.naari.service.dto.UserDTO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link LoginProfile} entities in the database.
 * The main input is a {@link LoginProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoginProfile} or a {@link Page} of {@link LoginProfile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoginProfileServiceExt {

    private final Logger log = LoggerFactory.getLogger(LoginProfileServiceExt.class);

    private final LoginProfileRepositoryExt loginProfileRepositoryExt;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public LoginProfileServiceExt(
        LoginProfileRepositoryExt loginProfileRepositoryExt,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository
    ) {
        this.loginProfileRepositoryExt = loginProfileRepositoryExt;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Return a {@link List} of {@link LoginProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public LoginProfile findByUsername(String username) {
        log.debug("find by username : {}", username);
        return loginProfileRepositoryExt.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public LoginProfile findByMemberId(String memberId) {
        log.debug("find by memberid : {}", memberId);
        return loginProfileRepositoryExt.findByMemberId(memberId);
    }

    public User registerUser(UserDTO userDTO, String password) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }
}
