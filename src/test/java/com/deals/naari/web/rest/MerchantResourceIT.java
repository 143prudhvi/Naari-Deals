package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Merchant;
import com.deals.naari.repository.MerchantRepository;
import com.deals.naari.service.criteria.MerchantCriteria;
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
 * Integration tests for the {@link MerchantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MerchantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_ICON = "AAAAAAAAAA";
    private static final String UPDATED_STORE_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/merchants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMerchantMockMvc;

    private Merchant merchant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Merchant createEntity(EntityManager em) {
        Merchant merchant = new Merchant()
            .name(DEFAULT_NAME)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .storeIcon(DEFAULT_STORE_ICON)
            .type(DEFAULT_TYPE)
            .location(DEFAULT_LOCATION)
            .siteUrl(DEFAULT_SITE_URL);
        return merchant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Merchant createUpdatedEntity(EntityManager em) {
        Merchant merchant = new Merchant()
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .storeIcon(UPDATED_STORE_ICON)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .siteUrl(UPDATED_SITE_URL);
        return merchant;
    }

    @BeforeEach
    public void initTest() {
        merchant = createEntity(em);
    }

    @Test
    @Transactional
    void createMerchant() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();
        // Create the Merchant
        restMerchantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isCreated());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMerchant.getStoreIcon()).isEqualTo(DEFAULT_STORE_ICON);
        assertThat(testMerchant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
    }

    @Test
    @Transactional
    void createMerchantWithExistingId() throws Exception {
        // Create the Merchant with an existing ID
        merchant.setId(1L);

        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMerchants() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList
        restMerchantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].storeIcon").value(hasItem(DEFAULT_STORE_ICON)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL.toString())));
    }

    @Test
    @Transactional
    void getMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get the merchant
        restMerchantMockMvc
            .perform(get(ENTITY_API_URL_ID, merchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(merchant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.storeIcon").value(DEFAULT_STORE_ICON))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.siteUrl").value(DEFAULT_SITE_URL.toString()));
    }

    @Test
    @Transactional
    void getMerchantsByIdFiltering() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        Long id = merchant.getId();

        defaultMerchantShouldBeFound("id.equals=" + id);
        defaultMerchantShouldNotBeFound("id.notEquals=" + id);

        defaultMerchantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMerchantShouldNotBeFound("id.greaterThan=" + id);

        defaultMerchantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMerchantShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMerchantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name equals to DEFAULT_NAME
        defaultMerchantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the merchantList where name equals to UPDATED_NAME
        defaultMerchantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMerchantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMerchantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the merchantList where name equals to UPDATED_NAME
        defaultMerchantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMerchantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name is not null
        defaultMerchantShouldBeFound("name.specified=true");

        // Get all the merchantList where name is null
        defaultMerchantShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByNameContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name contains DEFAULT_NAME
        defaultMerchantShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the merchantList where name contains UPDATED_NAME
        defaultMerchantShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMerchantsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where name does not contain DEFAULT_NAME
        defaultMerchantShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the merchantList where name does not contain UPDATED_NAME
        defaultMerchantShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMerchantsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country equals to DEFAULT_COUNTRY
        defaultMerchantShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the merchantList where country equals to UPDATED_COUNTRY
        defaultMerchantShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultMerchantShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the merchantList where country equals to UPDATED_COUNTRY
        defaultMerchantShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country is not null
        defaultMerchantShouldBeFound("country.specified=true");

        // Get all the merchantList where country is null
        defaultMerchantShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByCountryContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country contains DEFAULT_COUNTRY
        defaultMerchantShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the merchantList where country contains UPDATED_COUNTRY
        defaultMerchantShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where country does not contain DEFAULT_COUNTRY
        defaultMerchantShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the merchantList where country does not contain UPDATED_COUNTRY
        defaultMerchantShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city equals to DEFAULT_CITY
        defaultMerchantShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the merchantList where city equals to UPDATED_CITY
        defaultMerchantShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city in DEFAULT_CITY or UPDATED_CITY
        defaultMerchantShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the merchantList where city equals to UPDATED_CITY
        defaultMerchantShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city is not null
        defaultMerchantShouldBeFound("city.specified=true");

        // Get all the merchantList where city is null
        defaultMerchantShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByCityContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city contains DEFAULT_CITY
        defaultMerchantShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the merchantList where city contains UPDATED_CITY
        defaultMerchantShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllMerchantsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where city does not contain DEFAULT_CITY
        defaultMerchantShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the merchantList where city does not contain UPDATED_CITY
        defaultMerchantShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllMerchantsByStoreIconIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where storeIcon equals to DEFAULT_STORE_ICON
        defaultMerchantShouldBeFound("storeIcon.equals=" + DEFAULT_STORE_ICON);

        // Get all the merchantList where storeIcon equals to UPDATED_STORE_ICON
        defaultMerchantShouldNotBeFound("storeIcon.equals=" + UPDATED_STORE_ICON);
    }

    @Test
    @Transactional
    void getAllMerchantsByStoreIconIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where storeIcon in DEFAULT_STORE_ICON or UPDATED_STORE_ICON
        defaultMerchantShouldBeFound("storeIcon.in=" + DEFAULT_STORE_ICON + "," + UPDATED_STORE_ICON);

        // Get all the merchantList where storeIcon equals to UPDATED_STORE_ICON
        defaultMerchantShouldNotBeFound("storeIcon.in=" + UPDATED_STORE_ICON);
    }

    @Test
    @Transactional
    void getAllMerchantsByStoreIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where storeIcon is not null
        defaultMerchantShouldBeFound("storeIcon.specified=true");

        // Get all the merchantList where storeIcon is null
        defaultMerchantShouldNotBeFound("storeIcon.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByStoreIconContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where storeIcon contains DEFAULT_STORE_ICON
        defaultMerchantShouldBeFound("storeIcon.contains=" + DEFAULT_STORE_ICON);

        // Get all the merchantList where storeIcon contains UPDATED_STORE_ICON
        defaultMerchantShouldNotBeFound("storeIcon.contains=" + UPDATED_STORE_ICON);
    }

    @Test
    @Transactional
    void getAllMerchantsByStoreIconNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where storeIcon does not contain DEFAULT_STORE_ICON
        defaultMerchantShouldNotBeFound("storeIcon.doesNotContain=" + DEFAULT_STORE_ICON);

        // Get all the merchantList where storeIcon does not contain UPDATED_STORE_ICON
        defaultMerchantShouldBeFound("storeIcon.doesNotContain=" + UPDATED_STORE_ICON);
    }

    @Test
    @Transactional
    void getAllMerchantsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where type equals to DEFAULT_TYPE
        defaultMerchantShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the merchantList where type equals to UPDATED_TYPE
        defaultMerchantShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMerchantShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the merchantList where type equals to UPDATED_TYPE
        defaultMerchantShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where type is not null
        defaultMerchantShouldBeFound("type.specified=true");

        // Get all the merchantList where type is null
        defaultMerchantShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByTypeContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where type contains DEFAULT_TYPE
        defaultMerchantShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the merchantList where type contains UPDATED_TYPE
        defaultMerchantShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where type does not contain DEFAULT_TYPE
        defaultMerchantShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the merchantList where type does not contain UPDATED_TYPE
        defaultMerchantShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMerchantsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where location equals to DEFAULT_LOCATION
        defaultMerchantShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the merchantList where location equals to UPDATED_LOCATION
        defaultMerchantShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllMerchantsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultMerchantShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the merchantList where location equals to UPDATED_LOCATION
        defaultMerchantShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllMerchantsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where location is not null
        defaultMerchantShouldBeFound("location.specified=true");

        // Get all the merchantList where location is null
        defaultMerchantShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByLocationContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where location contains DEFAULT_LOCATION
        defaultMerchantShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the merchantList where location contains UPDATED_LOCATION
        defaultMerchantShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllMerchantsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where location does not contain DEFAULT_LOCATION
        defaultMerchantShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the merchantList where location does not contain UPDATED_LOCATION
        defaultMerchantShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMerchantShouldBeFound(String filter) throws Exception {
        restMerchantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].storeIcon").value(hasItem(DEFAULT_STORE_ICON)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL.toString())));

        // Check, that the count call also returns 1
        restMerchantMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMerchantShouldNotBeFound(String filter) throws Exception {
        restMerchantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMerchantMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMerchant() throws Exception {
        // Get the merchant
        restMerchantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant
        Merchant updatedMerchant = merchantRepository.findById(merchant.getId()).get();
        // Disconnect from session so that the updates on updatedMerchant are not directly saved in db
        em.detach(updatedMerchant);
        updatedMerchant
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .storeIcon(UPDATED_STORE_ICON)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .siteUrl(UPDATED_SITE_URL);

        restMerchantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMerchant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMerchant))
            )
            .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getStoreIcon()).isEqualTo(UPDATED_STORE_ICON);
        assertThat(testMerchant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void putNonExistingMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();
        merchant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, merchant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();
        merchant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();
        merchant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMerchantWithPatch() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant using partial update
        Merchant partialUpdatedMerchant = new Merchant();
        partialUpdatedMerchant.setId(merchant.getId());

        partialUpdatedMerchant.country(UPDATED_COUNTRY).city(UPDATED_CITY).storeIcon(UPDATED_STORE_ICON).siteUrl(UPDATED_SITE_URL);

        restMerchantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMerchant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchant))
            )
            .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getStoreIcon()).isEqualTo(UPDATED_STORE_ICON);
        assertThat(testMerchant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void fullUpdateMerchantWithPatch() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant using partial update
        Merchant partialUpdatedMerchant = new Merchant();
        partialUpdatedMerchant.setId(merchant.getId());

        partialUpdatedMerchant
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .storeIcon(UPDATED_STORE_ICON)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .siteUrl(UPDATED_SITE_URL);

        restMerchantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMerchant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchant))
            )
            .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getStoreIcon()).isEqualTo(UPDATED_STORE_ICON);
        assertThat(testMerchant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();
        merchant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, merchant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(merchant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();
        merchant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(merchant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();
        merchant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        int databaseSizeBeforeDelete = merchantRepository.findAll().size();

        // Delete the merchant
        restMerchantMockMvc
            .perform(delete(ENTITY_API_URL_ID, merchant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
