package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.BioProfile;
import com.deals.naari.repository.BioProfileRepository;
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
 * Integration tests for the {@link BioProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BioProfileResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOB = "AAAAAAAAAA";
    private static final String UPDATED_DOB = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bio-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BioProfileRepository bioProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBioProfileMockMvc;

    private BioProfile bioProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BioProfile createEntity(EntityManager em) {
        BioProfile bioProfile = new BioProfile()
            .userId(DEFAULT_USER_ID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dob(DEFAULT_DOB)
            .gender(DEFAULT_GENDER)
            .imageUrl(DEFAULT_IMAGE_URL);
        return bioProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BioProfile createUpdatedEntity(EntityManager em) {
        BioProfile bioProfile = new BioProfile()
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .imageUrl(UPDATED_IMAGE_URL);
        return bioProfile;
    }

    @BeforeEach
    public void initTest() {
        bioProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createBioProfile() throws Exception {
        int databaseSizeBeforeCreate = bioProfileRepository.findAll().size();
        // Create the BioProfile
        restBioProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bioProfile)))
            .andExpect(status().isCreated());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeCreate + 1);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createBioProfileWithExistingId() throws Exception {
        // Create the BioProfile with an existing ID
        bioProfile.setId(1L);

        int databaseSizeBeforeCreate = bioProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBioProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bioProfile)))
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBioProfiles() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get all the bioProfileList
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bioProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getBioProfile() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        // Get the bioProfile
        restBioProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, bioProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bioProfile.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingBioProfile() throws Exception {
        // Get the bioProfile
        restBioProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBioProfile() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();

        // Update the bioProfile
        BioProfile updatedBioProfile = bioProfileRepository.findById(bioProfile.getId()).get();
        // Disconnect from session so that the updates on updatedBioProfile are not directly saved in db
        em.detach(updatedBioProfile);
        updatedBioProfile
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .imageUrl(UPDATED_IMAGE_URL);

        restBioProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBioProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBioProfile))
            )
            .andExpect(status().isOk());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bioProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bioProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBioProfileWithPatch() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();

        // Update the bioProfile using partial update
        BioProfile partialUpdatedBioProfile = new BioProfile();
        partialUpdatedBioProfile.setId(bioProfile.getId());

        partialUpdatedBioProfile.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).dob(UPDATED_DOB).imageUrl(UPDATED_IMAGE_URL);

        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBioProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBioProfile))
            )
            .andExpect(status().isOk());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateBioProfileWithPatch() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();

        // Update the bioProfile using partial update
        BioProfile partialUpdatedBioProfile = new BioProfile();
        partialUpdatedBioProfile.setId(bioProfile.getId());

        partialUpdatedBioProfile
            .userId(UPDATED_USER_ID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .imageUrl(UPDATED_IMAGE_URL);

        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBioProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBioProfile))
            )
            .andExpect(status().isOk());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
        BioProfile testBioProfile = bioProfileList.get(bioProfileList.size() - 1);
        assertThat(testBioProfile.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBioProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBioProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBioProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testBioProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testBioProfile.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bioProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBioProfile() throws Exception {
        int databaseSizeBeforeUpdate = bioProfileRepository.findAll().size();
        bioProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBioProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bioProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BioProfile in the database
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBioProfile() throws Exception {
        // Initialize the database
        bioProfileRepository.saveAndFlush(bioProfile);

        int databaseSizeBeforeDelete = bioProfileRepository.findAll().size();

        // Delete the bioProfile
        restBioProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, bioProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BioProfile> bioProfileList = bioProfileRepository.findAll();
        assertThat(bioProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
