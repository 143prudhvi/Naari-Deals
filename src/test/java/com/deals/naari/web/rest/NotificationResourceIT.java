package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Notification;
import com.deals.naari.repository.NotificationRepository;
import com.deals.naari.service.criteria.NotificationCriteria;
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
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_READ = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_READ = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMockMvc;

    private Notification notification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .userId(DEFAULT_USER_ID)
            .title(DEFAULT_TITLE)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .dateOfRead(DEFAULT_DATE_OF_READ)
            .imageUrl(DEFAULT_IMAGE_URL)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .discountType(DEFAULT_DISCOUNT_TYPE);
        return notification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification notification = new Notification()
            .userId(UPDATED_USER_ID)
            .title(UPDATED_TITLE)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .dateOfRead(UPDATED_DATE_OF_READ)
            .imageUrl(UPDATED_IMAGE_URL)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountType(UPDATED_DISCOUNT_TYPE);
        return notification;
    }

    @BeforeEach
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();
        // Create the Notification
        restNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testNotification.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testNotification.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNotification.getDateOfRead()).isEqualTo(DEFAULT_DATE_OF_READ);
        assertThat(testNotification.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testNotification.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testNotification.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testNotification.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testNotification.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void createNotificationWithExistingId() throws Exception {
        // Create the Notification with an existing ID
        notification.setId(1L);

        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfRead").value(hasItem(DEFAULT_DATE_OF_READ)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE)));
    }

    @Test
    @Transactional
    void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dateOfRead").value(DEFAULT_DATE_OF_READ))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.discountType").value(DEFAULT_DISCOUNT_TYPE));
    }

    @Test
    @Transactional
    void getNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        Long id = notification.getId();

        defaultNotificationShouldBeFound("id.equals=" + id);
        defaultNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where userId equals to DEFAULT_USER_ID
        defaultNotificationShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the notificationList where userId equals to UPDATED_USER_ID
        defaultNotificationShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllNotificationsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultNotificationShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the notificationList where userId equals to UPDATED_USER_ID
        defaultNotificationShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllNotificationsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where userId is not null
        defaultNotificationShouldBeFound("userId.specified=true");

        // Get all the notificationList where userId is null
        defaultNotificationShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByUserIdContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where userId contains DEFAULT_USER_ID
        defaultNotificationShouldBeFound("userId.contains=" + DEFAULT_USER_ID);

        // Get all the notificationList where userId contains UPDATED_USER_ID
        defaultNotificationShouldNotBeFound("userId.contains=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllNotificationsByUserIdNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where userId does not contain DEFAULT_USER_ID
        defaultNotificationShouldNotBeFound("userId.doesNotContain=" + DEFAULT_USER_ID);

        // Get all the notificationList where userId does not contain UPDATED_USER_ID
        defaultNotificationShouldBeFound("userId.doesNotContain=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    void getAllNotificationsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title equals to DEFAULT_TITLE
        defaultNotificationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the notificationList where title equals to UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNotificationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the notificationList where title equals to UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title is not null
        defaultNotificationShouldBeFound("title.specified=true");

        // Get all the notificationList where title is null
        defaultNotificationShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByTitleContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title contains DEFAULT_TITLE
        defaultNotificationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the notificationList where title contains UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title does not contain DEFAULT_TITLE
        defaultNotificationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the notificationList where title does not contain UPDATED_TITLE
        defaultNotificationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message equals to DEFAULT_MESSAGE
        defaultNotificationShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the notificationList where message equals to UPDATED_MESSAGE
        defaultNotificationShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultNotificationShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the notificationList where message equals to UPDATED_MESSAGE
        defaultNotificationShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message is not null
        defaultNotificationShouldBeFound("message.specified=true");

        // Get all the notificationList where message is null
        defaultNotificationShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message contains DEFAULT_MESSAGE
        defaultNotificationShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the notificationList where message contains UPDATED_MESSAGE
        defaultNotificationShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message does not contain DEFAULT_MESSAGE
        defaultNotificationShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the notificationList where message does not contain UPDATED_MESSAGE
        defaultNotificationShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where status equals to DEFAULT_STATUS
        defaultNotificationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the notificationList where status equals to UPDATED_STATUS
        defaultNotificationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNotificationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNotificationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the notificationList where status equals to UPDATED_STATUS
        defaultNotificationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNotificationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where status is not null
        defaultNotificationShouldBeFound("status.specified=true");

        // Get all the notificationList where status is null
        defaultNotificationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where status contains DEFAULT_STATUS
        defaultNotificationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the notificationList where status contains UPDATED_STATUS
        defaultNotificationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNotificationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where status does not contain DEFAULT_STATUS
        defaultNotificationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the notificationList where status does not contain UPDATED_STATUS
        defaultNotificationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type equals to DEFAULT_TYPE
        defaultNotificationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the notificationList where type equals to UPDATED_TYPE
        defaultNotificationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNotificationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the notificationList where type equals to UPDATED_TYPE
        defaultNotificationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type is not null
        defaultNotificationShouldBeFound("type.specified=true");

        // Get all the notificationList where type is null
        defaultNotificationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type contains DEFAULT_TYPE
        defaultNotificationShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the notificationList where type contains UPDATED_TYPE
        defaultNotificationShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type does not contain DEFAULT_TYPE
        defaultNotificationShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the notificationList where type does not contain UPDATED_TYPE
        defaultNotificationShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByDateOfReadIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where dateOfRead equals to DEFAULT_DATE_OF_READ
        defaultNotificationShouldBeFound("dateOfRead.equals=" + DEFAULT_DATE_OF_READ);

        // Get all the notificationList where dateOfRead equals to UPDATED_DATE_OF_READ
        defaultNotificationShouldNotBeFound("dateOfRead.equals=" + UPDATED_DATE_OF_READ);
    }

    @Test
    @Transactional
    void getAllNotificationsByDateOfReadIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where dateOfRead in DEFAULT_DATE_OF_READ or UPDATED_DATE_OF_READ
        defaultNotificationShouldBeFound("dateOfRead.in=" + DEFAULT_DATE_OF_READ + "," + UPDATED_DATE_OF_READ);

        // Get all the notificationList where dateOfRead equals to UPDATED_DATE_OF_READ
        defaultNotificationShouldNotBeFound("dateOfRead.in=" + UPDATED_DATE_OF_READ);
    }

    @Test
    @Transactional
    void getAllNotificationsByDateOfReadIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where dateOfRead is not null
        defaultNotificationShouldBeFound("dateOfRead.specified=true");

        // Get all the notificationList where dateOfRead is null
        defaultNotificationShouldNotBeFound("dateOfRead.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByDateOfReadContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where dateOfRead contains DEFAULT_DATE_OF_READ
        defaultNotificationShouldBeFound("dateOfRead.contains=" + DEFAULT_DATE_OF_READ);

        // Get all the notificationList where dateOfRead contains UPDATED_DATE_OF_READ
        defaultNotificationShouldNotBeFound("dateOfRead.contains=" + UPDATED_DATE_OF_READ);
    }

    @Test
    @Transactional
    void getAllNotificationsByDateOfReadNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where dateOfRead does not contain DEFAULT_DATE_OF_READ
        defaultNotificationShouldNotBeFound("dateOfRead.doesNotContain=" + DEFAULT_DATE_OF_READ);

        // Get all the notificationList where dateOfRead does not contain UPDATED_DATE_OF_READ
        defaultNotificationShouldBeFound("dateOfRead.doesNotContain=" + UPDATED_DATE_OF_READ);
    }

    @Test
    @Transactional
    void getAllNotificationsByOriginalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where originalPrice equals to DEFAULT_ORIGINAL_PRICE
        defaultNotificationShouldBeFound("originalPrice.equals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the notificationList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultNotificationShouldNotBeFound("originalPrice.equals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByOriginalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where originalPrice in DEFAULT_ORIGINAL_PRICE or UPDATED_ORIGINAL_PRICE
        defaultNotificationShouldBeFound("originalPrice.in=" + DEFAULT_ORIGINAL_PRICE + "," + UPDATED_ORIGINAL_PRICE);

        // Get all the notificationList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultNotificationShouldNotBeFound("originalPrice.in=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByOriginalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where originalPrice is not null
        defaultNotificationShouldBeFound("originalPrice.specified=true");

        // Get all the notificationList where originalPrice is null
        defaultNotificationShouldNotBeFound("originalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByOriginalPriceContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where originalPrice contains DEFAULT_ORIGINAL_PRICE
        defaultNotificationShouldBeFound("originalPrice.contains=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the notificationList where originalPrice contains UPDATED_ORIGINAL_PRICE
        defaultNotificationShouldNotBeFound("originalPrice.contains=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByOriginalPriceNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where originalPrice does not contain DEFAULT_ORIGINAL_PRICE
        defaultNotificationShouldNotBeFound("originalPrice.doesNotContain=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the notificationList where originalPrice does not contain UPDATED_ORIGINAL_PRICE
        defaultNotificationShouldBeFound("originalPrice.doesNotContain=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCurrentPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where currentPrice equals to DEFAULT_CURRENT_PRICE
        defaultNotificationShouldBeFound("currentPrice.equals=" + DEFAULT_CURRENT_PRICE);

        // Get all the notificationList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultNotificationShouldNotBeFound("currentPrice.equals=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCurrentPriceIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where currentPrice in DEFAULT_CURRENT_PRICE or UPDATED_CURRENT_PRICE
        defaultNotificationShouldBeFound("currentPrice.in=" + DEFAULT_CURRENT_PRICE + "," + UPDATED_CURRENT_PRICE);

        // Get all the notificationList where currentPrice equals to UPDATED_CURRENT_PRICE
        defaultNotificationShouldNotBeFound("currentPrice.in=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCurrentPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where currentPrice is not null
        defaultNotificationShouldBeFound("currentPrice.specified=true");

        // Get all the notificationList where currentPrice is null
        defaultNotificationShouldNotBeFound("currentPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByCurrentPriceContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where currentPrice contains DEFAULT_CURRENT_PRICE
        defaultNotificationShouldBeFound("currentPrice.contains=" + DEFAULT_CURRENT_PRICE);

        // Get all the notificationList where currentPrice contains UPDATED_CURRENT_PRICE
        defaultNotificationShouldNotBeFound("currentPrice.contains=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCurrentPriceNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where currentPrice does not contain DEFAULT_CURRENT_PRICE
        defaultNotificationShouldNotBeFound("currentPrice.doesNotContain=" + DEFAULT_CURRENT_PRICE);

        // Get all the notificationList where currentPrice does not contain UPDATED_CURRENT_PRICE
        defaultNotificationShouldBeFound("currentPrice.doesNotContain=" + UPDATED_CURRENT_PRICE);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discount equals to DEFAULT_DISCOUNT
        defaultNotificationShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the notificationList where discount equals to UPDATED_DISCOUNT
        defaultNotificationShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultNotificationShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the notificationList where discount equals to UPDATED_DISCOUNT
        defaultNotificationShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discount is not null
        defaultNotificationShouldBeFound("discount.specified=true");

        // Get all the notificationList where discount is null
        defaultNotificationShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discount contains DEFAULT_DISCOUNT
        defaultNotificationShouldBeFound("discount.contains=" + DEFAULT_DISCOUNT);

        // Get all the notificationList where discount contains UPDATED_DISCOUNT
        defaultNotificationShouldNotBeFound("discount.contains=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discount does not contain DEFAULT_DISCOUNT
        defaultNotificationShouldNotBeFound("discount.doesNotContain=" + DEFAULT_DISCOUNT);

        // Get all the notificationList where discount does not contain UPDATED_DISCOUNT
        defaultNotificationShouldBeFound("discount.doesNotContain=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discountType equals to DEFAULT_DISCOUNT_TYPE
        defaultNotificationShouldBeFound("discountType.equals=" + DEFAULT_DISCOUNT_TYPE);

        // Get all the notificationList where discountType equals to UPDATED_DISCOUNT_TYPE
        defaultNotificationShouldNotBeFound("discountType.equals=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discountType in DEFAULT_DISCOUNT_TYPE or UPDATED_DISCOUNT_TYPE
        defaultNotificationShouldBeFound("discountType.in=" + DEFAULT_DISCOUNT_TYPE + "," + UPDATED_DISCOUNT_TYPE);

        // Get all the notificationList where discountType equals to UPDATED_DISCOUNT_TYPE
        defaultNotificationShouldNotBeFound("discountType.in=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discountType is not null
        defaultNotificationShouldBeFound("discountType.specified=true");

        // Get all the notificationList where discountType is null
        defaultNotificationShouldNotBeFound("discountType.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountTypeContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discountType contains DEFAULT_DISCOUNT_TYPE
        defaultNotificationShouldBeFound("discountType.contains=" + DEFAULT_DISCOUNT_TYPE);

        // Get all the notificationList where discountType contains UPDATED_DISCOUNT_TYPE
        defaultNotificationShouldNotBeFound("discountType.contains=" + UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByDiscountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where discountType does not contain DEFAULT_DISCOUNT_TYPE
        defaultNotificationShouldNotBeFound("discountType.doesNotContain=" + DEFAULT_DISCOUNT_TYPE);

        // Get all the notificationList where discountType does not contain UPDATED_DISCOUNT_TYPE
        defaultNotificationShouldBeFound("discountType.doesNotContain=" + UPDATED_DISCOUNT_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationShouldBeFound(String filter) throws Exception {
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfRead").value(hasItem(DEFAULT_DATE_OF_READ)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE)));

        // Check, that the count call also returns 1
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationShouldNotBeFound(String filter) throws Exception {
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).get();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .userId(UPDATED_USER_ID)
            .title(UPDATED_TITLE)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .dateOfRead(UPDATED_DATE_OF_READ)
            .imageUrl(UPDATED_IMAGE_URL)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountType(UPDATED_DISCOUNT_TYPE);

        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotification.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotification.getDateOfRead()).isEqualTo(UPDATED_DATE_OF_READ);
        assertThat(testNotification.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testNotification.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testNotification.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testNotification.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testNotification.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationWithPatch() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification using partial update
        Notification partialUpdatedNotification = new Notification();
        partialUpdatedNotification.setId(notification.getId());

        partialUpdatedNotification
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .dateOfRead(UPDATED_DATE_OF_READ)
            .imageUrl(UPDATED_IMAGE_URL)
            .currentPrice(UPDATED_CURRENT_PRICE);

        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotification.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotification.getDateOfRead()).isEqualTo(UPDATED_DATE_OF_READ);
        assertThat(testNotification.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testNotification.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testNotification.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testNotification.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testNotification.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateNotificationWithPatch() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification using partial update
        Notification partialUpdatedNotification = new Notification();
        partialUpdatedNotification.setId(notification.getId());

        partialUpdatedNotification
            .userId(UPDATED_USER_ID)
            .title(UPDATED_TITLE)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .dateOfRead(UPDATED_DATE_OF_READ)
            .imageUrl(UPDATED_IMAGE_URL)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountType(UPDATED_DISCOUNT_TYPE);

        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotification.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotification.getDateOfRead()).isEqualTo(UPDATED_DATE_OF_READ);
        assertThat(testNotification.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testNotification.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testNotification.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testNotification.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testNotification.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notification))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(notification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Delete the notification
        restNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, notification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
