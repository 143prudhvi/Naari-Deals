package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoginProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginProfile.class);
        LoginProfile loginProfile1 = new LoginProfile();
        loginProfile1.setId(1L);
        LoginProfile loginProfile2 = new LoginProfile();
        loginProfile2.setId(loginProfile1.getId());
        assertThat(loginProfile1).isEqualTo(loginProfile2);
        loginProfile2.setId(2L);
        assertThat(loginProfile1).isNotEqualTo(loginProfile2);
        loginProfile1.setId(null);
        assertThat(loginProfile1).isNotEqualTo(loginProfile2);
    }
}
