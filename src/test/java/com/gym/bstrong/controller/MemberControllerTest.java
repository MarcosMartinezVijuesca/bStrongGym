package com.gym.bstrong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.bstrong.dto.MemberInDto;
import com.gym.bstrong.dto.MemberOutDto;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.service.MemberService;
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

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllMembers_ShouldReturn200AndList() throws Exception {
        MemberOutDto outDto = new MemberOutDto();
        outDto.setFirstName("Test");

        when(memberService.findAll(null, null, null)).thenReturn(List.of(outDto));

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value("Test"));
    }

    @Test
    void getMemberById_ShouldReturn200_WhenExists() throws Exception {
        MemberOutDto outDto = new MemberOutDto();
        outDto.setId(1L);
        when(memberService.findById(1L)).thenReturn(outDto);

        mockMvc.perform(get("/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getMemberById_ShouldReturn404_WhenNotFound() throws Exception {
        when(memberService.findById(99L)).thenThrow(new MemberNotFoundException());

        mockMvc.perform(get("/members/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addMember_ShouldReturn201_WhenValid() throws Exception {
        MemberInDto input = new MemberInDto("Juan", "Gomez", LocalDate.now(), true, 78f, "juan@email.com");
        MemberOutDto output = new MemberOutDto();
        output.setId(10L);
        output.setFirstName("Juan");

        when(memberService.addMember(any(MemberInDto.class))).thenReturn(output);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }

    @Test
    void addMember_ShouldReturn400_WhenInvalidData() throws Exception {
        MemberInDto invalidInput = new MemberInDto();
        invalidInput.setWeight(80f);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyMember_ShouldReturn200_WhenExists() throws Exception {
        MemberInDto input = new MemberInDto("Juan", "Cambio", LocalDate.now(), true, 80f, "juan@mail.com");
        MemberOutDto output = new MemberOutDto();
        output.setId(1L);
        output.setFirstName("Juan");
        output.setLastName("Cambio");

        when(memberService.modifyMember(eq(1L), any(MemberInDto.class))).thenReturn(output);

        mockMvc.perform(put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Cambio"));
    }

    @Test
    void modifyMember_ShouldReturn400_WhenInvalidData() throws Exception {
        MemberInDto invalidInput = new MemberInDto(); // Nombre null

        mockMvc.perform(put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void modifyMember_ShouldReturn404_WhenNotFound() throws Exception {
        MemberInDto input = new MemberInDto("Juan", "Perez", LocalDate.now(), true, 80f, "juan@mail.com");

        when(memberService.modifyMember(eq(99L), any(MemberInDto.class)))
                .thenThrow(new MemberNotFoundException());

        mockMvc.perform(put("/members/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMember_ShouldReturn204_WhenExists() throws Exception {

        mockMvc.perform(delete("/members/1"))
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    void deleteMember_ShouldReturn404_WhenNotFound() throws Exception {
        // doThrow para métodos void
        doThrow(new MemberNotFoundException()).when(memberService).deleteMember(99L);

        mockMvc.perform(delete("/members/99"))
                .andExpect(status().isNotFound());
    }
}
