package com.deals.naari.service.impl;

import com.deals.naari.domain.Merchant;
import com.deals.naari.repository.MerchantRepository;
import com.deals.naari.service.MerchantService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Merchant}.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private final Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);

    private final MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public Merchant save(Merchant merchant) {
        log.debug("Request to save Merchant : {}", merchant);
        return merchantRepository.save(merchant);
    }

    @Override
    public Merchant update(Merchant merchant) {
        log.debug("Request to update Merchant : {}", merchant);
        return merchantRepository.save(merchant);
    }

    @Override
    public Optional<Merchant> partialUpdate(Merchant merchant) {
        log.debug("Request to partially update Merchant : {}", merchant);

        return merchantRepository
            .findById(merchant.getId())
            .map(existingMerchant -> {
                if (merchant.getCode() != null) {
                    existingMerchant.setCode(merchant.getCode());
                }
                if (merchant.getTitle() != null) {
                    existingMerchant.setTitle(merchant.getTitle());
                }
                if (merchant.getSubTitle() != null) {
                    existingMerchant.setSubTitle(merchant.getSubTitle());
                }
                if (merchant.getAddress() != null) {
                    existingMerchant.setAddress(merchant.getAddress());
                }
                if (merchant.getPhone() != null) {
                    existingMerchant.setPhone(merchant.getPhone());
                }
                if (merchant.getCountry() != null) {
                    existingMerchant.setCountry(merchant.getCountry());
                }
                if (merchant.getCity() != null) {
                    existingMerchant.setCity(merchant.getCity());
                }
                if (merchant.getImageUrl() != null) {
                    existingMerchant.setImageUrl(merchant.getImageUrl());
                }
                if (merchant.getType() != null) {
                    existingMerchant.setType(merchant.getType());
                }
                if (merchant.getLocation() != null) {
                    existingMerchant.setLocation(merchant.getLocation());
                }
                if (merchant.getSiteUrl() != null) {
                    existingMerchant.setSiteUrl(merchant.getSiteUrl());
                }
                if (merchant.getStatus() != null) {
                    existingMerchant.setStatus(merchant.getStatus());
                }

                return existingMerchant;
            })
            .map(merchantRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Merchant> findAll() {
        log.debug("Request to get all Merchants");
        return merchantRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Merchant> findOne(Long id) {
        log.debug("Request to get Merchant : {}", id);
        return merchantRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Merchant : {}", id);
        merchantRepository.deleteById(id);
    }
}
