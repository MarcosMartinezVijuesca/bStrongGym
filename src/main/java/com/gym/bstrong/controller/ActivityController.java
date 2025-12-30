package com.gym.bstrong.controller;

import com.gym.bstrong.dto.ActivityInDto;
import com.gym.bstrong.dto.ActivityOutDto;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.service.ActivityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @GetMapping
    public ResponseEntity<List<ActivityOutDto>> getAllActivities(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Integer minCapacity) {

        logger.info("GET /activities params: name={}, active={}, minCapacity={}", name, active, minCapacity);
        return ResponseEntity.ok(activityService.findAll(name, active, minCapacity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityOutDto> getActivity(@PathVariable long id) throws ActivityNotFoundException {
        logger.info("GET /activities/{}", id);
        return ResponseEntity.ok(activityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ActivityOutDto> addActivity(@Valid @RequestBody ActivityInDto activityInDto) throws MonitorNotFoundException {
        logger.info("POST /activities");
        return new ResponseEntity<>(activityService.addActivity(activityInDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityOutDto> modifyActivity(@PathVariable long id, @Valid @RequestBody ActivityInDto activityInDto)
            throws ActivityNotFoundException, MonitorNotFoundException {
        logger.info("PUT /activities/{}", id);
        return ResponseEntity.ok(activityService.modifyActivity(id, activityInDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable long id) throws ActivityNotFoundException {
        logger.info("DELETE /activities/{}", id);
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}