package com.gym.bstrong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.bstrong.dto.BookingInDto;
import com.gym.bstrong.dto.BookingOutDto;
import com.gym.bstrong.exception.BookingNotFoundException;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.service.BookingService;
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

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBookings_ShouldReturn200AndList() throws Exception {
        BookingOutDto outDto = new BookingOutDto();
        outDto.setId(1L);
        outDto.setMemberName("Juan");

        when(bookingService.findAll(null, null, null)).thenReturn(List.of(outDto));

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].memberName").value("Juan"));
    }

    @Test
    void getBookingById_ShouldReturn200_WhenExists() throws Exception {
        BookingOutDto outDto = new BookingOutDto();
        outDto.setId(1L);

        when(bookingService.findById(1L)).thenReturn(outDto);

        mockMvc.perform(get("/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getBookingById_ShouldReturn404_WhenNotFound() throws Exception {
        when(bookingService.findById(99L)).thenThrow(new BookingNotFoundException());

        mockMvc.perform(get("/bookings/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBooking_ShouldReturn201_WhenValid() throws Exception {
        BookingInDto input = new BookingInDto(LocalDate.now(), false, null, null, 10f, 1L, 2L);
        BookingOutDto output = new BookingOutDto();
        output.setId(10L);

        when(bookingService.addBooking(any(BookingInDto.class))).thenReturn(output);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void addBooking_ShouldReturn400_WhenInvalidData() throws Exception {
        BookingInDto invalidInput = new BookingInDto();

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addBooking_ShouldReturn404_WhenMemberNotFound() throws Exception {
        BookingInDto input = new BookingInDto(LocalDate.now(), false, null, null, 10f, 99L, 2L);

        when(bookingService.addBooking(any(BookingInDto.class)))
                .thenThrow(new MemberNotFoundException());

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void modifyBooking_ShouldReturn200_WhenExists() throws Exception {
        BookingInDto input = new BookingInDto(LocalDate.now(), true, 5, "Good", 10f, 1L, 2L);
        BookingOutDto output = new BookingOutDto();
        output.setId(1L);
        output.setReviewNote(5);

        when(bookingService.modifyBooking(eq(1L), any(BookingInDto.class))).thenReturn(output);

        mockMvc.perform(put("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewNote").value(5));
    }

    @Test
    void modifyBooking_ShouldReturn404_WhenNotFound() throws Exception {
        BookingInDto input = new BookingInDto(LocalDate.now(), true, 5, "Good", 10f, 1L, 2L);

        when(bookingService.modifyBooking(eq(99L), any(BookingInDto.class)))
                .thenThrow(new BookingNotFoundException());

        mockMvc.perform(put("/bookings/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBooking_ShouldReturn204_WhenExists() throws Exception {
        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBooking_ShouldReturn404_WhenNotFound() throws Exception {
        doThrow(new BookingNotFoundException()).when(bookingService).deleteBooking(99L);

        mockMvc.perform(delete("/bookings/99"))
                .andExpect(status().isNotFound());
    }
}