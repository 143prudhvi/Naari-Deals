package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.CategoryType;
import com.deals.naari.repository.CategoryTypeRepository;
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
 * Integration tests for the {@link CategoryTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoryTypeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/category-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryTypeMockMvc;

    private CategoryType categoryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryType createEntity(EntityManager em) {
        CategoryType categoryType = new CategoryType()
            .title(DEFAULT_TITLE)
            .subTitle(DEFAULT_SUB_TITLE)
            .icon(DEFAULT_ICON)
            .bgColor(DEFAULT_BG_COLOR)
            .country(DEFAULT_COUNTRY)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return categoryType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryType createUpdatedEntity(EntityManager em) {
        CategoryType categoryType = new CategoryType()
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .icon(UPDATED_ICON)
            .bgColor(UPDATED_BG_COLOR)
            .country(UPDATED_COUNTRY)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        return categoryType;
    }

    @BeforeEach
    public void initTest() {
        categoryType = createEntity(em);
    }

    @Test
    @Transactional
    void createCategoryType() throws Exception {
        int databaseSizeBeforeCreate = categoryTypeRepository.findAll().size();
        // Create the CategoryType
        restCategoryTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryType)))
            .andExpect(status().isCreated());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCategoryType.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testCategoryType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testCategoryType.getBgColor()).isEqualTo(DEFAULT_BG_COLOR);
        assertThat(testCategoryType.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCategoryType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategoryType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createCategoryTypeWithExistingId() throws Exception {
        // Create the CategoryType with an existing ID
        categoryType.setId(1L);

        int databaseSizeBeforeCreate = categoryTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryType)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoryTypes() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get all the categoryTypeList
        restCategoryTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryType.getId().intValue())))
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
    void getCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        // Get the categoryType
        restCategoryTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, categoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryType.getId().intValue()))
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
    void getNonExistingCategoryType() throws Exception {
        // Get the categoryType
        restCategoryTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Update the categoryType
        CategoryType updatedCategoryType = categoryTypeRepository.findById(categoryType.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryType are not directly saved in db
        em.detach(updatedCategoryType);
        updatedCategoryType
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .icon(UPDATED_ICON)
            .bgColor(UPDATED_BG_COLOR)
            .country(UPDATED_COUNTRY)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);

        restCategoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategoryType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategoryType))
            )
            .andExpect(status().isOk());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategoryType.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testCategoryType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testCategoryType.getBgColor()).isEqualTo(UPDATED_BG_COLOR);
        assertThat(testCategoryType.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCategoryType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategoryType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();
        categoryType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoryType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();
        categoryType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categoryType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();
        categoryType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categoryType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoryTypeWithPatch() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Update the categoryType using partial update
        CategoryType partialUpdatedCategoryType = new CategoryType();
        partialUpdatedCategoryType.setId(categoryType.getId());

        partialUpdatedCategoryType.title(UPDATED_TITLE).subTitle(UPDATED_SUB_TITLE);

        restCategoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoryType))
            )
            .andExpect(status().isOk());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategoryType.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testCategoryType.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testCategoryType.getBgColor()).isEqualTo(DEFAULT_BG_COLOR);
        assertThat(testCategoryType.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCategoryType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategoryType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCategoryTypeWithPatch() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();

        // Update the categoryType using partial update
        CategoryType partialUpdatedCategoryType = new CategoryType();
        partialUpdatedCategoryType.setId(categoryType.getId());

        partialUpdatedCategoryType
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .icon(UPDATED_ICON)
            .bgColor(UPDATED_BG_COLOR)
            .country(UPDATED_COUNTRY)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);

        restCategoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategoryType))
            )
            .andExpect(status().isOk());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
        CategoryType testCategoryType = categoryTypeList.get(categoryTypeList.size() - 1);
        assertThat(testCategoryType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategoryType.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testCategoryType.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testCategoryType.getBgColor()).isEqualTo(UPDATED_BG_COLOR);
        assertThat(testCategoryType.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCategoryType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategoryType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();
        categoryType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();
        categoryType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categoryType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = categoryTypeRepository.findAll().size();
        categoryType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categoryType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoryType in the database
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoryType() throws Exception {
        // Initialize the database
        categoryTypeRepository.saveAndFlush(categoryType);

        int databaseSizeBeforeDelete = categoryTypeRepository.findAll().size();

        // Delete the categoryType
        restCategoryTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoryType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryType> categoryTypeList = categoryTypeRepository.findAll();
        assertThat(categoryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
