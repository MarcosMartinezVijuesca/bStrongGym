package com.gym.bstrong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.bstrong.dto.SubscriptionInDto;
import com.gym.bstrong.dto.SubscriptionOutDto;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.exception.SubscriptionNotFoundException;
import com.gym.bstrong.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionService subscriptionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSubscriptions_ShouldReturn200AndList() throws Exception {
        SubscriptionOutDto outDto = new SubscriptionOutDto();
        outDto.setType("MONTHLY");

        when(subscriptionService.findAll(null, null, null)).thenReturn(List.of(outDto));

        mockMvc.perform(get("/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].type").value("MONTHLY"));
    }

    @Test
    void getSubscriptionById_ShouldReturn200_WhenExists() throws Exception {
        SubscriptionOutDto outDto = new SubscriptionOutDto();
        outDto.setId(1L);
        when(subscriptionService.findById(1L)).thenReturn(outDto);

        mockMvc.perform(get("/subscriptions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getSubscriptionById_ShouldReturn404_WhenNotFound() throws Exception {
        when(subscriptionService.findById(99L)).thenThrow(new SubscriptionNotFoundException());

        mockMvc.perform(get("/subscriptions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addSubscription_ShouldReturn201_WhenValid() throws Exception {
        SubscriptionInDto input = new SubscriptionInDto("MONTHLY", LocalDate.now(), LocalDate.now().plusMonths(1), 30f, true, true, 1L);
        SubscriptionOutDto output = new SubscriptionOutDto();
        output.setId(10L);
        output.setType("MONTHLY");

        when(subscriptionService.addSubscription(any(SubscriptionInDto.class))).thenReturn(output);

        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("MONTHLY"));
    }

    @Test
    void addSubscription_ShouldReturn400_WhenInvalidData() throws Exception {
        SubscriptionInDto invalidInput = new SubscriptionInDto();

        mockMvc.perform(post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifySubscription_ShouldReturn200_WhenExists() throws Exception {
        SubscriptionInDto input = new SubscriptionInDto("ANNUAL", LocalDate.now(), LocalDate.now().plusYears(1), 300f, true, false, 1L);
        SubscriptionOutDto output = new SubscriptionOutDto();
        output.setId(1L);
        output.setType("ANNUAL");

        when(subscriptionService.modifySubscription(eq(1L), any(SubscriptionInDto.class))).thenReturn(output);

        mockMvc.perform(put("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("ANNUAL"));
    }

    @Test
    void modifySubscription_ShouldReturn400_WhenInvalidData() throws Exception {
        SubscriptionInDto invalidInput = new SubscriptionInDto(); // Objeto vacío inválido

        mockMvc.perform(put("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifySubscription_ShouldReturn404_WhenNotFound() throws Exception {
        SubscriptionInDto input = new SubscriptionInDto("MONTHLY", LocalDate.now(), LocalDate.now(), 30f, true, true, 1L);

        when(subscriptionService.modifySubscription(eq(99L), any(SubscriptionInDto.class)))
                .thenThrow(new SubscriptionNotFoundException());

        mockMvc.perform(put("/subscriptions/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSubscription_ShouldReturn204_WhenExists() throws Exception {
        mockMvc.perform(delete("/subscriptions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSubscription_ShouldReturn404_WhenNotFound() throws Exception {
        doThrow(new SubscriptionNotFoundException()).when(subscriptionService).deleteSubscription(99L);

        mockMvc.perform(delete("/subscriptions/99"))
                .andExpect(status().isNotFound());
    }
}