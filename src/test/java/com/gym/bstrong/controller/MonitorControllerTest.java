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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MonitorController.class)
public class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
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

    @Test
    void modifyMonitor_ShouldReturn200_WhenExists() throws Exception {
        // Datos de entrada (lo que enviamos)
        MonitorInDto input = MonitorInDto.builder()
                .name("Laura Modificada")
                .dni("12345678A")
                .salary(1600f)
                .available(true)
                .specialty("Pilates")
                .build();

        MonitorOutDto output = MonitorOutDto.builder()
                .id(1L)
                .name("Laura Modificada")
                .dni("12345678A")
                .build();

        when(monitorService.modifyMonitor(eq(1L), any(MonitorInDto.class))).thenReturn(output);

        mockMvc.perform(put("/monitors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laura Modificada"));
    }

    @Test
    void modifyMonitor_ShouldReturn400_WhenInvalidData() throws Exception {
        // DNI incorrecto para forzar el error
        MonitorInDto invalidInput = MonitorInDto.builder()
                .name("Laura")
                .dni("123")
                .build();

        mockMvc.perform(put("/monitors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyMonitor_ShouldReturn404_WhenNotFound() throws Exception {
        MonitorInDto input = MonitorInDto.builder()
                .name("Laura")
                .dni("12345678A")
                .build();

        when(monitorService.modifyMonitor(eq(99L), any(MonitorInDto.class)))
                .thenThrow(new MonitorNotFoundException());

        mockMvc.perform(put("/monitors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMonitor_ShouldReturn204_WhenExists() throws Exception {
        // doNothing().when(monitorService).deleteMonitor(1L);

        mockMvc.perform(delete("/monitors/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMonitor_ShouldReturn404_WhenNotFound() throws Exception {
        doThrow(new MonitorNotFoundException()).when(monitorService).deleteMonitor(99L);

        mockMvc.perform(delete("/monitors/99"))
                .andExpect(status().isNotFound());
    }
}
