package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.Slide;
import com.deals.naari.repository.SlideRepository;
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

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DEAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_DEAL_URL = "BBBBBBBBBB";

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
            .imageUrl(DEFAULT_IMAGE_URL)
            .status(DEFAULT_STATUS)
            .country(DEFAULT_COUNTRY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .dealUrl(DEFAULT_DEAL_URL);
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
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .dealUrl(UPDATED_DEAL_URL);
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
        assertThat(testSlide.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testSlide.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSlide.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
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
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].dealUrl").value(hasItem(DEFAULT_DEAL_URL)));
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
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE))
            .andExpect(jsonPath("$.dealUrl").value(DEFAULT_DEAL_URL));
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
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .dealUrl(UPDATED_DEAL_URL);

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
        assertThat(testSlide.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSlide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSlide.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
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

        partialUpdatedSlide.title(UPDATED_TITLE).imageUrl(UPDATED_IMAGE_URL).country(UPDATED_COUNTRY).endDate(UPDATED_END_DATE);

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
        assertThat(testSlide.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSlide.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSlide.getDealUrl()).isEqualTo(DEFAULT_DEAL_URL);
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
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS)
            .country(UPDATED_COUNTRY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .dealUrl(UPDATED_DEAL_URL);

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
        assertThat(testSlide.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testSlide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSlide.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSlide.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSlide.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSlide.getDealUrl()).isEqualTo(UPDATED_DEAL_URL);
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
