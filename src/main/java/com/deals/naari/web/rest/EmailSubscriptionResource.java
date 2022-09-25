package com.deals.naari.web.rest;

import com.deals.naari.domain.EmailSubscription;
import com.deals.naari.repository.EmailSubscriptionRepository;
import com.deals.naari.service.EmailSubscriptionService;
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
 * REST controller for managing {@link com.deals.naari.domain.EmailSubscription}.
 */
@RestController
@RequestMapping("/api")
public class EmailSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(EmailSubscriptionResource.class);

    private static final String ENTITY_NAME = "emailSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailSubscriptionService emailSubscriptionService;

    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public EmailSubscriptionResource(
        EmailSubscriptionService emailSubscriptionService,
        EmailSubscriptionRepository emailSubscriptionRepository
    ) {
        this.emailSubscriptionService = emailSubscriptionService;
        this.emailSubscriptionRepository = emailSubscriptionRepository;
    }

    /**
     * {@code POST  /email-subscriptions} : Create a new emailSubscription.
     *
     * @param emailSubscription the emailSubscription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailSubscription, or with status {@code 400 (Bad Request)} if the emailSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-subscriptions")
    public ResponseEntity<EmailSubscription> createEmailSubscription(@RequestBody EmailSubscription emailSubscription)
        throws URISyntaxException {
        log.debug("REST request to save EmailSubscription : {}", emailSubscription);
        if (emailSubscription.getId() != null) {
            throw new BadRequestAlertException("A new emailSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailSubscription result = emailSubscriptionService.save(emailSubscription);
        return ResponseEntity
            .created(new URI("/api/email-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-subscriptions/:id} : Updates an existing emailSubscription.
     *
     * @param id the id of the emailSubscription to save.
     * @param emailSubscription the emailSubscription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailSubscription,
     * or with status {@code 400 (Bad Request)} if the emailSubscription is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailSubscription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-subscriptions/{id}")
    public ResponseEntity<EmailSubscription> updateEmailSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailSubscription emailSubscription
    ) throws URISyntaxException {
        log.debug("REST request to update EmailSubscription : {}, {}", id, emailSubscription);
        if (emailSubscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailSubscription.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmailSubscription result = emailSubscriptionService.update(emailSubscription);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailSubscription.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /email-subscriptions/:id} : Partial updates given fields of an existing emailSubscription, field will ignore if it is null
     *
     * @param id the id of the emailSubscription to save.
     * @param emailSubscription the emailSubscription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailSubscription,
     * or with status {@code 400 (Bad Request)} if the emailSubscription is not valid,
     * or with status {@code 404 (Not Found)} if the emailSubscription is not found,
     * or with status {@code 500 (Internal Server Error)} if the emailSubscription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/email-subscriptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmailSubscription> partialUpdateEmailSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailSubscription emailSubscription
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmailSubscription partially : {}, {}", id, emailSubscription);
        if (emailSubscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailSubscription.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmailSubscription> result = emailSubscriptionService.partialUpdate(emailSubscription);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailSubscription.getId().toString())
        );
    }

    /**
     * {@code GET  /email-subscriptions} : get all the emailSubscriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailSubscriptions in body.
     */
    @GetMapping("/email-subscriptions")
    public List<EmailSubscription> getAllEmailSubscriptions() {
        log.debug("REST request to get all EmailSubscriptions");
        return emailSubscriptionService.findAll();
    }

    /**
     * {@code GET  /email-subscriptions/:id} : get the "id" emailSubscription.
     *
     * @param id the id of the emailSubscription to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailSubscription, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-subscriptions/{id}")
    public ResponseEntity<EmailSubscription> getEmailSubscription(@PathVariable Long id) {
        log.debug("REST request to get EmailSubscription : {}", id);
        Optional<EmailSubscription> emailSubscription = emailSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailSubscription);
    }

    /**
     * {@code DELETE  /email-subscriptions/:id} : delete the "id" emailSubscription.
     *
     * @param id the id of the emailSubscription to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-subscriptions/{id}")
    public ResponseEntity<Void> deleteEmailSubscription(@PathVariable Long id) {
        log.debug("REST request to delete EmailSubscription : {}", id);
        emailSubscriptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
