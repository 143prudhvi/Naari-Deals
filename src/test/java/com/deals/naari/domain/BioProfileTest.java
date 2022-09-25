package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BioProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BioProfile.class);
        BioProfile bioProfile1 = new BioProfile();
        bioProfile1.setId(1L);
        BioProfile bioProfile2 = new BioProfile();
        bioProfile2.setId(bioProfile1.getId());
        assertThat(bioProfile1).isEqualTo(bioProfile2);
        bioProfile2.setId(2L);
        assertThat(bioProfile1).isNotEqualTo(bioProfile2);
        bioProfile1.setId(null);
        assertThat(bioProfile1).isNotEqualTo(bioProfile2);
    }
}
