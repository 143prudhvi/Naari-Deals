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

/**
 * Integration tests for the {@link DealTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealTypeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_BG_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BG_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

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
        DealType dealType = new DealType()
            .title(DEFAULT_TITLE)
            .subTitle(DEFAULT_SUB_TITLE)
            .icon(DEFAULT_ICON)
            .bgColor(DEFAULT_BG_COLOR)
            .country(DEFAULT_COUNTRY)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return dealType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DealType createUpdatedEntity(EntityManager em) {
        DealType dealType = new DealType()
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .icon(UPDATED_ICON)
            .bgColor(UPDATED_BG_COLOR)
            .country(UPDATED_COUNTRY)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
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
        assertThat(testDealType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDealType.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testDealType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testDealType.getBgColor()).isEqualTo(DEFAULT_BG_COLOR);
        assertThat(testDealType.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDealType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDealType.getStatus()).isEqualTo(DEFAULT_STATUS);
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
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].bgColor").value(hasItem(DEFAULT_BG_COLOR)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
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
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.bgColor").value(DEFAULT_BG_COLOR))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
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
        updatedDealType
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .icon(UPDATED_ICON)
            .bgColor(UPDATED_BG_COLOR)
            .country(UPDATED_COUNTRY)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);

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
        assertThat(testDealType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDealType.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testDealType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDealType.getBgColor()).isEqualTo(UPDATED_BG_COLOR);
        assertThat(testDealType.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDealType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDealType.getStatus()).isEqualTo(UPDATED_STATUS);
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

        partialUpdatedDealType.country(UPDATED_COUNTRY);

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
        assertThat(testDealType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDealType.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testDealType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testDealType.getBgColor()).isEqualTo(DEFAULT_BG_COLOR);
        assertThat(testDealType.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDealType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDealType.getStatus()).isEqualTo(DEFAULT_STATUS);
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

        partialUpdatedDealType
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .icon(UPDATED_ICON)
            .bgColor(UPDATED_BG_COLOR)
            .country(UPDATED_COUNTRY)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);

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
        assertThat(testDealType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDealType.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testDealType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDealType.getBgColor()).isEqualTo(UPDATED_BG_COLOR);
        assertThat(testDealType.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDealType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDealType.getStatus()).isEqualTo(UPDATED_STATUS);
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
