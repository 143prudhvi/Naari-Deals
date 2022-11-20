package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Brand;
import com.deals.naari.repository.BrandRepository;
import com.deals.naari.service.criteria.BrandCriteria;
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
 * Integration tests for the {@link BrandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BrandResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/brands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrandMockMvc;

    private Brand brand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createEntity(EntityManager em) {
        Brand brand = new Brand()
            .title(DEFAULT_TITLE)
            .subTitle(DEFAULT_SUB_TITLE)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS)
            .country(DEFAULT_COUNTRY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .siteUrl(DEFAULT_SITE_URL);
        return brand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createUpdatedEntity(EntityManager em) {
        Brand brand = new Brand()
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .imageUrl(UPDATED_IMAGE_URL)
            .siteUrl(UPDATED_SITE_URL);
        return brand;
    }

    @BeforeEach
    public void initTest() {
        brand = createEntity(em);
    }

    @Test
    @Transactional
    void createBrand() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();
        // Create the Brand
        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate + 1);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBrand.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testBrand.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBrand.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBrand.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testBrand.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBrand.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
    }

    @Test
    @Transactional
    void createBrandWithExistingId() throws Exception {
        // Create the Brand with an existing ID
        brand.setId(1L);

        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBrands() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL)));
    }

    @Test
    @Transactional
    void getBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc
            .perform(get(ENTITY_API_URL_ID, brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.siteUrl").value(DEFAULT_SITE_URL));
    }

    @Test
    @Transactional
    void getBrandsByIdFiltering() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        Long id = brand.getId();

        defaultBrandShouldBeFound("id.equals=" + id);
        defaultBrandShouldNotBeFound("id.notEquals=" + id);

        defaultBrandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBrandShouldNotBeFound("id.greaterThan=" + id);

        defaultBrandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBrandShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBrandsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where title equals to DEFAULT_TITLE
        defaultBrandShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the brandList where title equals to UPDATED_TITLE
        defaultBrandShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBrandShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the brandList where title equals to UPDATED_TITLE
        defaultBrandShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where title is not null
        defaultBrandShouldBeFound("title.specified=true");

        // Get all the brandList where title is null
        defaultBrandShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByTitleContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where title contains DEFAULT_TITLE
        defaultBrandShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the brandList where title contains UPDATED_TITLE
        defaultBrandShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where title does not contain DEFAULT_TITLE
        defaultBrandShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the brandList where title does not contain UPDATED_TITLE
        defaultBrandShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsBySubTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where subTitle equals to DEFAULT_SUB_TITLE
        defaultBrandShouldBeFound("subTitle.equals=" + DEFAULT_SUB_TITLE);

        // Get all the brandList where subTitle equals to UPDATED_SUB_TITLE
        defaultBrandShouldNotBeFound("subTitle.equals=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsBySubTitleIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where subTitle in DEFAULT_SUB_TITLE or UPDATED_SUB_TITLE
        defaultBrandShouldBeFound("subTitle.in=" + DEFAULT_SUB_TITLE + "," + UPDATED_SUB_TITLE);

        // Get all the brandList where subTitle equals to UPDATED_SUB_TITLE
        defaultBrandShouldNotBeFound("subTitle.in=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsBySubTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where subTitle is not null
        defaultBrandShouldBeFound("subTitle.specified=true");

        // Get all the brandList where subTitle is null
        defaultBrandShouldNotBeFound("subTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsBySubTitleContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where subTitle contains DEFAULT_SUB_TITLE
        defaultBrandShouldBeFound("subTitle.contains=" + DEFAULT_SUB_TITLE);

        // Get all the brandList where subTitle contains UPDATED_SUB_TITLE
        defaultBrandShouldNotBeFound("subTitle.contains=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsBySubTitleNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where subTitle does not contain DEFAULT_SUB_TITLE
        defaultBrandShouldNotBeFound("subTitle.doesNotContain=" + DEFAULT_SUB_TITLE);

        // Get all the brandList where subTitle does not contain UPDATED_SUB_TITLE
        defaultBrandShouldBeFound("subTitle.doesNotContain=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllBrandsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where code equals to DEFAULT_CODE
        defaultBrandShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the brandList where code equals to UPDATED_CODE
        defaultBrandShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBrandsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where code in DEFAULT_CODE or UPDATED_CODE
        defaultBrandShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the brandList where code equals to UPDATED_CODE
        defaultBrandShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBrandsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where code is not null
        defaultBrandShouldBeFound("code.specified=true");

        // Get all the brandList where code is null
        defaultBrandShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByCodeContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where code contains DEFAULT_CODE
        defaultBrandShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the brandList where code contains UPDATED_CODE
        defaultBrandShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBrandsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where code does not contain DEFAULT_CODE
        defaultBrandShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the brandList where code does not contain UPDATED_CODE
        defaultBrandShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllBrandsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where status equals to DEFAULT_STATUS
        defaultBrandShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the brandList where status equals to UPDATED_STATUS
        defaultBrandShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBrandsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBrandShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the brandList where status equals to UPDATED_STATUS
        defaultBrandShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBrandsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where status is not null
        defaultBrandShouldBeFound("status.specified=true");

        // Get all the brandList where status is null
        defaultBrandShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByStatusContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where status contains DEFAULT_STATUS
        defaultBrandShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the brandList where status contains UPDATED_STATUS
        defaultBrandShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBrandsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where status does not contain DEFAULT_STATUS
        defaultBrandShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the brandList where status does not contain UPDATED_STATUS
        defaultBrandShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBrandsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where country equals to DEFAULT_COUNTRY
        defaultBrandShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the brandList where country equals to UPDATED_COUNTRY
        defaultBrandShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllBrandsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultBrandShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the brandList where country equals to UPDATED_COUNTRY
        defaultBrandShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllBrandsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where country is not null
        defaultBrandShouldBeFound("country.specified=true");

        // Get all the brandList where country is null
        defaultBrandShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByCountryContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where country contains DEFAULT_COUNTRY
        defaultBrandShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the brandList where country contains UPDATED_COUNTRY
        defaultBrandShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllBrandsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where country does not contain DEFAULT_COUNTRY
        defaultBrandShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the brandList where country does not contain UPDATED_COUNTRY
        defaultBrandShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllBrandsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultBrandShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the brandList where imageUrl equals to UPDATED_IMAGE_URL
        defaultBrandShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultBrandShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the brandList where imageUrl equals to UPDATED_IMAGE_URL
        defaultBrandShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where imageUrl is not null
        defaultBrandShouldBeFound("imageUrl.specified=true");

        // Get all the brandList where imageUrl is null
        defaultBrandShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where imageUrl contains DEFAULT_IMAGE_URL
        defaultBrandShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the brandList where imageUrl contains UPDATED_IMAGE_URL
        defaultBrandShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultBrandShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the brandList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultBrandShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsBySiteUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where siteUrl equals to DEFAULT_SITE_URL
        defaultBrandShouldBeFound("siteUrl.equals=" + DEFAULT_SITE_URL);

        // Get all the brandList where siteUrl equals to UPDATED_SITE_URL
        defaultBrandShouldNotBeFound("siteUrl.equals=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsBySiteUrlIsInShouldWork() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where siteUrl in DEFAULT_SITE_URL or UPDATED_SITE_URL
        defaultBrandShouldBeFound("siteUrl.in=" + DEFAULT_SITE_URL + "," + UPDATED_SITE_URL);

        // Get all the brandList where siteUrl equals to UPDATED_SITE_URL
        defaultBrandShouldNotBeFound("siteUrl.in=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsBySiteUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where siteUrl is not null
        defaultBrandShouldBeFound("siteUrl.specified=true");

        // Get all the brandList where siteUrl is null
        defaultBrandShouldNotBeFound("siteUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsBySiteUrlContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where siteUrl contains DEFAULT_SITE_URL
        defaultBrandShouldBeFound("siteUrl.contains=" + DEFAULT_SITE_URL);

        // Get all the brandList where siteUrl contains UPDATED_SITE_URL
        defaultBrandShouldNotBeFound("siteUrl.contains=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllBrandsBySiteUrlNotContainsSomething() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList where siteUrl does not contain DEFAULT_SITE_URL
        defaultBrandShouldNotBeFound("siteUrl.doesNotContain=" + DEFAULT_SITE_URL);

        // Get all the brandList where siteUrl does not contain UPDATED_SITE_URL
        defaultBrandShouldBeFound("siteUrl.doesNotContain=" + UPDATED_SITE_URL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBrandShouldBeFound(String filter) throws Exception {
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL)));

        // Check, that the count call also returns 1
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBrandShouldNotBeFound(String filter) throws Exception {
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand
        Brand updatedBrand = brandRepository.findById(brand.getId()).get();
        // Disconnect from session so that the updates on updatedBrand are not directly saved in db
        em.detach(updatedBrand);
        updatedBrand
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .imageUrl(UPDATED_IMAGE_URL)
            .siteUrl(UPDATED_SITE_URL);

        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBrand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBrand.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testBrand.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBrand.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBrand.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBrand.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBrand.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void putNonExistingBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();
        brand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(brand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();
        brand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(brand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();
        brand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBrandWithPatch() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand using partial update
        Brand partialUpdatedBrand = new Brand();
        partialUpdatedBrand.setId(brand.getId());

        partialUpdatedBrand.subTitle(UPDATED_SUB_TITLE).status(UPDATED_STATUS).country(UPDATED_COUNTRY);

        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBrand.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testBrand.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBrand.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBrand.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBrand.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testBrand.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
    }

    @Test
    @Transactional
    void fullUpdateBrandWithPatch() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand using partial update
        Brand partialUpdatedBrand = new Brand();
        partialUpdatedBrand.setId(brand.getId());

        partialUpdatedBrand
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .imageUrl(UPDATED_IMAGE_URL)
            .siteUrl(UPDATED_SITE_URL);

        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBrand.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testBrand.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBrand.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBrand.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testBrand.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testBrand.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();
        brand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, brand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(brand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();
        brand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(brand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();
        brand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        int databaseSizeBeforeDelete = brandRepository.findAll().size();

        // Delete the brand
        restBrandMockMvc
            .perform(delete(ENTITY_API_URL_ID, brand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
