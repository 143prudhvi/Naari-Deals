package com.deals.naari.service.impl;

import com.deals.naari.domain.Feedback;
import com.deals.naari.repository.FeedbackRepository;
import com.deals.naari.service.FeedbackService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Feedback}.
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback save(Feedback feedback) {
        log.debug("Request to save Feedback : {}", feedback);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback update(Feedback feedback) {
        log.debug("Request to update Feedback : {}", feedback);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> partialUpdate(Feedback feedback) {
        log.debug("Request to partially update Feedback : {}", feedback);

        return feedbackRepository
            .findById(feedback.getId())
            .map(existingFeedback -> {
                if (feedback.getType() != null) {
                    existingFeedback.setType(feedback.getType());
                }
                if (feedback.getName() != null) {
                    existingFeedback.setName(feedback.getName());
                }
                if (feedback.getEmail() != null) {
                    existingFeedback.setEmail(feedback.getEmail());
                }
                if (feedback.getPhone() != null) {
                    existingFeedback.setPhone(feedback.getPhone());
                }
                if (feedback.getUserId() != null) {
                    existingFeedback.setUserId(feedback.getUserId());
                }
                if (feedback.getMessage() != null) {
                    existingFeedback.setMessage(feedback.getMessage());
                }

                return existingFeedback;
            })
            .map(feedbackRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findAll() {
        log.debug("Request to get all Feedbacks");
        return feedbackRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Feedback> findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        return feedbackRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
    }
}
