package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.DealType;
import com.deals.naari.repository.DealTypeRepository;
import com.deals.naari.service.criteria.DealTypeCriteria;
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

    private static final Boolean DEFAULT_DISPLAY = false;
    private static final Boolean UPDATED_DISPLAY = true;

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
            .status(DEFAULT_STATUS)
            .display(DEFAULT_DISPLAY);
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
            .status(UPDATED_STATUS)
            .display(UPDATED_DISPLAY);
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
        assertThat(testDealType.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
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
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())));
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
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.booleanValue()));
    }

    @Test
    @Transactional
    void getDealTypesByIdFiltering() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        Long id = dealType.getId();

        defaultDealTypeShouldBeFound("id.equals=" + id);
        defaultDealTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDealTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDealTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDealTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDealTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDealTypesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where title equals to DEFAULT_TITLE
        defaultDealTypeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the dealTypeList where title equals to UPDATED_TITLE
        defaultDealTypeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDealTypeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the dealTypeList where title equals to UPDATED_TITLE
        defaultDealTypeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where title is not null
        defaultDealTypeShouldBeFound("title.specified=true");

        // Get all the dealTypeList where title is null
        defaultDealTypeShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesByTitleContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where title contains DEFAULT_TITLE
        defaultDealTypeShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the dealTypeList where title contains UPDATED_TITLE
        defaultDealTypeShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where title does not contain DEFAULT_TITLE
        defaultDealTypeShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the dealTypeList where title does not contain UPDATED_TITLE
        defaultDealTypeShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesBySubTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where subTitle equals to DEFAULT_SUB_TITLE
        defaultDealTypeShouldBeFound("subTitle.equals=" + DEFAULT_SUB_TITLE);

        // Get all the dealTypeList where subTitle equals to UPDATED_SUB_TITLE
        defaultDealTypeShouldNotBeFound("subTitle.equals=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesBySubTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where subTitle in DEFAULT_SUB_TITLE or UPDATED_SUB_TITLE
        defaultDealTypeShouldBeFound("subTitle.in=" + DEFAULT_SUB_TITLE + "," + UPDATED_SUB_TITLE);

        // Get all the dealTypeList where subTitle equals to UPDATED_SUB_TITLE
        defaultDealTypeShouldNotBeFound("subTitle.in=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesBySubTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where subTitle is not null
        defaultDealTypeShouldBeFound("subTitle.specified=true");

        // Get all the dealTypeList where subTitle is null
        defaultDealTypeShouldNotBeFound("subTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesBySubTitleContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where subTitle contains DEFAULT_SUB_TITLE
        defaultDealTypeShouldBeFound("subTitle.contains=" + DEFAULT_SUB_TITLE);

        // Get all the dealTypeList where subTitle contains UPDATED_SUB_TITLE
        defaultDealTypeShouldNotBeFound("subTitle.contains=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesBySubTitleNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where subTitle does not contain DEFAULT_SUB_TITLE
        defaultDealTypeShouldNotBeFound("subTitle.doesNotContain=" + DEFAULT_SUB_TITLE);

        // Get all the dealTypeList where subTitle does not contain UPDATED_SUB_TITLE
        defaultDealTypeShouldBeFound("subTitle.doesNotContain=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllDealTypesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where icon equals to DEFAULT_ICON
        defaultDealTypeShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the dealTypeList where icon equals to UPDATED_ICON
        defaultDealTypeShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllDealTypesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultDealTypeShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the dealTypeList where icon equals to UPDATED_ICON
        defaultDealTypeShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllDealTypesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where icon is not null
        defaultDealTypeShouldBeFound("icon.specified=true");

        // Get all the dealTypeList where icon is null
        defaultDealTypeShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesByIconContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where icon contains DEFAULT_ICON
        defaultDealTypeShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the dealTypeList where icon contains UPDATED_ICON
        defaultDealTypeShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllDealTypesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where icon does not contain DEFAULT_ICON
        defaultDealTypeShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the dealTypeList where icon does not contain UPDATED_ICON
        defaultDealTypeShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllDealTypesByBgColorIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where bgColor equals to DEFAULT_BG_COLOR
        defaultDealTypeShouldBeFound("bgColor.equals=" + DEFAULT_BG_COLOR);

        // Get all the dealTypeList where bgColor equals to UPDATED_BG_COLOR
        defaultDealTypeShouldNotBeFound("bgColor.equals=" + UPDATED_BG_COLOR);
    }

    @Test
    @Transactional
    void getAllDealTypesByBgColorIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where bgColor in DEFAULT_BG_COLOR or UPDATED_BG_COLOR
        defaultDealTypeShouldBeFound("bgColor.in=" + DEFAULT_BG_COLOR + "," + UPDATED_BG_COLOR);

        // Get all the dealTypeList where bgColor equals to UPDATED_BG_COLOR
        defaultDealTypeShouldNotBeFound("bgColor.in=" + UPDATED_BG_COLOR);
    }

    @Test
    @Transactional
    void getAllDealTypesByBgColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where bgColor is not null
        defaultDealTypeShouldBeFound("bgColor.specified=true");

        // Get all the dealTypeList where bgColor is null
        defaultDealTypeShouldNotBeFound("bgColor.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesByBgColorContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where bgColor contains DEFAULT_BG_COLOR
        defaultDealTypeShouldBeFound("bgColor.contains=" + DEFAULT_BG_COLOR);

        // Get all the dealTypeList where bgColor contains UPDATED_BG_COLOR
        defaultDealTypeShouldNotBeFound("bgColor.contains=" + UPDATED_BG_COLOR);
    }

    @Test
    @Transactional
    void getAllDealTypesByBgColorNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where bgColor does not contain DEFAULT_BG_COLOR
        defaultDealTypeShouldNotBeFound("bgColor.doesNotContain=" + DEFAULT_BG_COLOR);

        // Get all the dealTypeList where bgColor does not contain UPDATED_BG_COLOR
        defaultDealTypeShouldBeFound("bgColor.doesNotContain=" + UPDATED_BG_COLOR);
    }

    @Test
    @Transactional
    void getAllDealTypesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where country equals to DEFAULT_COUNTRY
        defaultDealTypeShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the dealTypeList where country equals to UPDATED_COUNTRY
        defaultDealTypeShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealTypesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultDealTypeShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the dealTypeList where country equals to UPDATED_COUNTRY
        defaultDealTypeShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealTypesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where country is not null
        defaultDealTypeShouldBeFound("country.specified=true");

        // Get all the dealTypeList where country is null
        defaultDealTypeShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesByCountryContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where country contains DEFAULT_COUNTRY
        defaultDealTypeShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the dealTypeList where country contains UPDATED_COUNTRY
        defaultDealTypeShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealTypesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where country does not contain DEFAULT_COUNTRY
        defaultDealTypeShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the dealTypeList where country does not contain UPDATED_COUNTRY
        defaultDealTypeShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllDealTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where code equals to DEFAULT_CODE
        defaultDealTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the dealTypeList where code equals to UPDATED_CODE
        defaultDealTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDealTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDealTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the dealTypeList where code equals to UPDATED_CODE
        defaultDealTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDealTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where code is not null
        defaultDealTypeShouldBeFound("code.specified=true");

        // Get all the dealTypeList where code is null
        defaultDealTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where code contains DEFAULT_CODE
        defaultDealTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the dealTypeList where code contains UPDATED_CODE
        defaultDealTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDealTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where code does not contain DEFAULT_CODE
        defaultDealTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the dealTypeList where code does not contain UPDATED_CODE
        defaultDealTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllDealTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where status equals to DEFAULT_STATUS
        defaultDealTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the dealTypeList where status equals to UPDATED_STATUS
        defaultDealTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDealTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the dealTypeList where status equals to UPDATED_STATUS
        defaultDealTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where status is not null
        defaultDealTypeShouldBeFound("status.specified=true");

        // Get all the dealTypeList where status is null
        defaultDealTypeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllDealTypesByStatusContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where status contains DEFAULT_STATUS
        defaultDealTypeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the dealTypeList where status contains UPDATED_STATUS
        defaultDealTypeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealTypesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where status does not contain DEFAULT_STATUS
        defaultDealTypeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the dealTypeList where status does not contain UPDATED_STATUS
        defaultDealTypeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDealTypesByDisplayIsEqualToSomething() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where display equals to DEFAULT_DISPLAY
        defaultDealTypeShouldBeFound("display.equals=" + DEFAULT_DISPLAY);

        // Get all the dealTypeList where display equals to UPDATED_DISPLAY
        defaultDealTypeShouldNotBeFound("display.equals=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void getAllDealTypesByDisplayIsInShouldWork() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where display in DEFAULT_DISPLAY or UPDATED_DISPLAY
        defaultDealTypeShouldBeFound("display.in=" + DEFAULT_DISPLAY + "," + UPDATED_DISPLAY);

        // Get all the dealTypeList where display equals to UPDATED_DISPLAY
        defaultDealTypeShouldNotBeFound("display.in=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void getAllDealTypesByDisplayIsNullOrNotNull() throws Exception {
        // Initialize the database
        dealTypeRepository.saveAndFlush(dealType);

        // Get all the dealTypeList where display is not null
        defaultDealTypeShouldBeFound("display.specified=true");

        // Get all the dealTypeList where display is null
        defaultDealTypeShouldNotBeFound("display.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDealTypeShouldBeFound(String filter) throws Exception {
        restDealTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dealType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].bgColor").value(hasItem(DEFAULT_BG_COLOR)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())));

        // Check, that the count call also returns 1
        restDealTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDealTypeShouldNotBeFound(String filter) throws Exception {
        restDealTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDealTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .status(UPDATED_STATUS)
            .display(UPDATED_DISPLAY);

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
        assertThat(testDealType.getDisplay()).isEqualTo(UPDATED_DISPLAY);
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

        partialUpdatedDealType.country(UPDATED_COUNTRY).display(UPDATED_DISPLAY);

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
        assertThat(testDealType.getDisplay()).isEqualTo(UPDATED_DISPLAY);
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
            .status(UPDATED_STATUS)
            .display(UPDATED_DISPLAY);

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
        assertThat(testDealType.getDisplay()).isEqualTo(UPDATED_DISPLAY);
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
