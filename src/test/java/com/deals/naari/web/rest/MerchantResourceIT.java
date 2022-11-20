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

/**
 * Integration tests for the {@link MerchantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MerchantResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

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
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .subTitle(DEFAULT_SUB_TITLE)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .type(DEFAULT_TYPE)
            .location(DEFAULT_LOCATION)
            .siteUrl(DEFAULT_SITE_URL)
            .status(DEFAULT_STATUS);
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
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .imageUrl(UPDATED_IMAGE_URL)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .siteUrl(UPDATED_SITE_URL)
            .status(UPDATED_STATUS);
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
        assertThat(testMerchant.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMerchant.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMerchant.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testMerchant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMerchant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMerchant.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testMerchant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
        assertThat(testMerchant.getStatus()).isEqualTo(DEFAULT_STATUS);
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
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
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
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.siteUrl").value(DEFAULT_SITE_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
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
    void getAllMerchantsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where code equals to DEFAULT_CODE
        defaultMerchantShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the merchantList where code equals to UPDATED_CODE
        defaultMerchantShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMerchantsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where code in DEFAULT_CODE or UPDATED_CODE
        defaultMerchantShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the merchantList where code equals to UPDATED_CODE
        defaultMerchantShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMerchantsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where code is not null
        defaultMerchantShouldBeFound("code.specified=true");

        // Get all the merchantList where code is null
        defaultMerchantShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByCodeContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where code contains DEFAULT_CODE
        defaultMerchantShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the merchantList where code contains UPDATED_CODE
        defaultMerchantShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMerchantsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where code does not contain DEFAULT_CODE
        defaultMerchantShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the merchantList where code does not contain UPDATED_CODE
        defaultMerchantShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where title equals to DEFAULT_TITLE
        defaultMerchantShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the merchantList where title equals to UPDATED_TITLE
        defaultMerchantShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultMerchantShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the merchantList where title equals to UPDATED_TITLE
        defaultMerchantShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where title is not null
        defaultMerchantShouldBeFound("title.specified=true");

        // Get all the merchantList where title is null
        defaultMerchantShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByTitleContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where title contains DEFAULT_TITLE
        defaultMerchantShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the merchantList where title contains UPDATED_TITLE
        defaultMerchantShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where title does not contain DEFAULT_TITLE
        defaultMerchantShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the merchantList where title does not contain UPDATED_TITLE
        defaultMerchantShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsBySubTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where subTitle equals to DEFAULT_SUB_TITLE
        defaultMerchantShouldBeFound("subTitle.equals=" + DEFAULT_SUB_TITLE);

        // Get all the merchantList where subTitle equals to UPDATED_SUB_TITLE
        defaultMerchantShouldNotBeFound("subTitle.equals=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsBySubTitleIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where subTitle in DEFAULT_SUB_TITLE or UPDATED_SUB_TITLE
        defaultMerchantShouldBeFound("subTitle.in=" + DEFAULT_SUB_TITLE + "," + UPDATED_SUB_TITLE);

        // Get all the merchantList where subTitle equals to UPDATED_SUB_TITLE
        defaultMerchantShouldNotBeFound("subTitle.in=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsBySubTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where subTitle is not null
        defaultMerchantShouldBeFound("subTitle.specified=true");

        // Get all the merchantList where subTitle is null
        defaultMerchantShouldNotBeFound("subTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsBySubTitleContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where subTitle contains DEFAULT_SUB_TITLE
        defaultMerchantShouldBeFound("subTitle.contains=" + DEFAULT_SUB_TITLE);

        // Get all the merchantList where subTitle contains UPDATED_SUB_TITLE
        defaultMerchantShouldNotBeFound("subTitle.contains=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsBySubTitleNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where subTitle does not contain DEFAULT_SUB_TITLE
        defaultMerchantShouldNotBeFound("subTitle.doesNotContain=" + DEFAULT_SUB_TITLE);

        // Get all the merchantList where subTitle does not contain UPDATED_SUB_TITLE
        defaultMerchantShouldBeFound("subTitle.doesNotContain=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllMerchantsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address equals to DEFAULT_ADDRESS
        defaultMerchantShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the merchantList where address equals to UPDATED_ADDRESS
        defaultMerchantShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMerchantsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultMerchantShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the merchantList where address equals to UPDATED_ADDRESS
        defaultMerchantShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMerchantsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address is not null
        defaultMerchantShouldBeFound("address.specified=true");

        // Get all the merchantList where address is null
        defaultMerchantShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByAddressContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address contains DEFAULT_ADDRESS
        defaultMerchantShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the merchantList where address contains UPDATED_ADDRESS
        defaultMerchantShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMerchantsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where address does not contain DEFAULT_ADDRESS
        defaultMerchantShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the merchantList where address does not contain UPDATED_ADDRESS
        defaultMerchantShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMerchantsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phone equals to DEFAULT_PHONE
        defaultMerchantShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the merchantList where phone equals to UPDATED_PHONE
        defaultMerchantShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllMerchantsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultMerchantShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the merchantList where phone equals to UPDATED_PHONE
        defaultMerchantShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllMerchantsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phone is not null
        defaultMerchantShouldBeFound("phone.specified=true");

        // Get all the merchantList where phone is null
        defaultMerchantShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phone contains DEFAULT_PHONE
        defaultMerchantShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the merchantList where phone contains UPDATED_PHONE
        defaultMerchantShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllMerchantsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where phone does not contain DEFAULT_PHONE
        defaultMerchantShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the merchantList where phone does not contain UPDATED_PHONE
        defaultMerchantShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
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
    void getAllMerchantsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultMerchantShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the merchantList where imageUrl equals to UPDATED_IMAGE_URL
        defaultMerchantShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultMerchantShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the merchantList where imageUrl equals to UPDATED_IMAGE_URL
        defaultMerchantShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where imageUrl is not null
        defaultMerchantShouldBeFound("imageUrl.specified=true");

        // Get all the merchantList where imageUrl is null
        defaultMerchantShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where imageUrl contains DEFAULT_IMAGE_URL
        defaultMerchantShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the merchantList where imageUrl contains UPDATED_IMAGE_URL
        defaultMerchantShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultMerchantShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the merchantList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultMerchantShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
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

    @Test
    @Transactional
    void getAllMerchantsBySiteUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where siteUrl equals to DEFAULT_SITE_URL
        defaultMerchantShouldBeFound("siteUrl.equals=" + DEFAULT_SITE_URL);

        // Get all the merchantList where siteUrl equals to UPDATED_SITE_URL
        defaultMerchantShouldNotBeFound("siteUrl.equals=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsBySiteUrlIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where siteUrl in DEFAULT_SITE_URL or UPDATED_SITE_URL
        defaultMerchantShouldBeFound("siteUrl.in=" + DEFAULT_SITE_URL + "," + UPDATED_SITE_URL);

        // Get all the merchantList where siteUrl equals to UPDATED_SITE_URL
        defaultMerchantShouldNotBeFound("siteUrl.in=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsBySiteUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where siteUrl is not null
        defaultMerchantShouldBeFound("siteUrl.specified=true");

        // Get all the merchantList where siteUrl is null
        defaultMerchantShouldNotBeFound("siteUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsBySiteUrlContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where siteUrl contains DEFAULT_SITE_URL
        defaultMerchantShouldBeFound("siteUrl.contains=" + DEFAULT_SITE_URL);

        // Get all the merchantList where siteUrl contains UPDATED_SITE_URL
        defaultMerchantShouldNotBeFound("siteUrl.contains=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsBySiteUrlNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where siteUrl does not contain DEFAULT_SITE_URL
        defaultMerchantShouldNotBeFound("siteUrl.doesNotContain=" + DEFAULT_SITE_URL);

        // Get all the merchantList where siteUrl does not contain UPDATED_SITE_URL
        defaultMerchantShouldBeFound("siteUrl.doesNotContain=" + UPDATED_SITE_URL);
    }

    @Test
    @Transactional
    void getAllMerchantsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where status equals to DEFAULT_STATUS
        defaultMerchantShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the merchantList where status equals to UPDATED_STATUS
        defaultMerchantShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMerchantsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMerchantShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the merchantList where status equals to UPDATED_STATUS
        defaultMerchantShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMerchantsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where status is not null
        defaultMerchantShouldBeFound("status.specified=true");

        // Get all the merchantList where status is null
        defaultMerchantShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllMerchantsByStatusContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where status contains DEFAULT_STATUS
        defaultMerchantShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the merchantList where status contains UPDATED_STATUS
        defaultMerchantShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMerchantsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList where status does not contain DEFAULT_STATUS
        defaultMerchantShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the merchantList where status does not contain UPDATED_STATUS
        defaultMerchantShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
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
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].siteUrl").value(hasItem(DEFAULT_SITE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

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
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .imageUrl(UPDATED_IMAGE_URL)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .siteUrl(UPDATED_SITE_URL)
            .status(UPDATED_STATUS);

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
        assertThat(testMerchant.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMerchant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMerchant.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testMerchant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMerchant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testMerchant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
        assertThat(testMerchant.getStatus()).isEqualTo(UPDATED_STATUS);
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

        partialUpdatedMerchant
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .type(UPDATED_TYPE);

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
        assertThat(testMerchant.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMerchant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMerchant.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testMerchant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMerchant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testMerchant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(DEFAULT_SITE_URL);
        assertThat(testMerchant.getStatus()).isEqualTo(DEFAULT_STATUS);
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
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .imageUrl(UPDATED_IMAGE_URL)
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .siteUrl(UPDATED_SITE_URL)
            .status(UPDATED_STATUS);

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
        assertThat(testMerchant.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMerchant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMerchant.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testMerchant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMerchant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testMerchant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMerchant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMerchant.getSiteUrl()).isEqualTo(UPDATED_SITE_URL);
        assertThat(testMerchant.getStatus()).isEqualTo(UPDATED_STATUS);
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
