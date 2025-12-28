package com.gym.bstrong.service;

import com.gym.bstrong.domain.Member;
import com.gym.bstrong.dto.MemberInDto;
import com.gym.bstrong.dto.MemberOutDto;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<MemberOutDto> findAll(String firstName, String lastName, Boolean active) {
        List<Member> members;

        if (firstName == null && lastName == null && active == null) {
            members = memberRepository.findAll();
        } else {
            members = memberRepository.findByFilters(firstName, lastName, active);
        }

        return modelMapper.map(members, new TypeToken<List<MemberOutDto>>() {}.getType());
    }

     public MemberOutDto findById(long id) throws MemberNotFoundException {
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        return modelMapper.map(member, MemberOutDto.class);
    }

    public MemberOutDto addMember(MemberInDto memberInDto) {
        Member member = modelMapper.map(memberInDto, Member.class);

        member.setRegistrationDate(LocalDate.now());

        Member newMember = memberRepository.save(member);
        return modelMapper.map(newMember, MemberOutDto.class);
    }

    public MemberOutDto modifyMember(long id, MemberInDto memberInDto) throws MemberNotFoundException {

        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);


        modelMapper.map(memberInDto, member);
        member.setId(id);

        Member modifiedMember = memberRepository.save(member);
        return modelMapper.map(modifiedMember, MemberOutDto.class);
    }

    public void deleteMember(long id) throws MemberNotFoundException {
        Member member = memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }

}