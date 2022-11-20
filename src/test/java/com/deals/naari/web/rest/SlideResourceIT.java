package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Slide;
import com.deals.naari.repository.SlideRepository;
import com.deals.naari.service.criteria.SlideCriteria;
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
 * Integration tests for the {@link SlideResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SlideResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MERCHANT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_MERCHANT_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/slides";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSlideMockMvc;

    private Slide slide;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slide createEntity(EntityManager em) {
        Slide slide = new Slide()
            .title(DEFAULT_TITLE)
            .subTitle(DEFAULT_SUB_TITLE)
            .status(DEFAULT_STATUS)
            .country(DEFAULT_COUNTRY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .merchantIcon(DEFAULT_MERCHANT_ICON)
            .dealUrl(DEFAULT_DEAL_URL)
            .tags(DEFAULT_TAGS);
        return slide;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slide createUpdatedEntity(EntityManager em) {
        Slide slide = new Slide()
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .imageUrl(UPDATED_IMAGE_URL)
            .merchantIcon(UPDATED_MERCHANT_ICON)
            .dealUrl(UPDATED_DEAL_URL)
            .tags(UPDATED_TAGS);
        return slide;
    }

    @BeforeEach
    public void initTest() {
        slide = createEntity(em);
    }

    @Test
    @Transactional
    void createSlide() throws Exception {
        int databaseSizeBeforeCreate = slideRepository.findAll().size();
        // Create the Slide
        restSlideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(slide)))
            .andExpect(status().isCreated());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeCreate + 1);
        Slide testSlide = slideList.get(slideList.size() - 1);
        assertThat(testSlide.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSlide.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testSlide.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSlide.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSlide.getMerchantIcon()).isEqualTo(DEFAULT_MERCHANT_ICON);
        assertThat(testSlide.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testSlide.getTags()).isEqualTo(DEFAULT_TAGS);
    }

    @Test
    @Transactional
    void createSlideWithExistingId() throws Exception {
        // Create the Slide with an existing ID
        slide.setId(1L);

        int databaseSizeBeforeCreate = slideRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(slide)))
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSlides() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList
        restSlideMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slide.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].merchantIcon").value(hasItem(DEFAULT_MERCHANT_ICON)))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));
    }

    @Test
    @Transactional
    void getSlide() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get the slide
        restSlideMockMvc
            .perform(get(ENTITY_API_URL_ID, slide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(slide.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.merchantIcon").value(DEFAULT_MERCHANT_ICON))
            .andExpect(jsonPath("$.dealUrl").value(DEFAULT_DEAL_URL.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS));
    }

    @Test
    @Transactional
    void getSlidesByIdFiltering() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        Long id = slide.getId();

        defaultSlideShouldBeFound("id.equals=" + id);
        defaultSlideShouldNotBeFound("id.notEquals=" + id);

        defaultSlideShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSlideShouldNotBeFound("id.greaterThan=" + id);

        defaultSlideShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSlideShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSlidesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where title equals to DEFAULT_TITLE
        defaultSlideShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the slideList where title equals to UPDATED_TITLE
        defaultSlideShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSlideShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the slideList where title equals to UPDATED_TITLE
        defaultSlideShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where title is not null
        defaultSlideShouldBeFound("title.specified=true");

        // Get all the slideList where title is null
        defaultSlideShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByTitleContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where title contains DEFAULT_TITLE
        defaultSlideShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the slideList where title contains UPDATED_TITLE
        defaultSlideShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where title does not contain DEFAULT_TITLE
        defaultSlideShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the slideList where title does not contain UPDATED_TITLE
        defaultSlideShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesBySubTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where subTitle equals to DEFAULT_SUB_TITLE
        defaultSlideShouldBeFound("subTitle.equals=" + DEFAULT_SUB_TITLE);

        // Get all the slideList where subTitle equals to UPDATED_SUB_TITLE
        defaultSlideShouldNotBeFound("subTitle.equals=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesBySubTitleIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where subTitle in DEFAULT_SUB_TITLE or UPDATED_SUB_TITLE
        defaultSlideShouldBeFound("subTitle.in=" + DEFAULT_SUB_TITLE + "," + UPDATED_SUB_TITLE);

        // Get all the slideList where subTitle equals to UPDATED_SUB_TITLE
        defaultSlideShouldNotBeFound("subTitle.in=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesBySubTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where subTitle is not null
        defaultSlideShouldBeFound("subTitle.specified=true");

        // Get all the slideList where subTitle is null
        defaultSlideShouldNotBeFound("subTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesBySubTitleContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where subTitle contains DEFAULT_SUB_TITLE
        defaultSlideShouldBeFound("subTitle.contains=" + DEFAULT_SUB_TITLE);

        // Get all the slideList where subTitle contains UPDATED_SUB_TITLE
        defaultSlideShouldNotBeFound("subTitle.contains=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesBySubTitleNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where subTitle does not contain DEFAULT_SUB_TITLE
        defaultSlideShouldNotBeFound("subTitle.doesNotContain=" + DEFAULT_SUB_TITLE);

        // Get all the slideList where subTitle does not contain UPDATED_SUB_TITLE
        defaultSlideShouldBeFound("subTitle.doesNotContain=" + UPDATED_SUB_TITLE);
    }

    @Test
    @Transactional
    void getAllSlidesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where status equals to DEFAULT_STATUS
        defaultSlideShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the slideList where status equals to UPDATED_STATUS
        defaultSlideShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSlidesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSlideShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the slideList where status equals to UPDATED_STATUS
        defaultSlideShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSlidesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where status is not null
        defaultSlideShouldBeFound("status.specified=true");

        // Get all the slideList where status is null
        defaultSlideShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByStatusContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where status contains DEFAULT_STATUS
        defaultSlideShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the slideList where status contains UPDATED_STATUS
        defaultSlideShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSlidesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where status does not contain DEFAULT_STATUS
        defaultSlideShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the slideList where status does not contain UPDATED_STATUS
        defaultSlideShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSlidesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where country equals to DEFAULT_COUNTRY
        defaultSlideShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the slideList where country equals to UPDATED_COUNTRY
        defaultSlideShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSlidesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultSlideShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the slideList where country equals to UPDATED_COUNTRY
        defaultSlideShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSlidesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where country is not null
        defaultSlideShouldBeFound("country.specified=true");

        // Get all the slideList where country is null
        defaultSlideShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByCountryContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where country contains DEFAULT_COUNTRY
        defaultSlideShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the slideList where country contains UPDATED_COUNTRY
        defaultSlideShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSlidesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where country does not contain DEFAULT_COUNTRY
        defaultSlideShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the slideList where country does not contain UPDATED_COUNTRY
        defaultSlideShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllSlidesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where startDate equals to DEFAULT_START_DATE
        defaultSlideShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the slideList where startDate equals to UPDATED_START_DATE
        defaultSlideShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultSlideShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the slideList where startDate equals to UPDATED_START_DATE
        defaultSlideShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where startDate is not null
        defaultSlideShouldBeFound("startDate.specified=true");

        // Get all the slideList where startDate is null
        defaultSlideShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByStartDateContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where startDate contains DEFAULT_START_DATE
        defaultSlideShouldBeFound("startDate.contains=" + DEFAULT_START_DATE);

        // Get all the slideList where startDate contains UPDATED_START_DATE
        defaultSlideShouldNotBeFound("startDate.contains=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByStartDateNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where startDate does not contain DEFAULT_START_DATE
        defaultSlideShouldNotBeFound("startDate.doesNotContain=" + DEFAULT_START_DATE);

        // Get all the slideList where startDate does not contain UPDATED_START_DATE
        defaultSlideShouldBeFound("startDate.doesNotContain=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where endDate equals to DEFAULT_END_DATE
        defaultSlideShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the slideList where endDate equals to UPDATED_END_DATE
        defaultSlideShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultSlideShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the slideList where endDate equals to UPDATED_END_DATE
        defaultSlideShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where endDate is not null
        defaultSlideShouldBeFound("endDate.specified=true");

        // Get all the slideList where endDate is null
        defaultSlideShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByEndDateContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where endDate contains DEFAULT_END_DATE
        defaultSlideShouldBeFound("endDate.contains=" + DEFAULT_END_DATE);

        // Get all the slideList where endDate contains UPDATED_END_DATE
        defaultSlideShouldNotBeFound("endDate.contains=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByEndDateNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where endDate does not contain DEFAULT_END_DATE
        defaultSlideShouldNotBeFound("endDate.doesNotContain=" + DEFAULT_END_DATE);

        // Get all the slideList where endDate does not contain UPDATED_END_DATE
        defaultSlideShouldBeFound("endDate.doesNotContain=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllSlidesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultSlideShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the slideList where imageUrl equals to UPDATED_IMAGE_URL
        defaultSlideShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllSlidesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultSlideShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the slideList where imageUrl equals to UPDATED_IMAGE_URL
        defaultSlideShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllSlidesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where imageUrl is not null
        defaultSlideShouldBeFound("imageUrl.specified=true");

        // Get all the slideList where imageUrl is null
        defaultSlideShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where imageUrl contains DEFAULT_IMAGE_URL
        defaultSlideShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the slideList where imageUrl contains UPDATED_IMAGE_URL
        defaultSlideShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllSlidesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultSlideShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the slideList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultSlideShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllSlidesByMerchantIconIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where merchantIcon equals to DEFAULT_MERCHANT_ICON
        defaultSlideShouldBeFound("merchantIcon.equals=" + DEFAULT_MERCHANT_ICON);

        // Get all the slideList where merchantIcon equals to UPDATED_MERCHANT_ICON
        defaultSlideShouldNotBeFound("merchantIcon.equals=" + UPDATED_MERCHANT_ICON);
    }

    @Test
    @Transactional
    void getAllSlidesByMerchantIconIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where merchantIcon in DEFAULT_MERCHANT_ICON or UPDATED_MERCHANT_ICON
        defaultSlideShouldBeFound("merchantIcon.in=" + DEFAULT_MERCHANT_ICON + "," + UPDATED_MERCHANT_ICON);

        // Get all the slideList where merchantIcon equals to UPDATED_MERCHANT_ICON
        defaultSlideShouldNotBeFound("merchantIcon.in=" + UPDATED_MERCHANT_ICON);
    }

    @Test
    @Transactional
    void getAllSlidesByMerchantIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where merchantIcon is not null
        defaultSlideShouldBeFound("merchantIcon.specified=true");

        // Get all the slideList where merchantIcon is null
        defaultSlideShouldNotBeFound("merchantIcon.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByMerchantIconContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where merchantIcon contains DEFAULT_MERCHANT_ICON
        defaultSlideShouldBeFound("merchantIcon.contains=" + DEFAULT_MERCHANT_ICON);

        // Get all the slideList where merchantIcon contains UPDATED_MERCHANT_ICON
        defaultSlideShouldNotBeFound("merchantIcon.contains=" + UPDATED_MERCHANT_ICON);
    }

    @Test
    @Transactional
    void getAllSlidesByMerchantIconNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where merchantIcon does not contain DEFAULT_MERCHANT_ICON
        defaultSlideShouldNotBeFound("merchantIcon.doesNotContain=" + DEFAULT_MERCHANT_ICON);

        // Get all the slideList where merchantIcon does not contain UPDATED_MERCHANT_ICON
        defaultSlideShouldBeFound("merchantIcon.doesNotContain=" + UPDATED_MERCHANT_ICON);
    }

    @Test
    @Transactional
    void getAllSlidesByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where tags equals to DEFAULT_TAGS
        defaultSlideShouldBeFound("tags.equals=" + DEFAULT_TAGS);

        // Get all the slideList where tags equals to UPDATED_TAGS
        defaultSlideShouldNotBeFound("tags.equals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    void getAllSlidesByTagsIsInShouldWork() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where tags in DEFAULT_TAGS or UPDATED_TAGS
        defaultSlideShouldBeFound("tags.in=" + DEFAULT_TAGS + "," + UPDATED_TAGS);

        // Get all the slideList where tags equals to UPDATED_TAGS
        defaultSlideShouldNotBeFound("tags.in=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    void getAllSlidesByTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where tags is not null
        defaultSlideShouldBeFound("tags.specified=true");

        // Get all the slideList where tags is null
        defaultSlideShouldNotBeFound("tags.specified=false");
    }

    @Test
    @Transactional
    void getAllSlidesByTagsContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where tags contains DEFAULT_TAGS
        defaultSlideShouldBeFound("tags.contains=" + DEFAULT_TAGS);

        // Get all the slideList where tags contains UPDATED_TAGS
        defaultSlideShouldNotBeFound("tags.contains=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    void getAllSlidesByTagsNotContainsSomething() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList where tags does not contain DEFAULT_TAGS
        defaultSlideShouldNotBeFound("tags.doesNotContain=" + DEFAULT_TAGS);

        // Get all the slideList where tags does not contain UPDATED_TAGS
        defaultSlideShouldBeFound("tags.doesNotContain=" + UPDATED_TAGS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSlideShouldBeFound(String filter) throws Exception {
        restSlideMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slide.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].merchantIcon").value(hasItem(DEFAULT_MERCHANT_ICON)))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)));

        // Check, that the count call also returns 1
        restSlideMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSlideShouldNotBeFound(String filter) throws Exception {
        restSlideMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSlideMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSlide() throws Exception {
        // Get the slide
        restSlideMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSlide() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        int databaseSizeBeforeUpdate = slideRepository.findAll().size();

        // Update the slide
        Slide updatedSlide = slideRepository.findById(slide.getId()).get();
        // Disconnect from session so that the updates on updatedSlide are not directly saved in db
        em.detach(updatedSlide);
        updatedSlide
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .imageUrl(UPDATED_IMAGE_URL)
            .merchantIcon(UPDATED_MERCHANT_ICON)
            .dealUrl(UPDATED_DEAL_URL)
            .tags(UPDATED_TAGS);

        restSlideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSlide.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSlide))
            )
            .andExpect(status().isOk());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
        Slide testSlide = slideList.get(slideList.size() - 1);
        assertThat(testSlide.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSlide.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testSlide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSlide.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSlide.getMerchantIcon()).isEqualTo(UPDATED_MERCHANT_ICON);
        assertThat(testSlide.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testSlide.getTags()).isEqualTo(UPDATED_TAGS);
    }

    @Test
    @Transactional
    void putNonExistingSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();
        slide.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, slide.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(slide))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();
        slide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(slide))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();
        slide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlideMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(slide)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSlideWithPatch() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        int databaseSizeBeforeUpdate = slideRepository.findAll().size();

        // Update the slide using partial update
        Slide partialUpdatedSlide = new Slide();
        partialUpdatedSlide.setId(slide.getId());

        partialUpdatedSlide.title(UPDATED_TITLE).status(UPDATED_STATUS).startDate(UPDATED_START_DATE).imageUrl(UPDATED_IMAGE_URL);

        restSlideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSlide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSlide))
            )
            .andExpect(status().isOk());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
        Slide testSlide = slideList.get(slideList.size() - 1);
        assertThat(testSlide.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSlide.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testSlide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSlide.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSlide.getMerchantIcon()).isEqualTo(DEFAULT_MERCHANT_ICON);
        assertThat(testSlide.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
        assertThat(testSlide.getTags()).isEqualTo(DEFAULT_TAGS);
    }

    @Test
    @Transactional
    void fullUpdateSlideWithPatch() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        int databaseSizeBeforeUpdate = slideRepository.findAll().size();

        // Update the slide using partial update
        Slide partialUpdatedSlide = new Slide();
        partialUpdatedSlide.setId(slide.getId());

        partialUpdatedSlide
            .title(UPDATED_TITLE)
            .subTitle(UPDATED_SUB_TITLE)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .imageUrl(UPDATED_IMAGE_URL)
            .merchantIcon(UPDATED_MERCHANT_ICON)
            .dealUrl(UPDATED_DEAL_URL)
            .tags(UPDATED_TAGS);

        restSlideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSlide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSlide))
            )
            .andExpect(status().isOk());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
        Slide testSlide = slideList.get(slideList.size() - 1);
        assertThat(testSlide.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSlide.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testSlide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSlide.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSlide.getMerchantIcon()).isEqualTo(UPDATED_MERCHANT_ICON);
        assertThat(testSlide.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
        assertThat(testSlide.getTags()).isEqualTo(UPDATED_TAGS);
    }

    @Test
    @Transactional
    void patchNonExistingSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();
        slide.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, slide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(slide))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();
        slide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(slide))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();
        slide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlideMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(slide)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSlide() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        int databaseSizeBeforeDelete = slideRepository.findAll().size();

        // Delete the slide
        restSlideMockMvc
            .perform(delete(ENTITY_API_URL_ID, slide.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
