package com.gym.bstrong.controller;

import com.gym.bstrong.dto.MonitorInDto;
import com.gym.bstrong.dto.MonitorOutDto;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.service.MonitorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitors")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    private final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @GetMapping
    public ResponseEntity<List<MonitorOutDto>> getAllMonitors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Boolean available) {

        logger.info("Petición recibida: GET /monitors con filtros: nombre={}, especialidad={}, disponible={}", name, specialty, available);
        List<MonitorOutDto> monitors = monitorService.findAll(name, specialty, available);
        return new ResponseEntity<>(monitors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitorOutDto> getMonitor(@PathVariable long id) throws MonitorNotFoundException {
        logger.info("Petición recibida: GET /monitors/" + id);
        MonitorOutDto monitor = monitorService.findById(id);
        return new ResponseEntity<>(monitor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MonitorOutDto> addMonitor(@Valid @RequestBody MonitorInDto monitorInDto) {
        logger.info("Petición recibida: POST /monitors");
        MonitorOutDto newMonitor = monitorService.addMonitor(monitorInDto);
        return new ResponseEntity<>(newMonitor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitorOutDto> modifyMonitor(@PathVariable long id, @Valid @RequestBody MonitorInDto monitorInDto) throws MonitorNotFoundException {
        logger.info("Petición recibida: PUT /monitors/" + id);
        MonitorOutDto modifiedMonitor = monitorService.modifyMonitor(id, monitorInDto);
        return new ResponseEntity<>(modifiedMonitor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable long id) throws MonitorNotFoundException {
        logger.info("Petición recibida: DELETE /monitors/" + id);
        monitorService.deleteMonitor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}