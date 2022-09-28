package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.LoginProfile;
import com.deals.naari.repository.LoginProfileRepository;
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
 * Integration tests for the {@link LoginProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoginProfileResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/login-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoginProfileRepository loginProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoginProfileMockMvc;

    private LoginProfile loginProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginProfile createEntity(EntityManager em) {
        LoginProfile loginProfile = new LoginProfile()
            .userName(DEFAULT_USER_NAME)
            .userId(DEFAULT_USER_ID)
            .memberType(DEFAULT_MEMBER_TYPE)
            .memberId(DEFAULT_MEMBER_ID)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailId(DEFAULT_EMAIL_ID)
            .password(DEFAULT_PASSWORD)
            .activationStatus(DEFAULT_ACTIVATION_STATUS)
            .activationCode(DEFAULT_ACTIVATION_CODE);
        return loginProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginProfile createUpdatedEntity(EntityManager em) {
        LoginProfile loginProfile = new LoginProfile()
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberType(UPDATED_MEMBER_TYPE)
            .memberId(UPDATED_MEMBER_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);
        return loginProfile;
    }

    @BeforeEach
    public void initTest() {
        loginProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createLoginProfile() throws Exception {
        int databaseSizeBeforeCreate = loginProfileRepository.findAll().size();
        // Create the LoginProfile
        restLoginProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isCreated());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeCreate + 1);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testLoginProfile.getActivationStatus()).isEqualTo(DEFAULT_ACTIVATION_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(DEFAULT_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void createLoginProfileWithExistingId() throws Exception {
        // Create the LoginProfile with an existing ID
        loginProfile.setId(1L);

        int databaseSizeBeforeCreate = loginProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoginProfiles() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get all the loginProfileList
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE)))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS)))
            .andExpect(jsonPath("$.[*].activationCode").value(hasItem(DEFAULT_ACTIVATION_CODE)));
    }

    @Test
    @Transactional
    void getLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        // Get the loginProfile
        restLoginProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, loginProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loginProfile.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE))
            .andExpect(jsonPath("$.memberId").value(DEFAULT_MEMBER_ID))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS))
            .andExpect(jsonPath("$.activationCode").value(DEFAULT_ACTIVATION_CODE));
    }

    @Test
    @Transactional
    void getNonExistingLoginProfile() throws Exception {
        // Get the loginProfile
        restLoginProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile
        LoginProfile updatedLoginProfile = loginProfileRepository.findById(loginProfile.getId()).get();
        // Disconnect from session so that the updates on updatedLoginProfile are not directly saved in db
        em.detach(updatedLoginProfile);
        updatedLoginProfile
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberType(UPDATED_MEMBER_TYPE)
            .memberId(UPDATED_MEMBER_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLoginProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getActivationStatus()).isEqualTo(UPDATED_ACTIVATION_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void putNonExistingLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loginProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loginProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoginProfileWithPatch() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile using partial update
        LoginProfile partialUpdatedLoginProfile = new LoginProfile();
        partialUpdatedLoginProfile.setId(loginProfile.getId());

        partialUpdatedLoginProfile
            .userName(UPDATED_USER_NAME)
            .memberId(UPDATED_MEMBER_ID)
            .emailId(UPDATED_EMAIL_ID)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testLoginProfile.getActivationStatus()).isEqualTo(UPDATED_ACTIVATION_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void fullUpdateLoginProfileWithPatch() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();

        // Update the loginProfile using partial update
        LoginProfile partialUpdatedLoginProfile = new LoginProfile();
        partialUpdatedLoginProfile.setId(loginProfile.getId());

        partialUpdatedLoginProfile
            .userName(UPDATED_USER_NAME)
            .userId(UPDATED_USER_ID)
            .memberType(UPDATED_MEMBER_TYPE)
            .memberId(UPDATED_MEMBER_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailId(UPDATED_EMAIL_ID)
            .password(UPDATED_PASSWORD)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .activationCode(UPDATED_ACTIVATION_CODE);

        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoginProfile))
            )
            .andExpect(status().isOk());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
        LoginProfile testLoginProfile = loginProfileList.get(loginProfileList.size() - 1);
        assertThat(testLoginProfile.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testLoginProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLoginProfile.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testLoginProfile.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testLoginProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testLoginProfile.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testLoginProfile.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testLoginProfile.getActivationStatus()).isEqualTo(UPDATED_ACTIVATION_STATUS);
        assertThat(testLoginProfile.getActivationCode()).isEqualTo(UPDATED_ACTIVATION_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loginProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoginProfile() throws Exception {
        int databaseSizeBeforeUpdate = loginProfileRepository.findAll().size();
        loginProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoginProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loginProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoginProfile in the database
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoginProfile() throws Exception {
        // Initialize the database
        loginProfileRepository.saveAndFlush(loginProfile);

        int databaseSizeBeforeDelete = loginProfileRepository.findAll().size();

        // Delete the loginProfile
        restLoginProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, loginProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginProfile> loginProfileList = loginProfileRepository.findAll();
        assertThat(loginProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
