package com.deals.naari.service.impl;

import com.deals.naari.domain.Deal;
import com.deals.naari.repository.DealRepository;
import com.deals.naari.service.DealService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deal}.
 */
@Service
@Transactional
public class DealServiceImpl implements DealService {

    private final Logger log = LoggerFactory.getLogger(DealServiceImpl.class);

    private final DealRepository dealRepository;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public Deal save(Deal deal) {
        log.debug("Request to save Deal : {}", deal);
        return dealRepository.save(deal);
    }

    @Override
    public Deal update(Deal deal) {
        log.debug("Request to update Deal : {}", deal);
        return dealRepository.save(deal);
    }

    @Override
    public Optional<Deal> partialUpdate(Deal deal) {
        log.debug("Request to partially update Deal : {}", deal);

        return dealRepository
            .findById(deal.getId())
            .map(existingDeal -> {
                if (deal.getTitle() != null) {
                    existingDeal.setTitle(deal.getTitle());
                }
                if (deal.getDescription() != null) {
                    existingDeal.setDescription(deal.getDescription());
                }
                if (deal.getImageUrl() != null) {
                    existingDeal.setImageUrl(deal.getImageUrl());
                }
                if (deal.getDealUrl() != null) {
                    existingDeal.setDealUrl(deal.getDealUrl());
                }
                if (deal.getPostedBy() != null) {
                    existingDeal.setPostedBy(deal.getPostedBy());
                }
                if (deal.getPostedDate() != null) {
                    existingDeal.setPostedDate(deal.getPostedDate());
                }
                if (deal.getStartDate() != null) {
                    existingDeal.setStartDate(deal.getStartDate());
                }
                if (deal.getEndDate() != null) {
                    existingDeal.setEndDate(deal.getEndDate());
                }
                if (deal.getOriginalPrice() != null) {
                    existingDeal.setOriginalPrice(deal.getOriginalPrice());
                }
                if (deal.getCurrentPrice() != null) {
                    existingDeal.setCurrentPrice(deal.getCurrentPrice());
                }
                if (deal.getDiscount() != null) {
                    existingDeal.setDiscount(deal.getDiscount());
                }
                if (deal.getDiscountType() != null) {
                    existingDeal.setDiscountType(deal.getDiscountType());
                }
                if (deal.getActive() != null) {
                    existingDeal.setActive(deal.getActive());
                }
                if (deal.getApproved() != null) {
                    existingDeal.setApproved(deal.getApproved());
                }
                if (deal.getCountry() != null) {
                    existingDeal.setCountry(deal.getCountry());
                }
                if (deal.getCity() != null) {
                    existingDeal.setCity(deal.getCity());
                }
                if (deal.getPinCode() != null) {
                    existingDeal.setPinCode(deal.getPinCode());
                }
                if (deal.getMerchant() != null) {
                    existingDeal.setMerchant(deal.getMerchant());
                }
                if (deal.getTags() != null) {
                    existingDeal.setTags(deal.getTags());
                }

                return existingDeal;
            })
            .map(dealRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Deal> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Deal> findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        return dealRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deal : {}", id);
        dealRepository.deleteById(id);
    }
}
