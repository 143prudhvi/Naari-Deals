package com.deals.naari.web.rest;

import com.deals.naari.domain.BioProfile;
import com.deals.naari.repository.BioProfileRepository;
import com.deals.naari.service.BioProfileQueryService;
import com.deals.naari.service.BioProfileService;
import com.deals.naari.service.criteria.BioProfileCriteria;
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
 * REST controller for managing {@link com.deals.naari.domain.BioProfile}.
 */
@RestController
@RequestMapping("/api")
public class BioProfileResource {

    private final Logger log = LoggerFactory.getLogger(BioProfileResource.class);

    private static final String ENTITY_NAME = "bioProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BioProfileService bioProfileService;

    private final BioProfileRepository bioProfileRepository;

    private final BioProfileQueryService bioProfileQueryService;

    public BioProfileResource(
        BioProfileService bioProfileService,
        BioProfileRepository bioProfileRepository,
        BioProfileQueryService bioProfileQueryService
    ) {
        this.bioProfileService = bioProfileService;
        this.bioProfileRepository = bioProfileRepository;
        this.bioProfileQueryService = bioProfileQueryService;
    }

    /**
     * {@code POST  /bio-profiles} : Create a new bioProfile.
     *
     * @param bioProfile the bioProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bioProfile, or with status {@code 400 (Bad Request)} if the bioProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bio-profiles")
    public ResponseEntity<BioProfile> createBioProfile(@RequestBody BioProfile bioProfile) throws URISyntaxException {
        log.debug("REST request to save BioProfile : {}", bioProfile);
        if (bioProfile.getId() != null) {
            throw new BadRequestAlertException("A new bioProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BioProfile result = bioProfileService.save(bioProfile);
        return ResponseEntity
            .created(new URI("/api/bio-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bio-profiles/:id} : Updates an existing bioProfile.
     *
     * @param id the id of the bioProfile to save.
     * @param bioProfile the bioProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bioProfile,
     * or with status {@code 400 (Bad Request)} if the bioProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bioProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bio-profiles/{id}")
    public ResponseEntity<BioProfile> updateBioProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BioProfile bioProfile
    ) throws URISyntaxException {
        log.debug("REST request to update BioProfile : {}, {}", id, bioProfile);
        if (bioProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bioProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bioProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BioProfile result = bioProfileService.update(bioProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bioProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bio-profiles/:id} : Partial updates given fields of an existing bioProfile, field will ignore if it is null
     *
     * @param id the id of the bioProfile to save.
     * @param bioProfile the bioProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bioProfile,
     * or with status {@code 400 (Bad Request)} if the bioProfile is not valid,
     * or with status {@code 404 (Not Found)} if the bioProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the bioProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bio-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BioProfile> partialUpdateBioProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BioProfile bioProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update BioProfile partially : {}, {}", id, bioProfile);
        if (bioProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bioProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bioProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BioProfile> result = bioProfileService.partialUpdate(bioProfile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bioProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /bio-profiles} : get all the bioProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bioProfiles in body.
     */
    @GetMapping("/bio-profiles")
    public ResponseEntity<List<BioProfile>> getAllBioProfiles(BioProfileCriteria criteria) {
        log.debug("REST request to get BioProfiles by criteria: {}", criteria);
        List<BioProfile> entityList = bioProfileQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /bio-profiles/count} : count all the bioProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bio-profiles/count")
    public ResponseEntity<Long> countBioProfiles(BioProfileCriteria criteria) {
        log.debug("REST request to count BioProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(bioProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bio-profiles/:id} : get the "id" bioProfile.
     *
     * @param id the id of the bioProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bioProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bio-profiles/{id}")
    public ResponseEntity<BioProfile> getBioProfile(@PathVariable Long id) {
        log.debug("REST request to get BioProfile : {}", id);
        Optional<BioProfile> bioProfile = bioProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bioProfile);
    }

    /**
     * {@code DELETE  /bio-profiles/:id} : delete the "id" bioProfile.
     *
     * @param id the id of the bioProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bio-profiles/{id}")
    public ResponseEntity<Void> deleteBioProfile(@PathVariable Long id) {
        log.debug("REST request to delete BioProfile : {}", id);
        bioProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
