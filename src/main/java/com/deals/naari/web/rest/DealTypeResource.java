package com.deals.naari.web.rest;

import com.deals.naari.domain.DealType;
import com.deals.naari.repository.DealTypeRepository;
import com.deals.naari.service.DealTypeQueryService;
import com.deals.naari.service.DealTypeService;
import com.deals.naari.service.criteria.DealTypeCriteria;
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
 * REST controller for managing {@link com.deals.naari.domain.DealType}.
 */
@RestController
@RequestMapping("/api")
public class DealTypeResource {

    private final Logger log = LoggerFactory.getLogger(DealTypeResource.class);

    private static final String ENTITY_NAME = "dealType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealTypeService dealTypeService;

    private final DealTypeRepository dealTypeRepository;

    private final DealTypeQueryService dealTypeQueryService;

    public DealTypeResource(
        DealTypeService dealTypeService,
        DealTypeRepository dealTypeRepository,
        DealTypeQueryService dealTypeQueryService
    ) {
        this.dealTypeService = dealTypeService;
        this.dealTypeRepository = dealTypeRepository;
        this.dealTypeQueryService = dealTypeQueryService;
    }

    /**
     * {@code POST  /deal-types} : Create a new dealType.
     *
     * @param dealType the dealType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealType, or with status {@code 400 (Bad Request)} if the dealType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deal-types")
    public ResponseEntity<DealType> createDealType(@RequestBody DealType dealType) throws URISyntaxException {
        log.debug("REST request to save DealType : {}", dealType);
        if (dealType.getId() != null) {
            throw new BadRequestAlertException("A new dealType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DealType result = dealTypeService.save(dealType);
        return ResponseEntity
            .created(new URI("/api/deal-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deal-types/:id} : Updates an existing dealType.
     *
     * @param id the id of the dealType to save.
     * @param dealType the dealType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealType,
     * or with status {@code 400 (Bad Request)} if the dealType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deal-types/{id}")
    public ResponseEntity<DealType> updateDealType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealType dealType
    ) throws URISyntaxException {
        log.debug("REST request to update DealType : {}, {}", id, dealType);
        if (dealType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DealType result = dealTypeService.update(dealType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deal-types/:id} : Partial updates given fields of an existing dealType, field will ignore if it is null
     *
     * @param id the id of the dealType to save.
     * @param dealType the dealType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealType,
     * or with status {@code 400 (Bad Request)} if the dealType is not valid,
     * or with status {@code 404 (Not Found)} if the dealType is not found,
     * or with status {@code 500 (Internal Server Error)} if the dealType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deal-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DealType> partialUpdateDealType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DealType dealType
    ) throws URISyntaxException {
        log.debug("REST request to partial update DealType partially : {}, {}", id, dealType);
        if (dealType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dealType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DealType> result = dealTypeService.partialUpdate(dealType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dealType.getId().toString())
        );
    }

    /**
     * {@code GET  /deal-types} : get all the dealTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealTypes in body.
     */
    @GetMapping("/deal-types")
    public ResponseEntity<List<DealType>> getAllDealTypes(DealTypeCriteria criteria) {
        log.debug("REST request to get DealTypes by criteria: {}", criteria);
        List<DealType> entityList = dealTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /deal-types/count} : count all the dealTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/deal-types/count")
    public ResponseEntity<Long> countDealTypes(DealTypeCriteria criteria) {
        log.debug("REST request to count DealTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(dealTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /deal-types/:id} : get the "id" dealType.
     *
     * @param id the id of the dealType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deal-types/{id}")
    public ResponseEntity<DealType> getDealType(@PathVariable Long id) {
        log.debug("REST request to get DealType : {}", id);
        Optional<DealType> dealType = dealTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dealType);
    }

    /**
     * {@code DELETE  /deal-types/:id} : delete the "id" dealType.
     *
     * @param id the id of the dealType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deal-types/{id}")
    public ResponseEntity<Void> deleteDealType(@PathVariable Long id) {
        log.debug("REST request to delete DealType : {}", id);
        dealTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
