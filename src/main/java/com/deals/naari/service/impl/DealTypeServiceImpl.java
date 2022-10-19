package com.deals.naari.service.impl;

import com.deals.naari.domain.DealType;
import com.deals.naari.repository.DealTypeRepository;
import com.deals.naari.service.DealTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DealType}.
 */
@Service
@Transactional
public class DealTypeServiceImpl implements DealTypeService {

    private final Logger log = LoggerFactory.getLogger(DealTypeServiceImpl.class);

    private final DealTypeRepository dealTypeRepository;

    public DealTypeServiceImpl(DealTypeRepository dealTypeRepository) {
        this.dealTypeRepository = dealTypeRepository;
    }

    @Override
    public DealType save(DealType dealType) {
        log.debug("Request to save DealType : {}", dealType);
        return dealTypeRepository.save(dealType);
    }

    @Override
    public DealType update(DealType dealType) {
        log.debug("Request to update DealType : {}", dealType);
        return dealTypeRepository.save(dealType);
    }

    @Override
    public Optional<DealType> partialUpdate(DealType dealType) {
        log.debug("Request to partially update DealType : {}", dealType);

        return dealTypeRepository
            .findById(dealType.getId())
            .map(existingDealType -> {
                if (dealType.getTitle() != null) {
                    existingDealType.setTitle(dealType.getTitle());
                }
                if (dealType.getSubTitle() != null) {
                    existingDealType.setSubTitle(dealType.getSubTitle());
                }
                if (dealType.getIcon() != null) {
                    existingDealType.setIcon(dealType.getIcon());
                }
                if (dealType.getBgColor() != null) {
                    existingDealType.setBgColor(dealType.getBgColor());
                }
                if (dealType.getCountry() != null) {
                    existingDealType.setCountry(dealType.getCountry());
                }
                if (dealType.getCode() != null) {
                    existingDealType.setCode(dealType.getCode());
                }
                if (dealType.getStatus() != null) {
                    existingDealType.setStatus(dealType.getStatus());
                }

                return existingDealType;
            })
            .map(dealTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DealType> findAll() {
        log.debug("Request to get all DealTypes");
        return dealTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DealType> findOne(Long id) {
        log.debug("Request to get DealType : {}", id);
        return dealTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DealType : {}", id);
        dealTypeRepository.deleteById(id);
    }
}
