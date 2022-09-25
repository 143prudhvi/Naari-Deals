package com.deals.naari.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.deals.naari.IntegrationTest;
import com.deals.naari.domain.MemberType;
import com.deals.naari.repository.MemberTypeRepository;
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
 * Integration tests for the {@link MemberTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberTypeResourceIT {

    private static final String DEFAULT_MEMBER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/member-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberTypeRepository memberTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberTypeMockMvc;

    private MemberType memberType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberType createEntity(EntityManager em) {
        MemberType memberType = new MemberType()
            .memberType(DEFAULT_MEMBER_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .imageUrl(DEFAULT_IMAGE_URL);
        return memberType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberType createUpdatedEntity(EntityManager em) {
        MemberType memberType = new MemberType()
            .memberType(UPDATED_MEMBER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .imageUrl(UPDATED_IMAGE_URL);
        return memberType;
    }

    @BeforeEach
    public void initTest() {
        memberType = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberType() throws Exception {
        int databaseSizeBeforeCreate = memberTypeRepository.findAll().size();
        // Create the MemberType
        restMemberTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberType)))
            .andExpect(status().isCreated());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MemberType testMemberType = memberTypeList.get(memberTypeList.size() - 1);
        assertThat(testMemberType.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testMemberType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMemberType.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createMemberTypeWithExistingId() throws Exception {
        // Create the MemberType with an existing ID
        memberType.setId(1L);

        int databaseSizeBeforeCreate = memberTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberType)))
            .andExpect(status().isBadRequest());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberTypes() throws Exception {
        // Initialize the database
        memberTypeRepository.saveAndFlush(memberType);

        // Get all the memberTypeList
        restMemberTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberType.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    @Transactional
    void getMemberType() throws Exception {
        // Initialize the database
        memberTypeRepository.saveAndFlush(memberType);

        // Get the memberType
        restMemberTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, memberType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberType.getId().intValue()))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMemberType() throws Exception {
        // Get the memberType
        restMemberTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMemberType() throws Exception {
        // Initialize the database
        memberTypeRepository.saveAndFlush(memberType);

        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();

        // Update the memberType
        MemberType updatedMemberType = memberTypeRepository.findById(memberType.getId()).get();
        // Disconnect from session so that the updates on updatedMemberType are not directly saved in db
        em.detach(updatedMemberType);
        updatedMemberType.memberType(UPDATED_MEMBER_TYPE).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);

        restMemberTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMemberType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMemberType))
            )
            .andExpect(status().isOk());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
        MemberType testMemberType = memberTypeList.get(memberTypeList.size() - 1);
        assertThat(testMemberType.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMemberType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMemberType.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingMemberType() throws Exception {
        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();
        memberType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberType() throws Exception {
        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();
        memberType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberType() throws Exception {
        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();
        memberType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberTypeWithPatch() throws Exception {
        // Initialize the database
        memberTypeRepository.saveAndFlush(memberType);

        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();

        // Update the memberType using partial update
        MemberType partialUpdatedMemberType = new MemberType();
        partialUpdatedMemberType.setId(memberType.getId());

        partialUpdatedMemberType.memberType(UPDATED_MEMBER_TYPE).imageUrl(UPDATED_IMAGE_URL);

        restMemberTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberType))
            )
            .andExpect(status().isOk());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
        MemberType testMemberType = memberTypeList.get(memberTypeList.size() - 1);
        assertThat(testMemberType.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMemberType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMemberType.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateMemberTypeWithPatch() throws Exception {
        // Initialize the database
        memberTypeRepository.saveAndFlush(memberType);

        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();

        // Update the memberType using partial update
        MemberType partialUpdatedMemberType = new MemberType();
        partialUpdatedMemberType.setId(memberType.getId());

        partialUpdatedMemberType.memberType(UPDATED_MEMBER_TYPE).description(UPDATED_DESCRIPTION).imageUrl(UPDATED_IMAGE_URL);

        restMemberTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberType))
            )
            .andExpect(status().isOk());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
        MemberType testMemberType = memberTypeList.get(memberTypeList.size() - 1);
        assertThat(testMemberType.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMemberType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMemberType.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingMemberType() throws Exception {
        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();
        memberType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberType() throws Exception {
        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();
        memberType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberType))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberType() throws Exception {
        int databaseSizeBeforeUpdate = memberTypeRepository.findAll().size();
        memberType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberType in the database
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberType() throws Exception {
        // Initialize the database
        memberTypeRepository.saveAndFlush(memberType);

        int databaseSizeBeforeDelete = memberTypeRepository.findAll().size();

        // Delete the memberType
        restMemberTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberType> memberTypeList = memberTypeRepository.findAll();
        assertThat(memberTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
