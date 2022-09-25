package com.deals.naari.service.impl;

import com.deals.naari.domain.MemberType;
import com.deals.naari.repository.MemberTypeRepository;
import com.deals.naari.service.MemberTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MemberType}.
 */
@Service
@Transactional
public class MemberTypeServiceImpl implements MemberTypeService {

    private final Logger log = LoggerFactory.getLogger(MemberTypeServiceImpl.class);

    private final MemberTypeRepository memberTypeRepository;

    public MemberTypeServiceImpl(MemberTypeRepository memberTypeRepository) {
        this.memberTypeRepository = memberTypeRepository;
    }

    @Override
    public MemberType save(MemberType memberType) {
        log.debug("Request to save MemberType : {}", memberType);
        return memberTypeRepository.save(memberType);
    }

    @Override
    public MemberType update(MemberType memberType) {
        log.debug("Request to update MemberType : {}", memberType);
        return memberTypeRepository.save(memberType);
    }

    @Override
    public Optional<MemberType> partialUpdate(MemberType memberType) {
        log.debug("Request to partially update MemberType : {}", memberType);

        return memberTypeRepository
            .findById(memberType.getId())
            .map(existingMemberType -> {
                if (memberType.getMemberType() != null) {
                    existingMemberType.setMemberType(memberType.getMemberType());
                }
                if (memberType.getDescription() != null) {
                    existingMemberType.setDescription(memberType.getDescription());
                }
                if (memberType.getImageUrl() != null) {
                    existingMemberType.setImageUrl(memberType.getImageUrl());
                }

                return existingMemberType;
            })
            .map(memberTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberType> findAll() {
        log.debug("Request to get all MemberTypes");
        return memberTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MemberType> findOne(Long id) {
        log.debug("Request to get MemberType : {}", id);
        return memberTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MemberType : {}", id);
        memberTypeRepository.deleteById(id);
    }
}
