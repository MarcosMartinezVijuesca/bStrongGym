package com.gym.bstrong;

import com.gym.bstrong.domain.Monitor;
import com.gym.bstrong.dto.MonitorInDto;
import com.gym.bstrong.dto.MonitorOutDto;
import com.gym.bstrong.repository.MonitorRepository;
import com.gym.bstrong.service.MonitorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @InjectMocks
    private MonitorService monitorService;

    @Mock
    private MonitorRepository monitorRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        Monitor monitor1 = new Monitor(1L, "Laura", "12345678A", LocalDate.now(), 1500f, true, "Yoga");
        Monitor monitor2 = new Monitor(2L, "Pedro", "87654321B", LocalDate.now(), 1600f, false, "Crossfit");
        List<Monitor> mockMonitors = List.of(monitor1, monitor2);

        MonitorOutDto dto1 = new MonitorOutDto(1L, "Laura", "12345678A", "Yoga", true, LocalDate.now());
        MonitorOutDto dto2 = new MonitorOutDto(2L, "Pedro", "87654321B", "Crossfit", false, LocalDate.now());
        List<MonitorOutDto> mockDtos = List.of(dto1, dto2);

        when(monitorRepository.findAll()).thenReturn(mockMonitors);
        when(modelMapper.map(mockMonitors, new TypeToken<List<MonitorOutDto>>() {}.getType())).thenReturn(mockDtos);

        List<MonitorOutDto> result = monitorService.findAll(null, null, null);

        assertEquals(2, result.size());
        assertEquals("Laura", result.get(0).getName());

        verify(monitorRepository, times(1)).findAll();
    }

    @Test
    public void testAddMonitor() {
        MonitorInDto inputDto = new MonitorInDto("Laura", "12345678A", LocalDate.now(), 1500f, true, "Yoga");

        Monitor mappedMonitor = new Monitor();
        mappedMonitor.setName("Laura");
        mappedMonitor.setDni("12345678A");

        Monitor savedMonitor = new Monitor(10L, "Laura", "12345678A", LocalDate.now(), 1500f, true, "Yoga");

        MonitorOutDto outputDto = new MonitorOutDto(10L, "Laura", "12345678A", "Yoga", true, LocalDate.now());

        when(modelMapper.map(inputDto, Monitor.class)).thenReturn(mappedMonitor);
        when(monitorRepository.save(any(Monitor.class))).thenReturn(savedMonitor);
        when(modelMapper.map(savedMonitor, MonitorOutDto.class)).thenReturn(outputDto);

        MonitorOutDto result = monitorService.addMonitor(inputDto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Laura", result.getName());

        verify(monitorRepository, times(1)).save(any(Monitor.class));
    }
}
