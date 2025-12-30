package com.gym.bstrong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.bstrong.dto.ActivityInDto;
import com.gym.bstrong.dto.ActivityOutDto;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.MonitorNotFoundException;
import com.gym.bstrong.service.ActivityService;
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

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ActivityService activityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllActivities_ShouldReturn200AndList() throws Exception {
        ActivityOutDto outDto = new ActivityOutDto();
        outDto.setName("Zumba");
        outDto.setMonitorName("Laura");

        when(activityService.findAll(null, null, null)).thenReturn(List.of(outDto));

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Zumba"));
    }

    @Test
    void getActivityById_ShouldReturn200_WhenExists() throws Exception {
        ActivityOutDto outDto = new ActivityOutDto();
        outDto.setId(1L);
        outDto.setName("Pilates");

        when(activityService.findById(1L)).thenReturn(outDto);

        mockMvc.perform(get("/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pilates"));
    }

    @Test
    void getActivityById_ShouldReturn404_WhenNotFound() throws Exception {
        when(activityService.findById(99L)).thenThrow(new ActivityNotFoundException());

        mockMvc.perform(get("/activities/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addActivity_ShouldReturn201_WhenValid() throws Exception {
        ActivityInDto input = new ActivityInDto();
        input.setName("Crossfit");
        input.setCapacity(15);
        input.setDurationMinutes(60);
        input.setPricePerSession(10.0f);
        input.setMonitorId(1L);

        ActivityOutDto output = new ActivityOutDto();
        output.setId(5L);
        output.setName("Crossfit");

        when(activityService.addActivity(any(ActivityInDto.class))).thenReturn(output);

        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Crossfit"));
    }

    @Test
    void addActivity_ShouldReturn400_WhenInvalidData() throws Exception {
        ActivityInDto invalidInput = new ActivityInDto();
        invalidInput.setName("");
        invalidInput.setCapacity(0);

        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyActivity_ShouldReturn200_WhenExists() throws Exception {
        ActivityInDto input = new ActivityInDto();
        input.setName("Crossfit Avanzado");
        input.setCapacity(20);
        input.setDurationMinutes(60);
        input.setMonitorId(1L);

        ActivityOutDto output = new ActivityOutDto();
        output.setId(1L);
        output.setName("Crossfit Avanzado");

        when(activityService.modifyActivity(eq(1L), any(ActivityInDto.class))).thenReturn(output);

        mockMvc.perform(put("/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Crossfit Avanzado"));
    }

    @Test
    void modifyActivity_ShouldReturn400_WhenInvalidData() throws Exception {
        ActivityInDto invalidInput = new ActivityInDto();
        invalidInput.setName("");

        mockMvc.perform(put("/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyActivity_ShouldReturn404_WhenNotFound() throws Exception {
        ActivityInDto input = new ActivityInDto();
        input.setName("Yoga");
        input.setCapacity(10);
        input.setDurationMinutes(45);
        input.setMonitorId(1L);

        when(activityService.modifyActivity(eq(99L), any(ActivityInDto.class)))
                .thenThrow(new ActivityNotFoundException());

        mockMvc.perform(put("/activities/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteActivity_ShouldReturn204_WhenExists() throws Exception {
        mockMvc.perform(delete("/activities/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteActivity_ShouldReturn404_WhenNotFound() throws Exception {
        doThrow(new ActivityNotFoundException()).when(activityService).deleteActivity(99L);

        mockMvc.perform(delete("/activities/99"))
                .andExpect(status().isNotFound());
    }
}