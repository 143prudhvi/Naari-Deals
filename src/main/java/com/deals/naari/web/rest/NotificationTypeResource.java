package com.deals.naari.web.rest;

import com.deals.naari.domain.NotificationType;
import com.deals.naari.repository.NotificationTypeRepository;
import com.deals.naari.service.NotificationTypeService;
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
 * REST controller for managing {@link com.deals.naari.domain.NotificationType}.
 */
@RestController
@RequestMapping("/api")
public class NotificationTypeResource {

    private final Logger log = LoggerFactory.getLogger(NotificationTypeResource.class);

    private static final String ENTITY_NAME = "notificationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationTypeService notificationTypeService;

    private final NotificationTypeRepository notificationTypeRepository;

    public NotificationTypeResource(
        NotificationTypeService notificationTypeService,
        NotificationTypeRepository notificationTypeRepository
    ) {
        this.notificationTypeService = notificationTypeService;
        this.notificationTypeRepository = notificationTypeRepository;
    }

    /**
     * {@code POST  /notification-types} : Create a new notificationType.
     *
     * @param notificationType the notificationType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationType, or with status {@code 400 (Bad Request)} if the notificationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-types")
    public ResponseEntity<NotificationType> createNotificationType(@RequestBody NotificationType notificationType)
        throws URISyntaxException {
        log.debug("REST request to save NotificationType : {}", notificationType);
        if (notificationType.getId() != null) {
            throw new BadRequestAlertException("A new notificationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationType result = notificationTypeService.save(notificationType);
        return ResponseEntity
            .created(new URI("/api/notification-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-types/:id} : Updates an existing notificationType.
     *
     * @param id the id of the notificationType to save.
     * @param notificationType the notificationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationType,
     * or with status {@code 400 (Bad Request)} if the notificationType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-types/{id}")
    public ResponseEntity<NotificationType> updateNotificationType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationType notificationType
    ) throws URISyntaxException {
        log.debug("REST request to update NotificationType : {}, {}", id, notificationType);
        if (notificationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotificationType result = notificationTypeService.update(notificationType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notification-types/:id} : Partial updates given fields of an existing notificationType, field will ignore if it is null
     *
     * @param id the id of the notificationType to save.
     * @param notificationType the notificationType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationType,
     * or with status {@code 400 (Bad Request)} if the notificationType is not valid,
     * or with status {@code 404 (Not Found)} if the notificationType is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationType> partialUpdateNotificationType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationType notificationType
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotificationType partially : {}, {}", id, notificationType);
        if (notificationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notificationType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notificationTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotificationType> result = notificationTypeService.partialUpdate(notificationType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notificationType.getId().toString())
        );
    }

    /**
     * {@code GET  /notification-types} : get all the notificationTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationTypes in body.
     */
    @GetMapping("/notification-types")
    public List<NotificationType> getAllNotificationTypes() {
        log.debug("REST request to get all NotificationTypes");
        return notificationTypeService.findAll();
    }

    /**
     * {@code GET  /notification-types/:id} : get the "id" notificationType.
     *
     * @param id the id of the notificationType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-types/{id}")
    public ResponseEntity<NotificationType> getNotificationType(@PathVariable Long id) {
        log.debug("REST request to get NotificationType : {}", id);
        Optional<NotificationType> notificationType = notificationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationType);
    }

    /**
     * {@code DELETE  /notification-types/:id} : delete the "id" notificationType.
     *
     * @param id the id of the notificationType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-types/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable Long id) {
        log.debug("REST request to delete NotificationType : {}", id);
        notificationTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
