package com.gym.bstrong.controller;

import com.gym.bstrong.dto.DeleteResponseDto;
import com.gym.bstrong.dto.MemberInDtoV2;
import com.gym.bstrong.dto.MemberOutDtoV2;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.service.MemberService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/members")
public class MemberControllerV2 {

    @Autowired
    private MemberService memberService;

    private final Logger logger = LoggerFactory.getLogger(MemberControllerV2.class);

    @GetMapping
    public ResponseEntity<List<MemberOutDtoV2>> getAllMembersV2(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Boolean active) {

        logger.info("GET /v2/members");
        return ResponseEntity.ok(memberService.findAllV2(firstName, lastName, active));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberOutDtoV2> getMemberV2(@PathVariable long id) throws MemberNotFoundException {
        logger.info("GET /v2/members/{}", id);
        return ResponseEntity.ok(memberService.findByIdV2(id));
    }

    @PostMapping
    public ResponseEntity<MemberOutDtoV2> addMemberV2(@Valid @RequestBody MemberInDtoV2 memberInDtoV2) {
        logger.info("POST /v2/members");
        return new ResponseEntity<>(memberService.addMemberV2(memberInDtoV2), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDto> deleteMemberV2(@PathVariable long id) throws MemberNotFoundException {
        logger.info("DELETE /v2/members/{}", id);
        memberService.deleteMember(id);
        DeleteResponseDto response = DeleteResponseDto.builder()
                .message("Member deleted successfully")
                .id(id)
                .build();
        return ResponseEntity.ok(response);
    }
}
