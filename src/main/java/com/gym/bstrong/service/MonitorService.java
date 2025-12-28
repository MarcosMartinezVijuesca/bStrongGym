package com.gym.bstrong.service;

import com.gym.bstrong.domain.Monitor;
import com.gym.bstrong.dto.MonitorInDto;
import com.gym.bstrong.dto.MonitorOutDto;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.repository.MonitorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<MonitorOutDto> findAll(String name, String specialty, Boolean available) {
        List<Monitor> monitors;

        if (name == null && specialty == null && available == null) {
            monitors = monitorRepository.findAll();
        } else {
            monitors = monitorRepository.findByFilters(name, specialty, available);
        }

        return modelMapper.map(monitors, new TypeToken<List<MonitorOutDto>>() {}.getType());
    }

    public MonitorOutDto findById(long id) throws MonitorNotFoundException {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(MonitorNotFoundException::new);
        return modelMapper.map(monitor, MonitorOutDto.class);
    }

    public MonitorOutDto addMonitor(MonitorInDto monitorInDto) {
        Monitor monitor = modelMapper.map(monitorInDto, Monitor.class);

        monitor.setHireDate(LocalDate.now());

        Monitor newMonitor = monitorRepository.save(monitor);
        return modelMapper.map(newMonitor, MonitorOutDto.class);
    }

    public MonitorOutDto modifyMonitor(long id, MonitorInDto monitorInDto) throws MonitorNotFoundException {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(MonitorNotFoundException::new);

        modelMapper.map(monitorInDto, monitor);
        monitor.setId(id);

        Monitor modifiedMonitor = monitorRepository.save(monitor);
        return modelMapper.map(modifiedMonitor, MonitorOutDto.class);
    }

    public void deleteMonitor(long id) throws MonitorNotFoundException {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(MonitorNotFoundException::new);
        monitorRepository.delete(monitor);
    }
}