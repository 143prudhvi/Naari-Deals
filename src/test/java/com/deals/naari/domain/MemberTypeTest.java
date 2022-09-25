package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberType.class);
        MemberType memberType1 = new MemberType();
        memberType1.setId(1L);
        MemberType memberType2 = new MemberType();
        memberType2.setId(memberType1.getId());
        assertThat(memberType1).isEqualTo(memberType2);
        memberType2.setId(2L);
        assertThat(memberType1).isNotEqualTo(memberType2);
        memberType1.setId(null);
        assertThat(memberType1).isNotEqualTo(memberType2);
    }
}
