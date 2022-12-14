package com.deals.naari.service.impl;

import com.deals.naari.domain.Category;
import com.deals.naari.repository.CategoryRepository;
import com.deals.naari.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        log.debug("Request to update Category : {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> partialUpdate(Category category) {
        log.debug("Request to partially update Category : {}", category);

        return categoryRepository
            .findById(category.getId())
            .map(existingCategory -> {
                if (category.getParent() != null) {
                    existingCategory.setParent(category.getParent());
                }
                if (category.getTitle() != null) {
                    existingCategory.setTitle(category.getTitle());
                }
                if (category.getSubTitle() != null) {
                    existingCategory.setSubTitle(category.getSubTitle());
                }
                if (category.getImageUrl() != null) {
                    existingCategory.setImageUrl(category.getImageUrl());
                }
                if (category.getDescription() != null) {
                    existingCategory.setDescription(category.getDescription());
                }
                if (category.getCountry() != null) {
                    existingCategory.setCountry(category.getCountry());
                }
                if (category.getCode() != null) {
                    existingCategory.setCode(category.getCode());
                }
                if (category.getStatus() != null) {
                    existingCategory.setStatus(category.getStatus());
                }

                return existingCategory;
            })
            .map(categoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
