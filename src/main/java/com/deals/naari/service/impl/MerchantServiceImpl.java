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
                if (merchant.getName() != null) {
                    existingMerchant.setName(merchant.getName());
                }
                if (merchant.getCountry() != null) {
                    existingMerchant.setCountry(merchant.getCountry());
                }
                if (merchant.getCity() != null) {
                    existingMerchant.setCity(merchant.getCity());
                }
                if (merchant.getStoreIcon() != null) {
                    existingMerchant.setStoreIcon(merchant.getStoreIcon());
                }
                if (merchant.getType() != null) {
                    existingMerchant.setType(merchant.getType());
                }
                if (merchant.getLocation() != null) {
                    existingMerchant.setLocation(merchant.getLocation());
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
