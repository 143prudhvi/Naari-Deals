package com.deals.naari.service.impl;

import com.deals.naari.domain.Brand;
import com.deals.naari.repository.BrandRepository;
import com.deals.naari.service.BrandService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand save(Brand brand) {
        log.debug("Request to save Brand : {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        log.debug("Request to update Brand : {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> partialUpdate(Brand brand) {
        log.debug("Request to partially update Brand : {}", brand);

        return brandRepository
            .findById(brand.getId())
            .map(existingBrand -> {
                if (brand.getTitle() != null) {
                    existingBrand.setTitle(brand.getTitle());
                }
                if (brand.getSubTitle() != null) {
                    existingBrand.setSubTitle(brand.getSubTitle());
                }
                if (brand.getCode() != null) {
                    existingBrand.setCode(brand.getCode());
                }
                if (brand.getStatus() != null) {
                    existingBrand.setStatus(brand.getStatus());
                }
                if (brand.getCountry() != null) {
                    existingBrand.setCountry(brand.getCountry());
                }
                if (brand.getImageUrl() != null) {
                    existingBrand.setImageUrl(brand.getImageUrl());
                }

                return existingBrand;
            })
            .map(brandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> findAll() {
        log.debug("Request to get all Brands");
        return brandRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        return brandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
    }
}
