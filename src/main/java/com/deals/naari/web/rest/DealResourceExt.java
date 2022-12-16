package com.deals.naari.web.rest;

import com.deals.naari.domain.Deal;
import com.deals.naari.repository.DealRepository;
import com.deals.naari.service.DealQueryService;
import com.deals.naari.service.DealServiceExt;
import com.deals.naari.service.criteria.DealCriteria;
import com.deals.naari.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.deals.naari.domain.Deal}.
 */
@RestController
@RequestMapping("/api")
public class DealResourceExt {

    private final Logger log = LoggerFactory.getLogger(DealResourceExt.class);

    private static final String ENTITY_NAME = "deal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealServiceExt dealServiceExt;

    private final DealRepository dealRepository;

    private final DealQueryService dealQueryService;

    public DealResourceExt(DealServiceExt dealServiceExt, DealRepository dealRepository, DealQueryService dealQueryService) {
        this.dealServiceExt = dealServiceExt;
        this.dealRepository = dealRepository;
        this.dealQueryService = dealQueryService;
    }

    /**
     * {@code POST  /deals} : Create a new deal.
     *
     * @param deal the deal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deal, or with status {@code 400 (Bad Request)} if the deal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/naari-deals")
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) throws URISyntaxException {
        log.debug("REST request to save Deal : {}", deal);
        if (deal.getId() != null) {
            throw new BadRequestAlertException("A new deal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deal result = dealServiceExt.save(deal);
        return ResponseEntity
            .created(new URI("/api/deals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deals/:id} : Updates an existing deal.
     *
     * @param id the id of the deal to save.
     * @param deal the deal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deal,
     * or with status {@code 400 (Bad Request)} if the deal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/naari-deals/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Deal deal)
        throws URISyntaxException {
        log.debug("REST request to update Deal : {}, {}", id, deal);
        if (deal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Deal result = dealServiceExt.update(deal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deals/:id} : Partial updates given fields of an existing deal, field will ignore if it is null
     *
     * @param id the id of the deal to save.
     * @param deal the deal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deal,
     * or with status {@code 400 (Bad Request)} if the deal is not valid,
     * or with status {@code 404 (Not Found)} if the deal is not found,
     * or with status {@code 500 (Internal Server Error)} if the deal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/naari-deals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Deal> partialUpdateDeal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Deal deal)
        throws URISyntaxException {
        log.debug("REST request to partial update Deal partially : {}, {}", id, deal);
        if (deal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dealRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deal> result = dealServiceExt.partialUpdate(deal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deal.getId().toString())
        );
    }

    /**
     * {@code GET  /deals} : get all the deals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deals in body.
     */
    @GetMapping("/naari-deals")
    public ResponseEntity<List<Deal>> getAllDeals(DealCriteria criteria, @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get Deals by criteria: {}", criteria);
        Page<Deal> page = dealQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /deals/:id} : delete the "id" deal.
     *
     * @param id the id of the deal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/naari-deals/{id}")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        log.debug("REST request to delete Deals with ids : {}", id);
        dealServiceExt.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
