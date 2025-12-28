package com.gym.bstrong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.bstrong.dto.MonitorInDto;
import com.gym.bstrong.dto.MonitorOutDto;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.service.MonitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MonitorController.class)
public class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Si te da error, usa @MockBean
    private MonitorService monitorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllMonitors_ShouldReturn200AndList() throws Exception {
        MonitorOutDto outDto = MonitorOutDto.builder().name("Laura").build();

        when(monitorService.findAll(null, null, null)).thenReturn(List.of(outDto));

        mockMvc.perform(get("/monitors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Laura"));
    }


    @Test
    void getMonitorById_ShouldReturn200_WhenExists() throws Exception {
        MonitorOutDto outDto = MonitorOutDto.builder().id(1L).name("Laura").build();
        when(monitorService.findById(1L)).thenReturn(outDto);

        mockMvc.perform(get("/monitors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getMonitorById_ShouldReturn404_WhenNotFound() throws Exception {
        when(monitorService.findById(99L)).thenThrow(new MonitorNotFoundException());

        mockMvc.perform(get("/monitors/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addMonitor_ShouldReturn201_WhenValid() throws Exception {
        MonitorInDto input = MonitorInDto.builder()
                .name("Laura")
                .dni("12345678A")
                .salary(1500f)
                .build();

        MonitorOutDto output = MonitorOutDto.builder().id(5L).name("Laura").build();

        when(monitorService.addMonitor(any(MonitorInDto.class))).thenReturn(output);

        mockMvc.perform(post("/monitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laura"));
    }

    @Test
    void addMonitor_ShouldReturn400_WhenInvalidData() throws Exception {
        MonitorInDto invalidInput = MonitorInDto.builder()
                .name("Laura")
                .dni("123") // DNI inválido (muy corto)
                .build();

        mockMvc.perform(post("/monitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }
}
