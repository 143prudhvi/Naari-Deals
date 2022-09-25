package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.EmailSubscription;
import com.deals.naari.repository.EmailSubscriptionRepository;
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
 * Integration tests for the {@link EmailSubscriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailSubscriptionResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/email-subscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailSubscriptionRepository emailSubscriptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailSubscriptionMockMvc;

    private EmailSubscription emailSubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailSubscription createEntity(EntityManager em) {
        EmailSubscription emailSubscription = new EmailSubscription().email(DEFAULT_EMAIL).country(DEFAULT_COUNTRY);
        return emailSubscription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailSubscription createUpdatedEntity(EntityManager em) {
        EmailSubscription emailSubscription = new EmailSubscription().email(UPDATED_EMAIL).country(UPDATED_COUNTRY);
        return emailSubscription;
    }

    @BeforeEach
    public void initTest() {
        emailSubscription = createEntity(em);
    }

    @Test
    @Transactional
    void createEmailSubscription() throws Exception {
        int databaseSizeBeforeCreate = emailSubscriptionRepository.findAll().size();
        // Create the EmailSubscription
        restEmailSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isCreated());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        EmailSubscription testEmailSubscription = emailSubscriptionList.get(emailSubscriptionList.size() - 1);
        assertThat(testEmailSubscription.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmailSubscription.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createEmailSubscriptionWithExistingId() throws Exception {
        // Create the EmailSubscription with an existing ID
        emailSubscription.setId(1L);

        int databaseSizeBeforeCreate = emailSubscriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmailSubscriptions() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        // Get all the emailSubscriptionList
        restEmailSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getEmailSubscription() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        // Get the emailSubscription
        restEmailSubscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, emailSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailSubscription.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getNonExistingEmailSubscription() throws Exception {
        // Get the emailSubscription
        restEmailSubscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmailSubscription() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();

        // Update the emailSubscription
        EmailSubscription updatedEmailSubscription = emailSubscriptionRepository.findById(emailSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedEmailSubscription are not directly saved in db
        em.detach(updatedEmailSubscription);
        updatedEmailSubscription.email(UPDATED_EMAIL).country(UPDATED_COUNTRY);

        restEmailSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmailSubscription.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmailSubscription))
            )
            .andExpect(status().isOk());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        EmailSubscription testEmailSubscription = emailSubscriptionList.get(emailSubscriptionList.size() - 1);
        assertThat(testEmailSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailSubscription.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();
        emailSubscription.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailSubscription.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();
        emailSubscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();
        emailSubscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailSubscriptionWithPatch() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();

        // Update the emailSubscription using partial update
        EmailSubscription partialUpdatedEmailSubscription = new EmailSubscription();
        partialUpdatedEmailSubscription.setId(emailSubscription.getId());

        partialUpdatedEmailSubscription.email(UPDATED_EMAIL).country(UPDATED_COUNTRY);

        restEmailSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailSubscription))
            )
            .andExpect(status().isOk());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        EmailSubscription testEmailSubscription = emailSubscriptionList.get(emailSubscriptionList.size() - 1);
        assertThat(testEmailSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailSubscription.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateEmailSubscriptionWithPatch() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();

        // Update the emailSubscription using partial update
        EmailSubscription partialUpdatedEmailSubscription = new EmailSubscription();
        partialUpdatedEmailSubscription.setId(emailSubscription.getId());

        partialUpdatedEmailSubscription.email(UPDATED_EMAIL).country(UPDATED_COUNTRY);

        restEmailSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailSubscription))
            )
            .andExpect(status().isOk());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        EmailSubscription testEmailSubscription = emailSubscriptionList.get(emailSubscriptionList.size() - 1);
        assertThat(testEmailSubscription.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailSubscription.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();
        emailSubscription.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailSubscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();
        emailSubscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmailSubscription() throws Exception {
        int databaseSizeBeforeUpdate = emailSubscriptionRepository.findAll().size();
        emailSubscription.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailSubscription))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmailSubscription in the database
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmailSubscription() throws Exception {
        // Initialize the database
        emailSubscriptionRepository.saveAndFlush(emailSubscription);

        int databaseSizeBeforeDelete = emailSubscriptionRepository.findAll().size();

        // Delete the emailSubscription
        restEmailSubscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, emailSubscription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailSubscription> emailSubscriptionList = emailSubscriptionRepository.findAll();
        assertThat(emailSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
