package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealType.class);
        DealType dealType1 = new DealType();
        dealType1.setId(1L);
        DealType dealType2 = new DealType();
        dealType2.setId(dealType1.getId());
        assertThat(dealType1).isEqualTo(dealType2);
        dealType2.setId(2L);
        assertThat(dealType1).isNotEqualTo(dealType2);
        dealType1.setId(null);
        assertThat(dealType1).isNotEqualTo(dealType2);
    }
}
