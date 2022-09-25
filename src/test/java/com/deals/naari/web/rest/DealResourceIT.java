package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Deal;
import com.deals.naari.repository.DealRepository;
import com.deals.naari.service.criteria.DealCriteria;
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
 * Integration tests for the {@link DealResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DealResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_POSTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_POSTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTED_DATE = "AAAAAAAAAA";
    private static final String UPDATED_POSTED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealMockMvc;

    private Deal deal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deal createEntity(EntityManager em) {
        Deal deal = new Deal()
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL)
            .dealUrl(DEFAULT_DEAL_URL)
            .postedBy(DEFAULT_POSTED_BY)
            .postedDate(DEFAULT_POSTED_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .discountType(DEFAULT_DISCOUNT_TYPE)
            .active(DEFAULT_ACTIVE)
            .approved(DEFAULT_APPROVED)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .pinCode(DEFAULT_PIN_CODE)
            .merchant(DEFAULT_MERCHANT)
            .category(DEFAULT_CATEGORY);
        return deal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deal createUpdatedEntity(EntityManager em) {
        Deal deal = new Deal()
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .postedDate(UPDATED_POSTED_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .active(UPDATED_ACTIVE)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE)
            .merchant(UPDATED_MERCHANT)
            .category(UPDATED_CATEGORY);
        return deal;
    }

    @BeforeEach
    public void initTest() {
        deal = createEntity(em);
    }

    @Test
    @Transactional
    void createDeal() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();
        // Create the Deal
        restDealMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isCreated());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate + 1);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeal.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testDeal.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testDeal.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDeal.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDeal.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testDeal.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testDeal.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testDeal.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
        assertThat(testDeal.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testDeal.getApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testDeal.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDeal.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testDeal.getMerchant()).isEqualTo(DEFAULT_MERCHANT);
        assertThat(testDeal.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void createDealWithExistingId() throws Exception {
        // Create the Deal with an existing ID
        deal.setId(1L);

        int databaseSizeBeforeCreate = dealRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeals() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList
        restDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deal.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL.toString())))
            .andExpect(jsonPath("$.[*].postedBy").value(hasItem(DEFAULT_POSTED_BY)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].merchant").value(hasItem(DEFAULT_MERCHANT)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }

    @Test
    @Transactional
    void getDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get the deal
        restDealMockMvc
            .perform(get(ENTITY_API_URL_ID, deal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deal.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.dealUrl").value(DEFAULT_DEAL_URL.toString()))
            .andExpect(jsonPath("$.postedBy").value(DEFAULT_POSTED_BY))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.discountType").value(DEFAULT_DISCOUNT_TYPE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.merchant").value(DEFAULT_MERCHANT))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    void getDealsByIdFiltering() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        Long id = deal.getId();

        defaultDealShouldBeFound("id.equals=" + id);
        defaultDealShouldNotBeFound("id.notEquals=" + id);

        defaultDealShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealShouldNotBeFound("id.greaterThan=" + id);

        defaultDealShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where type equals to DEFAULT_TYPE
        defaultDealShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dealList where type equals to UPDATED_TYPE
        defaultDealShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDealShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dealList where type equals to UPDATED_TYPE
        defaultDealShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where type is not null
        defaultDealShouldBeFound("type.specified=true");

        // Get all the dealList where type is null
        defaultDealShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByTypeContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where type contains DEFAULT_TYPE
        defaultDealShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the dealList where type contains UPDATED_TYPE
        defaultDealShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where type does not contain DEFAULT_TYPE
        defaultDealShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the dealList where type does not contain UPDATED_TYPE
        defaultDealShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where title equals to DEFAULT_TITLE
        defaultDealShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the dealList where title equals to UPDATED_TITLE
        defaultDealShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDealShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the dealList where title equals to UPDATED_TITLE
        defaultDealShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where title is not null
        defaultDealShouldBeFound("title.specified=true");

        // Get all the dealList where title is null
        defaultDealShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByTitleContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where title contains DEFAULT_TITLE
        defaultDealShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the dealList where title contains UPDATED_TITLE
        defaultDealShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where title does not contain DEFAULT_TITLE
        defaultDealShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the dealList where title does not contain UPDATED_TITLE
        defaultDealShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealsByPostedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedBy equals to DEFAULT_POSTED_BY
        defaultDealShouldBeFound("postedBy.equals=" + DEFAULT_POSTED_BY);

        // Get all the dealList where postedBy equals to UPDATED_POSTED_BY
        defaultDealShouldNotBeFound("postedBy.equals=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDealsByPostedByIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedBy in DEFAULT_POSTED_BY or UPDATED_POSTED_BY
        defaultDealShouldBeFound("postedBy.in=" + DEFAULT_POSTED_BY + "," + UPDATED_POSTED_BY);

        // Get all the dealList where postedBy equals to UPDATED_POSTED_BY
        defaultDealShouldNotBeFound("postedBy.in=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDealsByPostedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedBy is not null
        defaultDealShouldBeFound("postedBy.specified=true");

        // Get all the dealList where postedBy is null
        defaultDealShouldNotBeFound("postedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByPostedByContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedBy contains DEFAULT_POSTED_BY
        defaultDealShouldBeFound("postedBy.contains=" + DEFAULT_POSTED_BY);

        // Get all the dealList where postedBy contains UPDATED_POSTED_BY
        defaultDealShouldNotBeFound("postedBy.contains=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDealsByPostedByNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedBy does not contain DEFAULT_POSTED_BY
        defaultDealShouldNotBeFound("postedBy.doesNotContain=" + DEFAULT_POSTED_BY);

        // Get all the dealList where postedBy does not contain UPDATED_POSTED_BY
        defaultDealShouldBeFound("postedBy.doesNotContain=" + UPDATED_POSTED_BY);
    }

    @Test
    @Transactional
    void getAllDealsByPostedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedDate equals to DEFAULT_POSTED_DATE
        defaultDealShouldBeFound("postedDate.equals=" + DEFAULT_POSTED_DATE);

        // Get all the dealList where postedDate equals to UPDATED_POSTED_DATE
        defaultDealShouldNotBeFound("postedDate.equals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByPostedDateIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedDate in DEFAULT_POSTED_DATE or UPDATED_POSTED_DATE
        defaultDealShouldBeFound("postedDate.in=" + DEFAULT_POSTED_DATE + "," + UPDATED_POSTED_DATE);

        // Get all the dealList where postedDate equals to UPDATED_POSTED_DATE
        defaultDealShouldNotBeFound("postedDate.in=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByPostedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedDate is not null
        defaultDealShouldBeFound("postedDate.specified=true");

        // Get all the dealList where postedDate is null
        defaultDealShouldNotBeFound("postedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByPostedDateContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedDate contains DEFAULT_POSTED_DATE
        defaultDealShouldBeFound("postedDate.contains=" + DEFAULT_POSTED_DATE);

        // Get all the dealList where postedDate contains UPDATED_POSTED_DATE
        defaultDealShouldNotBeFound("postedDate.contains=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByPostedDateNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where postedDate does not contain DEFAULT_POSTED_DATE
        defaultDealShouldNotBeFound("postedDate.doesNotContain=" + DEFAULT_POSTED_DATE);

        // Get all the dealList where postedDate does not contain UPDATED_POSTED_DATE
        defaultDealShouldBeFound("postedDate.doesNotContain=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where startDate equals to DEFAULT_START_DATE
        defaultDealShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the dealList where startDate equals to UPDATED_START_DATE
        defaultDealShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultDealShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the dealList where startDate equals to UPDATED_START_DATE
        defaultDealShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where startDate is not null
        defaultDealShouldBeFound("startDate.specified=true");

        // Get all the dealList where startDate is null
        defaultDealShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByStartDateContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where startDate contains DEFAULT_START_DATE
        defaultDealShouldBeFound("startDate.contains=" + DEFAULT_START_DATE);

        // Get all the dealList where startDate contains UPDATED_START_DATE
        defaultDealShouldNotBeFound("startDate.contains=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByStartDateNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where startDate does not contain DEFAULT_START_DATE
        defaultDealShouldNotBeFound("startDate.doesNotContain=" + DEFAULT_START_DATE);

        // Get all the dealList where startDate does not contain UPDATED_START_DATE
        defaultDealShouldBeFound("startDate.doesNotContain=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where endDate equals to DEFAULT_END_DATE
        defaultDealShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the dealList where endDate equals to UPDATED_END_DATE
        defaultDealShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultDealShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the dealList where endDate equals to UPDATED_END_DATE
        defaultDealShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where endDate is not null
        defaultDealShouldBeFound("endDate.specified=true");

        // Get all the dealList where endDate is null
        defaultDealShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByEndDateContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where endDate contains DEFAULT_END_DATE
        defaultDealShouldBeFound("endDate.contains=" + DEFAULT_END_DATE);

        // Get all the dealList where endDate contains UPDATED_END_DATE
        defaultDealShouldNotBeFound("endDate.contains=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByEndDateNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where endDate does not contain DEFAULT_END_DATE
        defaultDealShouldNotBeFound("endDate.doesNotContain=" + DEFAULT_END_DATE);

        // Get all the dealList where endDate does not contain UPDATED_END_DATE
        defaultDealShouldBeFound("endDate.doesNotContain=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllDealsByOriginalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where originalPrice equals to DEFAULT_ORIGINAL_PRICE
        defaultDealShouldBeFound("originalPrice.equals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultDealShouldNotBeFound("originalPrice.equals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByOriginalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where originalPrice in DEFAULT_ORIGINAL_PRICE or UPDATED_ORIGINAL_PRICE
        defaultDealShouldBeFound("originalPrice.in=" + DEFAULT_ORIGINAL_PRICE + "," + UPDATED_ORIGINAL_PRICE);

        // Get all the dealList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultDealShouldNotBeFound("originalPrice.in=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByOriginalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where originalPrice is not null
        defaultDealShouldBeFound("originalPrice.specified=true");

        // Get all the dealList where originalPrice is null
        defaultDealShouldNotBeFound("originalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByOriginalPriceContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where originalPrice contains DEFAULT_ORIGINAL_PRICE
        defaultDealShouldBeFound("originalPrice.contains=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dealList where originalPrice contains UPDATED_ORIGINAL_PRICE
        defaultDealShouldNotBeFound("originalPrice.contains=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByOriginalPriceNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where originalPrice does not contain DEFAULT_ORIGINAL_PRICE
        defaultDealShouldNotBeFound("originalPrice.doesNotContain=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the dealList where originalPrice does not contain UPDATED_ORIGINAL_PRICE
        defaultDealShouldBeFound("originalPrice.doesNotContain=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultDealShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the dealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultDealShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultDealShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the dealList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultDealShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where currentPrice is not null
        defaultDealShouldBeFound("currentPrice.specified=true");

        // Get all the dealList where currentPrice is null
        defaultDealShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByCurrentPriceContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where currentPrice contains DEFAULT_CURRENT_PRICE
        defaultDealShouldBeFound("currentPrice.contains=" + DEFAULT_CURRENT_PRICE);

        // Get all the dealList where currentPrice contains UPDATED_CURRENT_PRICE
        defaultDealShouldNotBeFound("currentPrice.contains=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByCurrentPriceNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where currentPrice does not contain DEFAULT_CURRENT_PRICE
        defaultDealShouldNotBeFound("currentPrice.doesNotContain=" + DEFAULT_CURRENT_PRICE);

        // Get all the dealList where currentPrice does not contain UPDATED_CURRENT_PRICE
        defaultDealShouldBeFound("currentPrice.doesNotContain=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discount equals to DEFAULT_DISCOUNT
        defaultDealShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the dealList where discount equals to UPDATED_DISCOUNT
        defaultDealShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultDealShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the dealList where discount equals to UPDATED_DISCOUNT
        defaultDealShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discount is not null
        defaultDealShouldBeFound("discount.specified=true");

        // Get all the dealList where discount is null
        defaultDealShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDiscountContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discount contains DEFAULT_DISCOUNT
        defaultDealShouldBeFound("discount.contains=" + DEFAULT_DISCOUNT);

        // Get all the dealList where discount contains UPDATED_DISCOUNT
        defaultDealShouldNotBeFound("discount.contains=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discount does not contain DEFAULT_DISCOUNT
        defaultDealShouldNotBeFound("discount.doesNotContain=" + DEFAULT_DISCOUNT);

        // Get all the dealList where discount does not contain UPDATED_DISCOUNT
        defaultDealShouldBeFound("discount.doesNotContain=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discountType equals to DEFAULT_DISCOUNT_TYPE
        defaultDealShouldBeFound("discountType.equals=" + DEFAULT_DISCOUNT_TYPE);

        // Get all the dealList where discountType equals to UPDATED_DISCOUNT_TYPE
        defaultDealShouldNotBeFound("discountType.equals=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discountType in DEFAULT_DISCOUNT_TYPE or UPDATED_DISCOUNT_TYPE
        defaultDealShouldBeFound("discountType.in=" + DEFAULT_DISCOUNT_TYPE + "," + UPDATED_DISCOUNT_TYPE);

        // Get all the dealList where discountType equals to UPDATED_DISCOUNT_TYPE
        defaultDealShouldNotBeFound("discountType.in=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discountType is not null
        defaultDealShouldBeFound("discountType.specified=true");

        // Get all the dealList where discountType is null
        defaultDealShouldNotBeFound("discountType.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByDiscountTypeContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discountType contains DEFAULT_DISCOUNT_TYPE
        defaultDealShouldBeFound("discountType.contains=" + DEFAULT_DISCOUNT_TYPE);

        // Get all the dealList where discountType contains UPDATED_DISCOUNT_TYPE
        defaultDealShouldNotBeFound("discountType.contains=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByDiscountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where discountType does not contain DEFAULT_DISCOUNT_TYPE
        defaultDealShouldNotBeFound("discountType.doesNotContain=" + DEFAULT_DISCOUNT_TYPE);

        // Get all the dealList where discountType does not contain UPDATED_DISCOUNT_TYPE
        defaultDealShouldBeFound("discountType.doesNotContain=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllDealsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where active equals to DEFAULT_ACTIVE
        defaultDealShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the dealList where active equals to UPDATED_ACTIVE
        defaultDealShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDealsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultDealShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the dealList where active equals to UPDATED_ACTIVE
        defaultDealShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDealsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where active is not null
        defaultDealShouldBeFound("active.specified=true");

        // Get all the dealList where active is null
        defaultDealShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByActiveContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where active contains DEFAULT_ACTIVE
        defaultDealShouldBeFound("active.contains=" + DEFAULT_ACTIVE);

        // Get all the dealList where active contains UPDATED_ACTIVE
        defaultDealShouldNotBeFound("active.contains=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDealsByActiveNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where active does not contain DEFAULT_ACTIVE
        defaultDealShouldNotBeFound("active.doesNotContain=" + DEFAULT_ACTIVE);

        // Get all the dealList where active does not contain UPDATED_ACTIVE
        defaultDealShouldBeFound("active.doesNotContain=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllDealsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where approved equals to DEFAULT_APPROVED
        defaultDealShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the dealList where approved equals to UPDATED_APPROVED
        defaultDealShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllDealsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultDealShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the dealList where approved equals to UPDATED_APPROVED
        defaultDealShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    void getAllDealsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where approved is not null
        defaultDealShouldBeFound("approved.specified=true");

        // Get all the dealList where approved is null
        defaultDealShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where country equals to DEFAULT_COUNTRY
        defaultDealShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the dealList where country equals to UPDATED_COUNTRY
        defaultDealShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultDealShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the dealList where country equals to UPDATED_COUNTRY
        defaultDealShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where country is not null
        defaultDealShouldBeFound("country.specified=true");

        // Get all the dealList where country is null
        defaultDealShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByCountryContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where country contains DEFAULT_COUNTRY
        defaultDealShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the dealList where country contains UPDATED_COUNTRY
        defaultDealShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where country does not contain DEFAULT_COUNTRY
        defaultDealShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the dealList where country does not contain UPDATED_COUNTRY
        defaultDealShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where city equals to DEFAULT_CITY
        defaultDealShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the dealList where city equals to UPDATED_CITY
        defaultDealShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDealsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where city in DEFAULT_CITY or UPDATED_CITY
        defaultDealShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the dealList where city equals to UPDATED_CITY
        defaultDealShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDealsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where city is not null
        defaultDealShouldBeFound("city.specified=true");

        // Get all the dealList where city is null
        defaultDealShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByCityContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where city contains DEFAULT_CITY
        defaultDealShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the dealList where city contains UPDATED_CITY
        defaultDealShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDealsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where city does not contain DEFAULT_CITY
        defaultDealShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the dealList where city does not contain UPDATED_CITY
        defaultDealShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllDealsByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where pinCode equals to DEFAULT_PIN_CODE
        defaultDealShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the dealList where pinCode equals to UPDATED_PIN_CODE
        defaultDealShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDealsByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultDealShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the dealList where pinCode equals to UPDATED_PIN_CODE
        defaultDealShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDealsByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where pinCode is not null
        defaultDealShouldBeFound("pinCode.specified=true");

        // Get all the dealList where pinCode is null
        defaultDealShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where pinCode contains DEFAULT_PIN_CODE
        defaultDealShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the dealList where pinCode contains UPDATED_PIN_CODE
        defaultDealShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDealsByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where pinCode does not contain DEFAULT_PIN_CODE
        defaultDealShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the dealList where pinCode does not contain UPDATED_PIN_CODE
        defaultDealShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllDealsByMerchantIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where merchant equals to DEFAULT_MERCHANT
        defaultDealShouldBeFound("merchant.equals=" + DEFAULT_MERCHANT);

        // Get all the dealList where merchant equals to UPDATED_MERCHANT
        defaultDealShouldNotBeFound("merchant.equals=" + UPDATED_MERCHANT);
    }

    @Test
    @Transactional
    void getAllDealsByMerchantIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where merchant in DEFAULT_MERCHANT or UPDATED_MERCHANT
        defaultDealShouldBeFound("merchant.in=" + DEFAULT_MERCHANT + "," + UPDATED_MERCHANT);

        // Get all the dealList where merchant equals to UPDATED_MERCHANT
        defaultDealShouldNotBeFound("merchant.in=" + UPDATED_MERCHANT);
    }

    @Test
    @Transactional
    void getAllDealsByMerchantIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where merchant is not null
        defaultDealShouldBeFound("merchant.specified=true");

        // Get all the dealList where merchant is null
        defaultDealShouldNotBeFound("merchant.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByMerchantContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where merchant contains DEFAULT_MERCHANT
        defaultDealShouldBeFound("merchant.contains=" + DEFAULT_MERCHANT);

        // Get all the dealList where merchant contains UPDATED_MERCHANT
        defaultDealShouldNotBeFound("merchant.contains=" + UPDATED_MERCHANT);
    }

    @Test
    @Transactional
    void getAllDealsByMerchantNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where merchant does not contain DEFAULT_MERCHANT
        defaultDealShouldNotBeFound("merchant.doesNotContain=" + DEFAULT_MERCHANT);

        // Get all the dealList where merchant does not contain UPDATED_MERCHANT
        defaultDealShouldBeFound("merchant.doesNotContain=" + UPDATED_MERCHANT);
    }

    @Test
    @Transactional
    void getAllDealsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where category equals to DEFAULT_CATEGORY
        defaultDealShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the dealList where category equals to UPDATED_CATEGORY
        defaultDealShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDealsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultDealShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the dealList where category equals to UPDATED_CATEGORY
        defaultDealShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDealsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where category is not null
        defaultDealShouldBeFound("category.specified=true");

        // Get all the dealList where category is null
        defaultDealShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllDealsByCategoryContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where category contains DEFAULT_CATEGORY
        defaultDealShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the dealList where category contains UPDATED_CATEGORY
        defaultDealShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDealsByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList where category does not contain DEFAULT_CATEGORY
        defaultDealShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the dealList where category does not contain UPDATED_CATEGORY
        defaultDealShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealShouldBeFound(String filter) throws Exception {
        restDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deal.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL.toString())))
            .andExpect(jsonPath("$.[*].postedBy").value(hasItem(DEFAULT_POSTED_BY)))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].merchant").value(hasItem(DEFAULT_MERCHANT)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));

        // Check, that the count call also returns 1
        restDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealShouldNotBeFound(String filter) throws Exception {
        restDealMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeal() throws Exception {
        // Get the deal
        restDealMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Update the deal
        Deal updatedDeal = dealRepository.findById(deal.getId()).get();
        // Disconnect from session so that the updates on updatedDeal are not directly saved in db
        em.detach(updatedDeal);
        updatedDeal
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .postedDate(UPDATED_POSTED_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .active(UPDATED_ACTIVE)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE)
            .merchant(UPDATED_MERCHANT)
            .category(UPDATED_CATEGORY);

        restDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeal))
            )
            .andExpect(status().isOk());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDeal.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
        assertThat(testDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testDeal.getMerchant()).isEqualTo(UPDATED_MERCHANT);
        assertThat(testDeal.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();
        deal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();
        deal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();
        deal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDealWithPatch() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Update the deal using partial update
        Deal partialUpdatedDeal = new Deal();
        partialUpdatedDeal.setId(deal.getId());

        partialUpdatedDeal
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .active(UPDATED_ACTIVE)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .merchant(UPDATED_MERCHANT);

        restDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeal))
            )
            .andExpect(status().isOk());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeal.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testDeal.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testDeal.getPostedBy()).isEqualTo(DEFAULT_POSTED_BY);
        assertThat(testDeal.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDeal.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
        assertThat(testDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDeal.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testDeal.getMerchant()).isEqualTo(UPDATED_MERCHANT);
        assertThat(testDeal.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateDealWithPatch() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Update the deal using partial update
        Deal partialUpdatedDeal = new Deal();
        partialUpdatedDeal.setId(deal.getId());

        partialUpdatedDeal
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL)
            .dealUrl(UPDATED_DEAL_URL)
            .postedBy(UPDATED_POSTED_BY)
            .postedDate(UPDATED_POSTED_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .active(UPDATED_ACTIVE)
            .approved(UPDATED_APPROVED)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .pinCode(UPDATED_PIN_CODE)
            .merchant(UPDATED_MERCHANT)
            .category(UPDATED_CATEGORY);

        restDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeal))
            )
            .andExpect(status().isOk());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeal.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testDeal.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testDeal.getPostedBy()).isEqualTo(UPDATED_POSTED_BY);
        assertThat(testDeal.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testDeal.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDeal.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDeal.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testDeal.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testDeal.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDeal.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
        assertThat(testDeal.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDeal.getApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testDeal.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDeal.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDeal.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testDeal.getMerchant()).isEqualTo(UPDATED_MERCHANT);
        assertThat(testDeal.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();
        deal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();
        deal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();
        deal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDealMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        int databaseSizeBeforeDelete = dealRepository.findAll().size();

        // Delete the deal
        restDealMockMvc
            .perform(delete(ENTITY_API_URL_ID, deal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
