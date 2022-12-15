package com.deals.naari.service;

import com.deals.naari.domain.Category;
import com.deals.naari.domain.Deal;
import com.deals.naari.domain.DealType;
import com.deals.naari.repository.CategoryRepositoryExt;
import com.deals.naari.repository.DealRepository;
import com.deals.naari.repository.DealRepositoryExt;
import com.deals.naari.repository.DealTypeRepositoryExt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deal}.
 */
@Service
@Transactional
public class DealServiceExt {

    private final Logger log = LoggerFactory.getLogger(DealServiceExt.class);

    private final DealRepository dealRepository;
    private final DealRepositoryExt dealRepositoryExt;

    private final CategoryRepositoryExt categoryRepositoryExt;

    private final DealTypeRepositoryExt dealTypeRepositoryExt;

    public DealServiceExt(
        DealRepositoryExt dealRepositoryExt,
        DealRepository dealRepository,
        CategoryRepositoryExt categoryRepositoryExt,
        DealTypeRepositoryExt dealTypeRepositoryExt
    ) {
        this.dealRepositoryExt = dealRepositoryExt;
        this.dealRepository = dealRepository;
        this.categoryRepositoryExt = categoryRepositoryExt;
        this.dealTypeRepositoryExt = dealTypeRepositoryExt;
    }

    public Deal save(Deal deal) {
        log.debug("Request to save Deal : {}", deal);

        for (String tag : deal.getTags().split(",")) {
            Optional<Category> category = categoryRepositoryExt.findByTitle(tag);
            if (category.isPresent()) {
                Category c = category.get();
                c.setSubTitle(Integer.toString(Integer.parseInt(c.getSubTitle()) + 1));
                categoryRepositoryExt.save(c);
            } else {
                Optional<DealType> dealType = dealTypeRepositoryExt.findByTitle(tag);
                DealType d = dealType.get();
                d.setSubTitle(Integer.toString(Integer.parseInt(d.getSubTitle()) + 1));
                dealTypeRepositoryExt.save(d);
            }
        }
        return dealRepository.save(deal);
    }

    public Deal update(Deal deal) {
        log.debug("Request to update Deal : {}", deal);

        Optional<Deal> existingDeal = dealRepository.findById(deal.getId());

        if (existingDeal.isPresent()) {
            if (!existingDeal.get().getTags().equals(deal.getTags())) {
                ArrayList<String> addedTags = new ArrayList<String>();
                String[] existingTags = existingDeal.get().getTags().split(",");
                String[] updatedTags = deal.getTags().split(",");
                ArrayList<String> existingTagsList = new ArrayList<>(Arrays.asList(existingTags));
                for (String tag : updatedTags) {
                    if (existingTagsList.contains(tag)) {
                        existingTagsList.remove(tag);
                    } else {
                        addedTags.add(tag);
                    }
                }

                for (String tag : addedTags) {
                    Optional<Category> category = categoryRepositoryExt.findByTitle(tag);
                    if (category.isPresent()) {
                        Category c = category.get();
                        c.setSubTitle(Integer.toString(Integer.parseInt(c.getSubTitle()) + 1));
                        categoryRepositoryExt.save(c);
                    } else {
                        Optional<DealType> dealType = dealTypeRepositoryExt.findByTitle(tag);
                        DealType d = dealType.get();
                        d.setSubTitle(Integer.toString(Integer.parseInt(d.getSubTitle()) + 1));
                        dealTypeRepositoryExt.save(d);
                    }
                }

                for (String tag : existingTagsList) {
                    Optional<Category> category = categoryRepositoryExt.findByTitle(tag);
                    if (category.isPresent()) {
                        Category c = category.get();
                        c.setSubTitle(Integer.toString(Integer.parseInt(c.getSubTitle()) - 1));
                        categoryRepositoryExt.save(c);
                    } else {
                        Optional<DealType> dealType = dealTypeRepositoryExt.findByTitle(tag);
                        DealType d = dealType.get();
                        d.setSubTitle(Integer.toString(Integer.parseInt(d.getSubTitle()) - 1));
                        dealTypeRepositoryExt.save(d);
                    }
                }
            }
        }

        return dealRepository.save(deal);
    }

    public Optional<Deal> partialUpdate(Deal deal) {
        log.debug("Request to partially update Deal : {}", deal);

        return dealRepository
            .findById(deal.getId())
            .map(existingDeal -> {
                if (deal.getTitle() != null) {
                    existingDeal.setTitle(deal.getTitle());
                }
                if (deal.getDescription() != null) {
                    existingDeal.setDescription(deal.getDescription());
                }
                if (deal.getImageUrl() != null) {
                    existingDeal.setImageUrl(deal.getImageUrl());
                }
                if (deal.getDealUrl() != null) {
                    existingDeal.setDealUrl(deal.getDealUrl());
                }
                if (deal.getPostedBy() != null) {
                    existingDeal.setPostedBy(deal.getPostedBy());
                }
                if (deal.getPostedDate() != null) {
                    existingDeal.setPostedDate(deal.getPostedDate());
                }
                if (deal.getStartDate() != null) {
                    existingDeal.setStartDate(deal.getStartDate());
                }
                if (deal.getEndDate() != null) {
                    existingDeal.setEndDate(deal.getEndDate());
                }
                if (deal.getOriginalPrice() != null) {
                    existingDeal.setOriginalPrice(deal.getOriginalPrice());
                }
                if (deal.getCurrentPrice() != null) {
                    existingDeal.setCurrentPrice(deal.getCurrentPrice());
                }
                if (deal.getDiscount() != null) {
                    existingDeal.setDiscount(deal.getDiscount());
                }
                if (deal.getDiscountType() != null) {
                    existingDeal.setDiscountType(deal.getDiscountType());
                }
                if (deal.getActive() != null) {
                    existingDeal.setActive(deal.getActive());
                }
                if (deal.getApproved() != null) {
                    existingDeal.setApproved(deal.getApproved());
                }
                if (deal.getCountry() != null) {
                    existingDeal.setCountry(deal.getCountry());
                }
                if (deal.getCity() != null) {
                    existingDeal.setCity(deal.getCity());
                }
                if (deal.getPinCode() != null) {
                    existingDeal.setPinCode(deal.getPinCode());
                }
                if (deal.getMerchant() != null) {
                    existingDeal.setMerchant(deal.getMerchant());
                }
                if (deal.getTags() != null) {
                    if (!existingDeal.getTags().equals(deal.getTags())) {
                        ArrayList<String> addedTags = new ArrayList<String>();
                        String[] existingTags = existingDeal.getTags().split(",");
                        String[] updatedTags = deal.getTags().split(",");
                        ArrayList<String> existingTagsList = new ArrayList<>(Arrays.asList(existingTags));
                        for (String tag : updatedTags) {
                            if (existingTagsList.contains(tag)) {
                                existingTagsList.remove(tag);
                            } else {
                                addedTags.add(tag);
                            }
                        }

                        for (String tag : addedTags) {
                            Optional<Category> category = categoryRepositoryExt.findByTitle(tag);
                            if (category.isPresent()) {
                                Category c = category.get();
                                c.setSubTitle(Integer.toString(Integer.parseInt(c.getSubTitle()) + 1));
                                categoryRepositoryExt.save(c);
                            } else {
                                Optional<DealType> dealType = dealTypeRepositoryExt.findByTitle(tag);
                                DealType d = dealType.get();
                                d.setSubTitle(Integer.toString(Integer.parseInt(d.getSubTitle()) + 1));
                                dealTypeRepositoryExt.save(d);
                            }
                        }

                        for (String tag : existingTagsList) {
                            Optional<Category> category = categoryRepositoryExt.findByTitle(tag);
                            if (category.isPresent()) {
                                Category c = category.get();
                                c.setSubTitle(Integer.toString(Integer.parseInt(c.getSubTitle()) - 1));
                                categoryRepositoryExt.save(c);
                            } else {
                                Optional<DealType> dealType = dealTypeRepositoryExt.findByTitle(tag);
                                DealType d = dealType.get();
                                d.setSubTitle(Integer.toString(Integer.parseInt(d.getSubTitle()) - 1));
                                dealTypeRepositoryExt.save(d);
                            }
                        }

                        existingDeal.setTags(deal.getTags());
                    }
                }

                return existingDeal;
            })
            .map(dealRepository::save);
    }

    @Transactional(readOnly = true)
    public Page<Deal> findAll(Pageable pageable) {
        log.debug("Request to get all Deals");
        return dealRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Deal> findOne(Long id) {
        log.debug("Request to get Deal : {}", id);
        return dealRepository.findById(id);
    }

    public void deleteDeals(String ids) {
        log.debug("Request to delete Deals with ids : {}", ids);
        dealRepositoryExt.deleteDeals(ids);
    }
}
