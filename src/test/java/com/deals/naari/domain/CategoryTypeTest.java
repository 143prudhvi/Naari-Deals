package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoryTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryType.class);
        CategoryType categoryType1 = new CategoryType();
        categoryType1.setId(1L);
        CategoryType categoryType2 = new CategoryType();
        categoryType2.setId(categoryType1.getId());
        assertThat(categoryType1).isEqualTo(categoryType2);
        categoryType2.setId(2L);
        assertThat(categoryType1).isNotEqualTo(categoryType2);
        categoryType1.setId(null);
        assertThat(categoryType1).isNotEqualTo(categoryType2);
    }
}
