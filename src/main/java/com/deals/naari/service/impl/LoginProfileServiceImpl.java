package com.deals.naari.service.impl;

import com.deals.naari.domain.LoginProfile;
import com.deals.naari.repository.LoginProfileRepository;
import com.deals.naari.service.LoginProfileService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoginProfile}.
 */
@Service
@Transactional
public class LoginProfileServiceImpl implements LoginProfileService {

    private final Logger log = LoggerFactory.getLogger(LoginProfileServiceImpl.class);

    private final LoginProfileRepository loginProfileRepository;

    public LoginProfileServiceImpl(LoginProfileRepository loginProfileRepository) {
        this.loginProfileRepository = loginProfileRepository;
    }

    @Override
    public LoginProfile save(LoginProfile loginProfile) {
        log.debug("Request to save LoginProfile : {}", loginProfile);
        return loginProfileRepository.save(loginProfile);
    }

    @Override
    public LoginProfile update(LoginProfile loginProfile) {
        log.debug("Request to update LoginProfile : {}", loginProfile);
        return loginProfileRepository.save(loginProfile);
    }

    @Override
    public Optional<LoginProfile> partialUpdate(LoginProfile loginProfile) {
        log.debug("Request to partially update LoginProfile : {}", loginProfile);

        return loginProfileRepository
            .findById(loginProfile.getId())
            .map(existingLoginProfile -> {
                if (loginProfile.getUserName() != null) {
                    existingLoginProfile.setUserName(loginProfile.getUserName());
                }
                if (loginProfile.getUserId() != null) {
                    existingLoginProfile.setUserId(loginProfile.getUserId());
                }
                if (loginProfile.getMemberType() != null) {
                    existingLoginProfile.setMemberType(loginProfile.getMemberType());
                }
                if (loginProfile.getMemberId() != null) {
                    existingLoginProfile.setMemberId(loginProfile.getMemberId());
                }
                if (loginProfile.getPhoneNumber() != null) {
                    existingLoginProfile.setPhoneNumber(loginProfile.getPhoneNumber());
                }
                if (loginProfile.getEmailId() != null) {
                    existingLoginProfile.setEmailId(loginProfile.getEmailId());
                }
                if (loginProfile.getPassword() != null) {
                    existingLoginProfile.setPassword(loginProfile.getPassword());
                }
                if (loginProfile.getActivationStatus() != null) {
                    existingLoginProfile.setActivationStatus(loginProfile.getActivationStatus());
                }
                if (loginProfile.getActivationCode() != null) {
                    existingLoginProfile.setActivationCode(loginProfile.getActivationCode());
                }

                return existingLoginProfile;
            })
            .map(loginProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoginProfile> findAll() {
        log.debug("Request to get all LoginProfiles");
        return loginProfileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LoginProfile> findOne(Long id) {
        log.debug("Request to get LoginProfile : {}", id);
        return loginProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginProfile : {}", id);
        loginProfileRepository.deleteById(id);
    }
}
