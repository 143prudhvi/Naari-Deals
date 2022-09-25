package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.NotificationType;
import com.deals.naari.repository.NotificationTypeRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NotificationTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notification-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationTypeMockMvc;

    private NotificationType notificationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationType createEntity(EntityManager em) {
        NotificationType notificationType = new NotificationType().type(DEFAULT_TYPE).description(DEFAULT_DESCRIPTION);
        return notificationType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationType createUpdatedEntity(EntityManager em) {
        NotificationType notificationType = new NotificationType().type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);
        return notificationType;
    }

    @BeforeEach
    public void initTest() {
        notificationType = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationType() throws Exception {
        int databaseSizeBeforeCreate = notificationTypeRepository.findAll().size();
        // Create the NotificationType
        restNotificationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationType testNotificationType = notificationTypeList.get(notificationTypeList.size() - 1);
        assertThat(testNotificationType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNotificationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createNotificationTypeWithExistingId() throws Exception {
        // Create the NotificationType with an existing ID
        notificationType.setId(1L);

        int databaseSizeBeforeCreate = notificationTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotificationTypes() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList
        restNotificationTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getNotificationType() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get the notificationType
        restNotificationTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingNotificationType() throws Exception {
        // Get the notificationType
        restNotificationTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotificationType() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();

        // Update the notificationType
        NotificationType updatedNotificationType = notificationTypeRepository.findById(notificationType.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationType are not directly saved in db
        em.detach(updatedNotificationType);
        updatedNotificationType.type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);

        restNotificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotificationType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotificationType))
            )
            .andExpect(status().isOk());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
        NotificationType testNotificationType = notificationTypeList.get(notificationTypeList.size() - 1);
        assertThat(testNotificationType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotificationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();
        notificationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();
        notificationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();
        notificationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationTypeWithPatch() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();

        // Update the notificationType using partial update
        NotificationType partialUpdatedNotificationType = new NotificationType();
        partialUpdatedNotificationType.setId(notificationType.getId());

        partialUpdatedNotificationType.description(UPDATED_DESCRIPTION);

        restNotificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationType))
            )
            .andExpect(status().isOk());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
        NotificationType testNotificationType = notificationTypeList.get(notificationTypeList.size() - 1);
        assertThat(testNotificationType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNotificationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateNotificationTypeWithPatch() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();

        // Update the notificationType using partial update
        NotificationType partialUpdatedNotificationType = new NotificationType();
        partialUpdatedNotificationType.setId(notificationType.getId());

        partialUpdatedNotificationType.type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);

        restNotificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationType))
            )
            .andExpect(status().isOk());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
        NotificationType testNotificationType = notificationTypeList.get(notificationTypeList.size() - 1);
        assertThat(testNotificationType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotificationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();
        notificationType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();
        notificationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();
        notificationType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationType() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        int databaseSizeBeforeDelete = notificationTypeRepository.findAll().size();

        // Delete the notificationType
        restNotificationTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
