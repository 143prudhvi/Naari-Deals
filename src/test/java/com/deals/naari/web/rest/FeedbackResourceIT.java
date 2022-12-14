package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Feedback;
import com.deals.naari.repository.FeedbackRepository;
import com.deals.naari.service.criteria.FeedbackCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FeedbackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/feedbacks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .userId(DEFAULT_USER_ID)
            .message(DEFAULT_MESSAGE);
        return feedback;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createUpdatedEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE);
        return feedback;
    }

    @BeforeEach
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();
        // Create the Feedback
        restFeedbackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFeedback.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeedback.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFeedback.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testFeedback.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testFeedback.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createFeedbackWithExistingId() throws Exception {
        // Create the Feedback with an existing ID
        feedback.setId(1L);

        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList
        restFeedbackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }

    @Test
    @Transactional
    void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc
            .perform(get(ENTITY_API_URL_ID, feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    void getFeedbacksByIdFiltering() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        Long id = feedback.getId();

        defaultFeedbackShouldBeFound("id.equals=" + id);
        defaultFeedbackShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbacksByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where type equals to DEFAULT_TYPE
        defaultFeedbackShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the feedbackList where type equals to UPDATED_TYPE
        defaultFeedbackShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultFeedbackShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the feedbackList where type equals to UPDATED_TYPE
        defaultFeedbackShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where type is not null
        defaultFeedbackShouldBeFound("type.specified=true");

        // Get all the feedbackList where type is null
        defaultFeedbackShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByTypeContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where type contains DEFAULT_TYPE
        defaultFeedbackShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the feedbackList where type contains UPDATED_TYPE
        defaultFeedbackShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where type does not contain DEFAULT_TYPE
        defaultFeedbackShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the feedbackList where type does not contain UPDATED_TYPE
        defaultFeedbackShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where name equals to DEFAULT_NAME
        defaultFeedbackShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the feedbackList where name equals to UPDATED_NAME
        defaultFeedbackShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFeedbackShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the feedbackList where name equals to UPDATED_NAME
        defaultFeedbackShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where name is not null
        defaultFeedbackShouldBeFound("name.specified=true");

        // Get all the feedbackList where name is null
        defaultFeedbackShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByNameContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where name contains DEFAULT_NAME
        defaultFeedbackShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the feedbackList where name contains UPDATED_NAME
        defaultFeedbackShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where name does not contain DEFAULT_NAME
        defaultFeedbackShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the feedbackList where name does not contain UPDATED_NAME
        defaultFeedbackShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where email equals to DEFAULT_EMAIL
        defaultFeedbackShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the feedbackList where email equals to UPDATED_EMAIL
        defaultFeedbackShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultFeedbackShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the feedbackList where email equals to UPDATED_EMAIL
        defaultFeedbackShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where email is not null
        defaultFeedbackShouldBeFound("email.specified=true");

        // Get all the feedbackList where email is null
        defaultFeedbackShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByEmailContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where email contains DEFAULT_EMAIL
        defaultFeedbackShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the feedbackList where email contains UPDATED_EMAIL
        defaultFeedbackShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where email does not contain DEFAULT_EMAIL
        defaultFeedbackShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the feedbackList where email does not contain UPDATED_EMAIL
        defaultFeedbackShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFeedbacksByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where phone equals to DEFAULT_PHONE
        defaultFeedbackShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the feedbackList where phone equals to UPDATED_PHONE
        defaultFeedbackShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultFeedbackShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the feedbackList where phone equals to UPDATED_PHONE
        defaultFeedbackShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where phone is not null
        defaultFeedbackShouldBeFound("phone.specified=true");

        // Get all the feedbackList where phone is null
        defaultFeedbackShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByPhoneContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where phone contains DEFAULT_PHONE
        defaultFeedbackShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the feedbackList where phone contains UPDATED_PHONE
        defaultFeedbackShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where phone does not contain DEFAULT_PHONE
        defaultFeedbackShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the feedbackList where phone does not contain UPDATED_PHONE
        defaultFeedbackShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFeedbacksByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where userId equals to DEFAULT_USER_ID
        defaultFeedbackShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the feedbackList where userId equals to UPDATED_USER_ID
        defaultFeedbackShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultFeedbackShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the feedbackList where userId equals to UPDATED_USER_ID
        defaultFeedbackShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where userId is not null
        defaultFeedbackShouldBeFound("userId.specified=true");

        // Get all the feedbackList where userId is null
        defaultFeedbackShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByUserIdContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where userId contains DEFAULT_USER_ID
        defaultFeedbackShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the feedbackList where userId contains UPDATED_USER_ID
        defaultFeedbackShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where userId does not contain DEFAULT_USER_ID
        defaultFeedbackShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the feedbackList where userId does not contain UPDATED_USER_ID
        defaultFeedbackShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackShouldBeFound(String filter) throws Exception {
        restFeedbackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));

        // Check, that the count call also returns 1
        restFeedbackMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackShouldNotBeFound(String filter) throws Exception {
        restFeedbackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE);

        restFeedbackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeedback.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFeedback))
            )
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFeedback.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeedback.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFeedback.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testFeedback.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFeedback.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();
        feedback.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feedback.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedback))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();
        feedback.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedback))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();
        feedback.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackWithPatch() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback using partial update
        Feedback partialUpdatedFeedback = new Feedback();
        partialUpdatedFeedback.setId(feedback.getId());

        partialUpdatedFeedback.name(UPDATED_NAME).userId(UPDATED_USER_ID);

        restFeedbackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedback.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedback))
            )
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFeedback.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeedback.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFeedback.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testFeedback.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFeedback.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackWithPatch() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback using partial update
        Feedback partialUpdatedFeedback = new Feedback();
        partialUpdatedFeedback.setId(feedback.getId());

        partialUpdatedFeedback
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE);

        restFeedbackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeedback.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedback))
            )
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFeedback.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeedback.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFeedback.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testFeedback.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFeedback.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();
        feedback.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feedback.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedback))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();
        feedback.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feedback))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();
        feedback.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Delete the feedback
        restFeedbackMockMvc
            .perform(delete(ENTITY_API_URL_ID, feedback.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
