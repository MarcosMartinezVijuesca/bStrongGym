package com.gym.bstrong.service;

import com.gym.bstrong.domain.Activity;
import com.gym.bstrong.domain.Monitor;
import com.gym.bstrong.dto.ActivityInDto;
import com.gym.bstrong.dto.ActivityOutDto;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.repository.ActivityRepository;
import com.gym.bstrong.repository.MonitorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ActivityOutDto> findAll(String name, Boolean active, Integer minCapacity) {
        List<Activity> activities;

        if (name == null && active == null && minCapacity == null) {
            activities = activityRepository.findAll();
        } else {
            activities = activityRepository.findByFilters(name, active, minCapacity);
        }

        return modelMapper.map(activities, new TypeToken<List<ActivityOutDto>>() {}.getType());
    }

    public ActivityOutDto findById(long id) throws ActivityNotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        return modelMapper.map(activity, ActivityOutDto.class);
    }

    public ActivityOutDto addActivity(ActivityInDto activityInDto) throws MonitorNotFoundException {
        Activity activity = modelMapper.map(activityInDto, Activity.class);

        Monitor monitor = monitorRepository.findById(activityInDto.getMonitorId())
                .orElseThrow(MonitorNotFoundException::new);

        activity.setMonitor(monitor);

        Activity newActivity = activityRepository.save(activity);
        return modelMapper.map(newActivity, ActivityOutDto.class);
    }

    public ActivityOutDto modifyActivity(long id, ActivityInDto activityInDto) throws ActivityNotFoundException, MonitorNotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);

        modelMapper.map(activityInDto, activity);
        activity.setId(id);

        if (activity.getMonitor() == null || activity.getMonitor().getId() != activityInDto.getMonitorId()) {
            Monitor monitor = monitorRepository.findById(activityInDto.getMonitorId())
                    .orElseThrow(MonitorNotFoundException::new);
            activity.setMonitor(monitor);
        }

        Activity modifiedActivity = activityRepository.save(activity);
        return modelMapper.map(modifiedActivity, ActivityOutDto.class);
    }

    public void deleteActivity(long id) throws ActivityNotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(ActivityNotFoundException::new);
        activityRepository.delete(activity);
    }
}