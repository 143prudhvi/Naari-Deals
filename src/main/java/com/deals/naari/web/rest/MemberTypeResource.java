package com.deals.naari.web.rest;

import com.deals.naari.domain.MemberType;
import com.deals.naari.repository.MemberTypeRepository;
import com.deals.naari.service.MemberTypeService;
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
 * REST controller for managing {@link com.deals.naari.domain.MemberType}.
 */
@RestController
@RequestMapping("/api")
public class MemberTypeResource {

    private final Logger log = LoggerFactory.getLogger(MemberTypeResource.class);

    private static final String ENTITY_NAME = "memberType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberTypeService memberTypeService;

    private final MemberTypeRepository memberTypeRepository;

    public MemberTypeResource(MemberTypeService memberTypeService, MemberTypeRepository memberTypeRepository) {
        this.memberTypeService = memberTypeService;
        this.memberTypeRepository = memberTypeRepository;
    }

    /**
     * {@code POST  /member-types} : Create a new memberType.
     *
     * @param memberType the memberType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberType, or with status {@code 400 (Bad Request)} if the memberType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-types")
    public ResponseEntity<MemberType> createMemberType(@RequestBody MemberType memberType) throws URISyntaxException {
        log.debug("REST request to save MemberType : {}", memberType);
        if (memberType.getId() != null) {
            throw new BadRequestAlertException("A new memberType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberType result = memberTypeService.save(memberType);
        return ResponseEntity
            .created(new URI("/api/member-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-types/:id} : Updates an existing memberType.
     *
     * @param id the id of the memberType to save.
     * @param memberType the memberType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberType,
     * or with status {@code 400 (Bad Request)} if the memberType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-types/{id}")
    public ResponseEntity<MemberType> updateMemberType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberType memberType
    ) throws URISyntaxException {
        log.debug("REST request to update MemberType : {}, {}", id, memberType);
        if (memberType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberType result = memberTypeService.update(memberType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, memberType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-types/:id} : Partial updates given fields of an existing memberType, field will ignore if it is null
     *
     * @param id the id of the memberType to save.
     * @param memberType the memberType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberType,
     * or with status {@code 400 (Bad Request)} if the memberType is not valid,
     * or with status {@code 404 (Not Found)} if the memberType is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberType> partialUpdateMemberType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberType memberType
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberType partially : {}, {}", id, memberType);
        if (memberType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberType> result = memberTypeService.partialUpdate(memberType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, memberType.getId().toString())
        );
    }

    /**
     * {@code GET  /member-types} : get all the memberTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberTypes in body.
     */
    @GetMapping("/member-types")
    public List<MemberType> getAllMemberTypes() {
        log.debug("REST request to get all MemberTypes");
        return memberTypeService.findAll();
    }

    /**
     * {@code GET  /member-types/:id} : get the "id" memberType.
     *
     * @param id the id of the memberType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-types/{id}")
    public ResponseEntity<MemberType> getMemberType(@PathVariable Long id) {
        log.debug("REST request to get MemberType : {}", id);
        Optional<MemberType> memberType = memberTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberType);
    }

    /**
     * {@code DELETE  /member-types/:id} : delete the "id" memberType.
     *
     * @param id the id of the memberType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-types/{id}")
    public ResponseEntity<Void> deleteMemberType(@PathVariable Long id) {
        log.debug("REST request to delete MemberType : {}", id);
        memberTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
