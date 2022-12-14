package com.deals.naari.web.rest;

import com.deals.naari.domain.Slide;
import com.deals.naari.repository.SlideRepository;
import com.deals.naari.service.SlideQueryService;
import com.deals.naari.service.SlideService;
import com.deals.naari.service.criteria.SlideCriteria;
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
 * REST controller for managing {@link com.deals.naari.domain.Slide}.
 */
@RestController
@RequestMapping("/api")
public class SlideResource {

    private final Logger log = LoggerFactory.getLogger(SlideResource.class);

    private static final String ENTITY_NAME = "slide";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SlideService slideService;

    private final SlideRepository slideRepository;

    private final SlideQueryService slideQueryService;

    public SlideResource(SlideService slideService, SlideRepository slideRepository, SlideQueryService slideQueryService) {
        this.slideService = slideService;
        this.slideRepository = slideRepository;
        this.slideQueryService = slideQueryService;
    }

    /**
     * {@code POST  /slides} : Create a new slide.
     *
     * @param slide the slide to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new slide, or with status {@code 400 (Bad Request)} if the slide has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slides")
    public ResponseEntity<Slide> createSlide(@RequestBody Slide slide) throws URISyntaxException {
        log.debug("REST request to save Slide : {}", slide);
        if (slide.getId() != null) {
            throw new BadRequestAlertException("A new slide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Slide result = slideService.save(slide);
        return ResponseEntity
            .created(new URI("/api/slides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /slides/:id} : Updates an existing slide.
     *
     * @param id the id of the slide to save.
     * @param slide the slide to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slide,
     * or with status {@code 400 (Bad Request)} if the slide is not valid,
     * or with status {@code 500 (Internal Server Error)} if the slide couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slides/{id}")
    public ResponseEntity<Slide> updateSlide(@PathVariable(value = "id", required = false) final Long id, @RequestBody Slide slide)
        throws URISyntaxException {
        log.debug("REST request to update Slide : {}, {}", id, slide);
        if (slide.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, slide.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!slideRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Slide result = slideService.update(slide);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, slide.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /slides/:id} : Partial updates given fields of an existing slide, field will ignore if it is null
     *
     * @param id the id of the slide to save.
     * @param slide the slide to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slide,
     * or with status {@code 400 (Bad Request)} if the slide is not valid,
     * or with status {@code 404 (Not Found)} if the slide is not found,
     * or with status {@code 500 (Internal Server Error)} if the slide couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/slides/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Slide> partialUpdateSlide(@PathVariable(value = "id", required = false) final Long id, @RequestBody Slide slide)
        throws URISyntaxException {
        log.debug("REST request to partial update Slide partially : {}, {}", id, slide);
        if (slide.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, slide.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!slideRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Slide> result = slideService.partialUpdate(slide);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, slide.getId().toString())
        );
    }

    /**
     * {@code GET  /slides} : get all the slides.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slides in body.
     */
    @GetMapping("/slides")
    public ResponseEntity<List<Slide>> getAllSlides(SlideCriteria criteria) {
        log.debug("REST request to get Slides by criteria: {}", criteria);
        List<Slide> entityList = slideQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /slides/count} : count all the slides.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/slides/count")
    public ResponseEntity<Long> countSlides(SlideCriteria criteria) {
        log.debug("REST request to count Slides by criteria: {}", criteria);
        return ResponseEntity.ok().body(slideQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /slides/:id} : get the "id" slide.
     *
     * @param id the id of the slide to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the slide, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slides/{id}")
    public ResponseEntity<Slide> getSlide(@PathVariable Long id) {
        log.debug("REST request to get Slide : {}", id);
        Optional<Slide> slide = slideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(slide);
    }

    /**
     * {@code DELETE  /slides/:id} : delete the "id" slide.
     *
     * @param id the id of the slide to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slides/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id) {
        log.debug("REST request to delete Slide : {}", id);
        slideService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
