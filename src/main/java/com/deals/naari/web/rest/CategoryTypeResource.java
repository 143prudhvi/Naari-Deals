package com.deals.naari.web.rest;

import com.deals.naari.domain.CategoryType;
import com.deals.naari.repository.CategoryTypeRepository;
import com.deals.naari.service.CategoryTypeService;
import com.deals.naari.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.deals.naari.domain.CategoryType}.
 */
@RestController
@RequestMapping("/api")
public class CategoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(CategoryTypeResource.class);

    private static final String ENTITY_NAME = "categoryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryTypeService categoryTypeService;

    private final CategoryTypeRepository categoryTypeRepository;

    public CategoryTypeResource(CategoryTypeService categoryTypeService, CategoryTypeRepository categoryTypeRepository) {
        this.categoryTypeService = categoryTypeService;
        this.categoryTypeRepository = categoryTypeRepository;
    }

    /**
     * {@code POST  /category-types} : Create a new categoryType.
     *
     * @param categoryType the categoryType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryType, or with status {@code 400 (Bad Request)} if the categoryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-types")
    public ResponseEntity<CategoryType> createCategoryType(@RequestBody CategoryType categoryType) throws URISyntaxException {
        log.debug("REST request to save CategoryType : {}", categoryType);
        if (categoryType.getId() != null) {
            throw new BadRequestAlertException("A new categoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryType result = categoryTypeService.save(categoryType);
        return ResponseEntity
            .created(new URI("/api/category-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-types/:id} : Updates an existing categoryType.
     *
     * @param id the id of the categoryType to save.
     * @param categoryType the categoryType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryType,
     * or with status {@code 400 (Bad Request)} if the categoryType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-types/{id}")
    public ResponseEntity<CategoryType> updateCategoryType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryType categoryType
    ) throws URISyntaxException {
        log.debug("REST request to update CategoryType : {}, {}", id, categoryType);
        if (categoryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoryType result = categoryTypeService.update(categoryType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /category-types/:id} : Partial updates given fields of an existing categoryType, field will ignore if it is null
     *
     * @param id the id of the categoryType to save.
     * @param categoryType the categoryType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryType,
     * or with status {@code 400 (Bad Request)} if the categoryType is not valid,
     * or with status {@code 404 (Not Found)} if the categoryType is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/category-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoryType> partialUpdateCategoryType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategoryType categoryType
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoryType partially : {}, {}", id, categoryType);
        if (categoryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryType> result = categoryTypeService.partialUpdate(categoryType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryType.getId().toString())
        );
    }

    /**
     * {@code GET  /category-types} : get all the categoryTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryTypes in body.
     */
    @GetMapping("/category-types")
    public List<CategoryType> getAllCategoryTypes() {
        log.debug("REST request to get all CategoryTypes");
        return categoryTypeService.findAll();
    }

    /**
     * {@code GET  /category-types/:id} : get the "id" categoryType.
     *
     * @param id the id of the categoryType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-types/{id}")
    public ResponseEntity<CategoryType> getCategoryType(@PathVariable Long id) {
        log.debug("REST request to get CategoryType : {}", id);
        Optional<CategoryType> categoryType = categoryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryType);
    }

    /**
     * {@code DELETE  /category-types/:id} : delete the "id" categoryType.
     *
     * @param id the id of the categoryType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-types/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable Long id) {
        log.debug("REST request to delete CategoryType : {}", id);
        categoryTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
