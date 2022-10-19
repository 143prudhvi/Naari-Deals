package com.deals.naari.service.impl;

import com.deals.naari.domain.CategoryType;
import com.deals.naari.repository.CategoryTypeRepository;
import com.deals.naari.service.CategoryTypeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoryType}.
 */
@Service
@Transactional
public class CategoryTypeServiceImpl implements CategoryTypeService {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeServiceImpl.class);

    private final CategoryTypeRepository categoryTypeRepository;

    public CategoryTypeServiceImpl(CategoryTypeRepository categoryTypeRepository) {
        this.categoryTypeRepository = categoryTypeRepository;
    }

    @Override
    public CategoryType save(CategoryType categoryType) {
        log.debug("Request to save CategoryType : {}", categoryType);
        return categoryTypeRepository.save(categoryType);
    }

    @Override
    public CategoryType update(CategoryType categoryType) {
        log.debug("Request to update CategoryType : {}", categoryType);
        return categoryTypeRepository.save(categoryType);
    }

    @Override
    public Optional<CategoryType> partialUpdate(CategoryType categoryType) {
        log.debug("Request to partially update CategoryType : {}", categoryType);

        return categoryTypeRepository
            .findById(categoryType.getId())
            .map(existingCategoryType -> {
                if (categoryType.getTitle() != null) {
                    existingCategoryType.setTitle(categoryType.getTitle());
                }
                if (categoryType.getSubTitle() != null) {
                    existingCategoryType.setSubTitle(categoryType.getSubTitle());
                }
                if (categoryType.getIcon() != null) {
                    existingCategoryType.setIcon(categoryType.getIcon());
                }
                if (categoryType.getBgColor() != null) {
                    existingCategoryType.setBgColor(categoryType.getBgColor());
                }
                if (categoryType.getCountry() != null) {
                    existingCategoryType.setCountry(categoryType.getCountry());
                }
                if (categoryType.getCode() != null) {
                    existingCategoryType.setCode(categoryType.getCode());
                }
                if (categoryType.getStatus() != null) {
                    existingCategoryType.setStatus(categoryType.getStatus());
                }

                return existingCategoryType;
            })
            .map(categoryTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryType> findAll() {
        log.debug("Request to get all CategoryTypes");
        return categoryTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryType> findOne(Long id) {
        log.debug("Request to get CategoryType : {}", id);
        return categoryTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryType : {}", id);
        categoryTypeRepository.deleteById(id);
    }
}
