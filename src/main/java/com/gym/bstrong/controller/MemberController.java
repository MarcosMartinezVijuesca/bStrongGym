package com.gym.bstrong.controller;

import com.gym.bstrong.dto.MemberInDto;
import com.gym.bstrong.dto.MemberOutDto;
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
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    private final Logger logger = LoggerFactory.getLogger(MemberController.class);


    @GetMapping
    public ResponseEntity<List<MemberOutDto>> getAllMembers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Boolean active) {

        logger.info("Petición recibida: GET /members");
        List<MemberOutDto> members = memberService.findAll(firstName, lastName, active);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberOutDto> getMember(@PathVariable long id) throws MemberNotFoundException {
        logger.info("Petición recibida: GET /members/" + id);
        MemberOutDto member = memberService.findById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<MemberOutDto> addMember(@Valid @RequestBody MemberInDto memberInDto) {
        logger.info("Petición recibida: POST /members");
        MemberOutDto newMember = memberService.addMember(memberInDto);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MemberOutDto> modifyMember(@PathVariable long id, @Valid @RequestBody MemberInDto memberInDto) throws MemberNotFoundException {
        logger.info("Petición recibida: PUT /members/" + id);
        MemberOutDto modifiedMember = memberService.modifyMember(id, memberInDto);
        return new ResponseEntity<>(modifiedMember, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable long id) throws MemberNotFoundException {
        logger.info("Petición recibida: DELETE /members/" + id);
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}