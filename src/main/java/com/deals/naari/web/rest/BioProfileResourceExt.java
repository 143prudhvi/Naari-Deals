package com.deals.naari.web.rest;

import com.deals.naari.domain.BioProfile;
import com.deals.naari.domain.LoginProfile;
import com.deals.naari.service.BioProfileServiceExt;
import com.deals.naari.service.LoginProfileServiceExt;
import com.deals.naari.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.deals.naari.domain.BioProfile}.
 */
@RestController
@RequestMapping("/api")
public class BioProfileResourceExt {

    private final Logger log = LoggerFactory.getLogger(BioProfileResourceExt.class);

    private static final String ENTITY_NAME = "bioProfileExt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BioProfileServiceExt bioProfileServiceExt;

    private final LoginProfileServiceExt loginProfileServiceExt;

    public BioProfileResourceExt(BioProfileServiceExt bioProfileServiceExt, LoginProfileServiceExt loginProfileServiceExt) {
        this.bioProfileServiceExt = bioProfileServiceExt;
        this.loginProfileServiceExt = loginProfileServiceExt;
    }

    /**
     * {@code GET  /bio-profiles/:id} : get the "id" bioProfile.
     *
     * @param id the id of the bioProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bioProfile, or with status {@code 404 (Not Found)}.
     * @throws URISyntaxException
     */
    @GetMapping("/bio-profile-by-name/{username}")
    public ResponseEntity<BioProfile> getBioProfileByName(@PathVariable String username) throws URISyntaxException {
        log.debug("REST request to get BioProfile : {}", username);
        BioProfile bioProfile = new BioProfile();
        bioProfile = bioProfileServiceExt.findByUserName(username);

        return ResponseEntity
            .created(new URI("/api/bio-profile-by-name/" + username))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "Bio Profile"))
            .body(bioProfile);
    }

    @GetMapping("/login-profile-by-name/{username}")
    public ResponseEntity<LoginProfile> getLoginProfileByName(@PathVariable String username) throws URISyntaxException {
        log.debug("REST request to get LoginProfile : {}", username);
        LoginProfile loginProfile = new LoginProfile();
        loginProfile = loginProfileServiceExt.findByUsername(username);
        return ResponseEntity
            .created(new URI("/api/login-profile-by-name/" + username))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "Login Profile"))
            .body(loginProfile);
    }
}
