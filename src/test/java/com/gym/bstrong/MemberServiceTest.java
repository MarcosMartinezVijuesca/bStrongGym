package com.gym.bstrong;

import com.gym.bstrong.domain.Member;
import com.gym.bstrong.dto.MemberInDto;
import com.gym.bstrong.dto.MemberOutDto;
import com.gym.bstrong.repository.MemberRepository;
import com.gym.bstrong.service.MemberService;
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
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        Member member1 = new Member(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),LocalDate.now(), true, 80.5f, "juan@mail.com", null, null);
        Member member2 = new Member(2L, "Ana", "Gómez", LocalDate.of(1995, 5, 15), LocalDate.now(), true, 60.0f, "ana@mail.com", null, null);
        List<Member> mockMembers = List.of(member1, member2);

        MemberOutDto dto1 = new MemberOutDto(1L, "Juan", "Pérez", true, null);
        MemberOutDto dto2 = new MemberOutDto(2L, "Ana", "Gómez", true, null);
        List<MemberOutDto> mockDtos = List.of(dto1, dto2);

        when(memberRepository.findAll()).thenReturn(mockMembers);
        when(modelMapper.map(mockMembers, new TypeToken<List<MemberOutDto>>() {}.getType())).thenReturn(mockDtos);

        List<MemberOutDto> result = memberService.findAll(null, null, null);

        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getFirstName());

        verify(memberRepository, times(1)).findAll();
    }

    @Test
    public void testAddMember() {

        MemberInDto inputDto = new MemberInDto("Juan", "Perez", LocalDate.of(1990, 1, 1), true, 80.5f, "juan@email.com");
        Member mappedMember = new Member();
        mappedMember.setFirstName("Carlos");

        Member savedMember = new Member(5L, "Carlos", "Ruiz", LocalDate.now(), LocalDate.now(), true, 80f, "carlos@email.com", null, null);
        MemberOutDto outputDto = new MemberOutDto(5L, "Carlos", "Ruiz", true, null);

        when(modelMapper.map(inputDto, Member.class)).thenReturn(mappedMember);
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);
        when(modelMapper.map(savedMember, MemberOutDto.class)).thenReturn(outputDto);

        MemberOutDto result = memberService.addMember(inputDto);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals("Carlos", result.getFirstName());

        verify(memberRepository, times(1)).save(any(Member.class));
    }
}