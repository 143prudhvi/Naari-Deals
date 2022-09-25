package com.deals.naari.service.impl;

import com.deals.naari.domain.BioProfile;
import com.deals.naari.repository.BioProfileRepository;
import com.deals.naari.service.BioProfileService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BioProfile}.
 */
@Service
@Transactional
public class BioProfileServiceImpl implements BioProfileService {

    private final Logger log = LoggerFactory.getLogger(BioProfileServiceImpl.class);

    private final BioProfileRepository bioProfileRepository;

    public BioProfileServiceImpl(BioProfileRepository bioProfileRepository) {
        this.bioProfileRepository = bioProfileRepository;
    }

    @Override
    public BioProfile save(BioProfile bioProfile) {
        log.debug("Request to save BioProfile : {}", bioProfile);
        return bioProfileRepository.save(bioProfile);
    }

    @Override
    public BioProfile update(BioProfile bioProfile) {
        log.debug("Request to update BioProfile : {}", bioProfile);
        return bioProfileRepository.save(bioProfile);
    }

    @Override
    public Optional<BioProfile> partialUpdate(BioProfile bioProfile) {
        log.debug("Request to partially update BioProfile : {}", bioProfile);

        return bioProfileRepository
            .findById(bioProfile.getId())
            .map(existingBioProfile -> {
                if (bioProfile.getUserId() != null) {
                    existingBioProfile.setUserId(bioProfile.getUserId());
                }
                if (bioProfile.getFirstName() != null) {
                    existingBioProfile.setFirstName(bioProfile.getFirstName());
                }
                if (bioProfile.getLastName() != null) {
                    existingBioProfile.setLastName(bioProfile.getLastName());
                }
                if (bioProfile.getDob() != null) {
                    existingBioProfile.setDob(bioProfile.getDob());
                }
                if (bioProfile.getGender() != null) {
                    existingBioProfile.setGender(bioProfile.getGender());
                }
                if (bioProfile.getImageUrl() != null) {
                    existingBioProfile.setImageUrl(bioProfile.getImageUrl());
                }

                return existingBioProfile;
            })
            .map(bioProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BioProfile> findAll() {
        log.debug("Request to get all BioProfiles");
        return bioProfileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BioProfile> findOne(Long id) {
        log.debug("Request to get BioProfile : {}", id);
        return bioProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BioProfile : {}", id);
        bioProfileRepository.deleteById(id);
    }
}
