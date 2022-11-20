package com.deals.naari.service.impl;

import com.deals.naari.domain.Slide;
import com.deals.naari.repository.SlideRepository;
import com.deals.naari.service.SlideService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Slide}.
 */
@Service
@Transactional
public class SlideServiceImpl implements SlideService {

    private final Logger log = LoggerFactory.getLogger(SlideServiceImpl.class);

    private final SlideRepository slideRepository;

    public SlideServiceImpl(SlideRepository slideRepository) {
        this.slideRepository = slideRepository;
    }

    @Override
    public Slide save(Slide slide) {
        log.debug("Request to save Slide : {}", slide);
        return slideRepository.save(slide);
    }

    @Override
    public Slide update(Slide slide) {
        log.debug("Request to update Slide : {}", slide);
        return slideRepository.save(slide);
    }

    @Override
    public Optional<Slide> partialUpdate(Slide slide) {
        log.debug("Request to partially update Slide : {}", slide);

        return slideRepository
            .findById(slide.getId())
            .map(existingSlide -> {
                if (slide.getTitle() != null) {
                    existingSlide.setTitle(slide.getTitle());
                }
                if (slide.getSubTitle() != null) {
                    existingSlide.setSubTitle(slide.getSubTitle());
                }
                if (slide.getStatus() != null) {
                    existingSlide.setStatus(slide.getStatus());
                }
                if (slide.getCountry() != null) {
                    existingSlide.setCountry(slide.getCountry());
                }
                if (slide.getStartDate() != null) {
                    existingSlide.setStartDate(slide.getStartDate());
                }
                if (slide.getEndDate() != null) {
                    existingSlide.setEndDate(slide.getEndDate());
                }
                if (slide.getImageUrl() != null) {
                    existingSlide.setImageUrl(slide.getImageUrl());
                }
                if (slide.getMerchantIcon() != null) {
                    existingSlide.setMerchantIcon(slide.getMerchantIcon());
                }
                if (slide.getDealUrl() != null) {
                    existingSlide.setDealUrl(slide.getDealUrl());
                }
                if (slide.getTags() != null) {
                    existingSlide.setTags(slide.getTags());
                }

                return existingSlide;
            })
            .map(slideRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Slide> findAll() {
        log.debug("Request to get all Slides");
        return slideRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Slide> findOne(Long id) {
        log.debug("Request to get Slide : {}", id);
        return slideRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Slide : {}", id);
        slideRepository.deleteById(id);
    }
}
