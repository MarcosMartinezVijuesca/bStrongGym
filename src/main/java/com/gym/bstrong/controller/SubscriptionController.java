package com.gym.bstrong.controller;

import com.gym.bstrong.dto.SubscriptionInDto;
import com.gym.bstrong.dto.SubscriptionOutDto;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.exception.SubscriptionNotFoundException;
import com.gym.bstrong.service.SubscriptionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    private final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @GetMapping
    public ResponseEntity<List<SubscriptionOutDto>> getAllSubscriptions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Boolean active) {

        logger.info("GET /subscriptions params: type={}, memberId={}, active={}", type, memberId, active);
        return ResponseEntity.ok(subscriptionService.findAll(type, memberId, active));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionOutDto> getSubscription(@PathVariable long id) throws SubscriptionNotFoundException {
        logger.info("GET /subscriptions/{}", id);
        return ResponseEntity.ok(subscriptionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionOutDto> addSubscription(@Valid @RequestBody SubscriptionInDto subscriptionInDto)
            throws MemberNotFoundException {
        logger.info("POST /subscriptions");
        return new ResponseEntity<>(subscriptionService.addSubscription(subscriptionInDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionOutDto> modifySubscription(@PathVariable long id, @Valid @RequestBody SubscriptionInDto subscriptionInDto)
            throws SubscriptionNotFoundException, MemberNotFoundException {
        logger.info("PUT /subscriptions/{}", id);
        return ResponseEntity.ok(subscriptionService.modifySubscription(id, subscriptionInDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable long id) throws SubscriptionNotFoundException {
        logger.info("DELETE /subscriptions/{}", id);
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}