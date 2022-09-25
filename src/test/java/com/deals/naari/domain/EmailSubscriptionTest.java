package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailSubscriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailSubscription.class);
        EmailSubscription emailSubscription1 = new EmailSubscription();
        emailSubscription1.setId(1L);
        EmailSubscription emailSubscription2 = new EmailSubscription();
        emailSubscription2.setId(emailSubscription1.getId());
        assertThat(emailSubscription1).isEqualTo(emailSubscription2);
        emailSubscription2.setId(2L);
        assertThat(emailSubscription1).isNotEqualTo(emailSubscription2);
        emailSubscription1.setId(null);
        assertThat(emailSubscription1).isNotEqualTo(emailSubscription2);
    }
}
