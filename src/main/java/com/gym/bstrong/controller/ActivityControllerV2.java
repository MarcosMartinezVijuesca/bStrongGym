package com.gym.bstrong.controller;

import com.gym.bstrong.dto.ActivityInDto;
import com.gym.bstrong.dto.ActivityOutDtoV2;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.service.ActivityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/activities")
public class ActivityControllerV2 {

    @Autowired
    private ActivityService activityService;

    private final Logger logger = LoggerFactory.getLogger(ActivityControllerV2.class);

    @PutMapping("/{id}")
    public ResponseEntity<ActivityOutDtoV2> modifyActivityV2(
            @PathVariable long id,
            @Valid @RequestBody ActivityInDto activityInDto)
            throws ActivityNotFoundException, MonitorNotFoundException {
        logger.info("PUT /v2/activities/{}", id);
        return ResponseEntity.ok(activityService.modifyActivityV2(id, activityInDto));
    }
}