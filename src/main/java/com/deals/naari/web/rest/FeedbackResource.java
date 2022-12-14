package com.deals.naari.web.rest;

import com.deals.naari.domain.Feedback;
import com.deals.naari.repository.FeedbackRepository;
import com.deals.naari.service.FeedbackQueryService;
import com.deals.naari.service.FeedbackService;
import com.deals.naari.service.criteria.FeedbackCriteria;
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
 * REST controller for managing {@link com.deals.naari.domain.Feedback}.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedbackService feedbackService;

    private final FeedbackRepository feedbackRepository;

    private final FeedbackQueryService feedbackQueryService;

    public FeedbackResource(
        FeedbackService feedbackService,
        FeedbackRepository feedbackRepository,
        FeedbackQueryService feedbackQueryService
    ) {
        this.feedbackService = feedbackService;
        this.feedbackRepository = feedbackRepository;
        this.feedbackQueryService = feedbackQueryService;
    }

    /**
     * {@code POST  /feedbacks} : Create a new feedback.
     *
     * @param feedback the feedback to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedback, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feedbacks")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedback);
        if (feedback.getId() != null) {
            throw new BadRequestAlertException("A new feedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Feedback result = feedbackService.save(feedback);
        return ResponseEntity
            .created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feedbacks/:id} : Updates an existing feedback.
     *
     * @param id the id of the feedback to save.
     * @param feedback the feedback to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedback,
     * or with status {@code 400 (Bad Request)} if the feedback is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedback couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> updateFeedback(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Feedback feedback
    ) throws URISyntaxException {
        log.debug("REST request to update Feedback : {}, {}", id, feedback);
        if (feedback.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedback.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Feedback result = feedbackService.update(feedback);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedback.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feedbacks/:id} : Partial updates given fields of an existing feedback, field will ignore if it is null
     *
     * @param id the id of the feedback to save.
     * @param feedback the feedback to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedback,
     * or with status {@code 400 (Bad Request)} if the feedback is not valid,
     * or with status {@code 404 (Not Found)} if the feedback is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedback couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feedbacks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Feedback> partialUpdateFeedback(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Feedback feedback
    ) throws URISyntaxException {
        log.debug("REST request to partial update Feedback partially : {}, {}", id, feedback);
        if (feedback.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedback.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedbackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Feedback> result = feedbackService.partialUpdate(feedback);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedback.getId().toString())
        );
    }

    /**
     * {@code GET  /feedbacks} : get all the feedbacks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks(FeedbackCriteria criteria) {
        log.debug("REST request to get Feedbacks by criteria: {}", criteria);
        List<Feedback> entityList = feedbackQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /feedbacks/count} : count all the feedbacks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/feedbacks/count")
    public ResponseEntity<Long> countFeedbacks(FeedbackCriteria criteria) {
        log.debug("REST request to count Feedbacks by criteria: {}", criteria);
        return ResponseEntity.ok().body(feedbackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /feedbacks/:id} : get the "id" feedback.
     *
     * @param id the id of the feedback to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedback, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<Feedback> feedback = feedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedback);
    }

    /**
     * {@code DELETE  /feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedback to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
