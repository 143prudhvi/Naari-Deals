package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.DealType;
import com.deals.naari.repository.DealTypeRepository;
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
 * Integration tests for the {@link DealTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealTypeResourceIT {

    private static final String DEFAULT_DEAL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deal-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealTypeRepository dealTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealTypeMockMvc;

    private DealType dealType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealType createEntity(EntityManager em) {
        DealType dealType = new DealType().dealType(DEFAULT_DEAL_TYPE).description(DEFAULT_DESCRIPTION).imageUrl(DEFAULT_IMAGE_URL);
        return dealType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealType createUpdatedEntity(EntityManager em) {
        DealType dealType = new DealType().dealType(UPDATED_DEAL_TYPE).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);
        return dealType;
    }

    @BeforeEach
    public void initTest() {
        dealType = createEntity(em);
    }

    @Test
    @Transactional
    void createDealType() throws Exception {
        int databaseSizeBeforeCreate = dealTypeRepository.findAll().size();
        // Create the DealType
        restDealTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealType)))
            .andExpect(status().isCreated());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DealType testDealType = dealTypeList.get(dealTypeList.size() - 1);
        assertThat(testDealType.getDealType()).isEqualTo(DEFAULT_DEAL_TYPE);
        assertThat(testDealType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDealType.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createDealTypeWithExistingId() throws Exception {
        // Create the DealType with an existing ID
        dealType.setId(1L);

        int databaseSizeBeforeCreate = dealTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealType)))
            .andExpect(status().isBadRequest());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDealTypes() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList
        restDealTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealType.getId().intValue())))
            .andExpect(jsonPath("$.[*].dealType").value(hasItem(DEFAULT_DEAL_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    void getDealType() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get the dealType
        restDealTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, dealType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dealType.getId().intValue()))
            .andExpect(jsonPath("$.dealType").value(DEFAULT_DEAL_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDealType() throws Exception {
        // Get the dealType
        restDealTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDealType() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();

        // Update the dealType
        DealType updatedDealType = dealTypeRepository.findById(dealType.getId()).get();
        // Disconnect from session so that the updates on updatedDealType are not directly saved in db
        em.detach(updatedDealType);
        updatedDealType.dealType(UPDATED_DEAL_TYPE).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);

        restDealTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDealType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDealType))
            )
            .andExpect(status().isOk());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
        DealType testDealType = dealTypeList.get(dealTypeList.size() - 1);
        assertThat(testDealType.getDealType()).isEqualTo(UPDATED_DEAL_TYPE);
        assertThat(testDealType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDealType.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingDealType() throws Exception {
        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();
        dealType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dealType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDealType() throws Exception {
        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();
        dealType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dealType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDealType() throws Exception {
        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();
        dealType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dealType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealTypeWithPatch() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();

        // Update the dealType using partial update
        DealType partialUpdatedDealType = new DealType();
        partialUpdatedDealType.setId(dealType.getId());

        restDealTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealType))
            )
            .andExpect(status().isOk());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
        DealType testDealType = dealTypeList.get(dealTypeList.size() - 1);
        assertThat(testDealType.getDealType()).isEqualTo(DEFAULT_DEAL_TYPE);
        assertThat(testDealType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDealType.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateDealTypeWithPatch() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();

        // Update the dealType using partial update
        DealType partialUpdatedDealType = new DealType();
        partialUpdatedDealType.setId(dealType.getId());

        partialUpdatedDealType.dealType(UPDATED_DEAL_TYPE).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);

        restDealTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDealType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDealType))
            )
            .andExpect(status().isOk());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
        DealType testDealType = dealTypeList.get(dealTypeList.size() - 1);
        assertThat(testDealType.getDealType()).isEqualTo(UPDATED_DEAL_TYPE);
        assertThat(testDealType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDealType.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingDealType() throws Exception {
        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();
        dealType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dealType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDealType() throws Exception {
        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();
        dealType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dealType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDealType() throws Exception {
        int databaseSizeBeforeUpdate = dealTypeRepository.findAll().size();
        dealType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dealType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DealType in the database
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDealType() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        int databaseSizeBeforeDelete = dealTypeRepository.findAll().size();

        // Delete the dealType
        restDealTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, dealType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DealType> dealTypeList = dealTypeRepository.findAll();
        assertThat(dealTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
