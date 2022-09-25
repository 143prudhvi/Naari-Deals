package com.deals.naari.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.deals.naari.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationType.class);
        NotificationType notificationType1 = new NotificationType();
        notificationType1.setId(1L);
        NotificationType notificationType2 = new NotificationType();
        notificationType2.setId(notificationType1.getId());
        assertThat(notificationType1).isEqualTo(notificationType2);
        notificationType2.setId(2L);
        assertThat(notificationType1).isNotEqualTo(notificationType2);
        notificationType1.setId(null);
        assertThat(notificationType1).isNotEqualTo(notificationType2);
    }
}
