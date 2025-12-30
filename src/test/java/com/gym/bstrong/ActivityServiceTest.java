package com.gym.bstrong;

import com.gym.bstrong.domain.Activity;
import com.gym.bstrong.domain.Monitor;
import com.gym.bstrong.dto.ActivityInDto;
import com.gym.bstrong.dto.ActivityOutDto;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.repository.ActivityRepository;
import com.gym.bstrong.repository.MonitorRepository;
import com.gym.bstrong.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private MonitorRepository monitorRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        Activity activity1 = new Activity(1L, "Yoga", "Relaxing yoga", 30, 20, true, 5, new Monitor());
        Activity activity2 = new Activity(2L, "Zumba", "High energy", 30, 30, true, 4, new Monitor());
        List<Activity> mockActivities = List.of(activity1, activity2);

        ActivityOutDto dto1 = new ActivityOutDto(1L, "Yoga", "Relaxing yoga", 30, 20, "Monitor1");
        ActivityOutDto dto2 = new ActivityOutDto(2L, "Zumba", "High energy", 30, 30, "Monitor2");
        List<ActivityOutDto> mockDtos = List.of(dto1, dto2);

        when(activityRepository.findAll()).thenReturn(mockActivities);
        when(modelMapper.map(mockActivities, new TypeToken<List<ActivityOutDto>>() {}.getType())).thenReturn(mockDtos);

        List<ActivityOutDto> result = activityService.findAll(null, null, null);

        assertEquals(2, result.size());
        assertEquals("Yoga", result.get(0).getName());

        verify(activityRepository, times(1)).findAll();
    }

    @Test
    public void testAddActivity() throws MonitorNotFoundException {
        ActivityInDto inputDto = new ActivityInDto("Yoga", "Relaxing yoga", 20, 60, true, 20, 1L);
        Monitor monitor = new Monitor();
        monitor.setId(1L);
        monitor.setName("Pepe");

        Activity mappedActivity = new Activity();
        mappedActivity.setName("Yoga");

        Activity savedActivity = new Activity(1L, "Yoga", "Relaxing yoga", 20, 60, true, 20, monitor);
        ActivityOutDto outputDto = new ActivityOutDto(1L, "Yoga", "Relaxing yoga", 20, 60, "Pepe");

        when(monitorRepository.findById(1L)).thenReturn(Optional.of(monitor));
        when(modelMapper.map(inputDto, Activity.class)).thenReturn(mappedActivity);
        when(activityRepository.save(any(Activity.class))).thenReturn(savedActivity);
        when(modelMapper.map(savedActivity, ActivityOutDto.class)).thenReturn(outputDto);

        ActivityOutDto result = activityService.addActivity(inputDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Yoga", result.getName());
        assertEquals("Pepe", result.getMonitorName());

        verify(monitorRepository, times(1)).findById(1L);
        verify(activityRepository, times(1)).save(any(Activity.class));
    }
}
