package com.deals.naari.service;

import com.deals.naari.domain.BioProfile;
import com.deals.naari.repository.BioProfileRepositoryExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BioProfileServiceExt {

    private final Logger log = LoggerFactory.getLogger(BioProfileServiceExt.class);

    private final BioProfileRepositoryExt bioProfileRepositoryExt;

    public BioProfileServiceExt(BioProfileRepositoryExt bioProfileRepositoryExt) {
        this.bioProfileRepositoryExt = bioProfileRepositoryExt;
    }

    @Transactional(readOnly = true)
    public BioProfile findByUserName(String username) {
        return bioProfileRepositoryExt.findByUserName(username);
    }

    @Transactional(readOnly = true)
    public BioProfile findByUserId(String userId) {
        return bioProfileRepositoryExt.findByUserId(userId).get();
    }
}
