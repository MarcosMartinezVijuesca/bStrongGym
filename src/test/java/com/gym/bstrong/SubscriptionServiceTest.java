package com.gym.bstrong;

import com.gym.bstrong.domain.Member;
import com.gym.bstrong.domain.Subscription;
import com.gym.bstrong.dto.SubscriptionInDto;
import com.gym.bstrong.dto.SubscriptionOutDto;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.repository.MemberRepository;
import com.gym.bstrong.repository.SubscriptionRepository;
import com.gym.bstrong.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        Subscription sub1 = new Subscription(1L, "MONTHLY", LocalDate.now(), LocalDate.now().plusMonths(1), 30f, true, true, new Member());
        Subscription sub2 = new Subscription(2L, "ANNUAL", LocalDate.now(), LocalDate.now().plusYears(1), 300f, true, false, new Member());
        List<Subscription> mockSubs = List.of(sub1, sub2);

        SubscriptionOutDto dto1 = new SubscriptionOutDto(1L, "MONTHLY", LocalDate.now(), LocalDate.now().plusMonths(1), 30f,  true, true, "Juan");
        SubscriptionOutDto dto2 = new SubscriptionOutDto(2L, "ANNUAL", LocalDate.now(), LocalDate.now().plusYears(1), 300f, true, false, "Ana");
        List<SubscriptionOutDto> mockDtos = List.of(dto1, dto2);

        when(subscriptionRepository.findAll()).thenReturn(mockSubs);
        when(modelMapper.map(mockSubs, new TypeToken<List<SubscriptionOutDto>>() {}.getType())).thenReturn(mockDtos);

        List<SubscriptionOutDto> result = subscriptionService.findAll(null, null, null);

        assertEquals(2, result.size());
        assertEquals("MONTHLY", result.get(0).getType());

        verify(subscriptionRepository, times(1)).findAll();
    }

    @Test
    public void testAddSubscription() throws MemberNotFoundException {
        SubscriptionInDto inputDto = new SubscriptionInDto("MONTHLY", LocalDate.now(), LocalDate.now().plusMonths(1), 30f, true, true, 1L);

        Member member = new Member();
        member.setId(1L);
        member.setFirstName("Juan");

        Subscription mappedSub = new Subscription();
        mappedSub.setType("MONTHLY");

        Subscription savedSub = new Subscription(10L, "MONTHLY", LocalDate.now(), LocalDate.now().plusMonths(1), 30f, true, true, member);

        SubscriptionOutDto outputDto = new SubscriptionOutDto(10L, "MONTHLY", LocalDate.now(), LocalDate.now().plusMonths(1), 30f, true, true, "Juan");

        when(modelMapper.map(inputDto, Subscription.class)).thenReturn(mappedSub); // 1er mapeo
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));       // Búsqueda manual de socio
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(savedSub); // Guardado
        when(modelMapper.map(savedSub, SubscriptionOutDto.class)).thenReturn(outputDto); // 2do mapeo

        SubscriptionOutDto result = subscriptionService.addSubscription(inputDto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("MONTHLY", result.getType());
        assertEquals("Juan", result.getMemberName());

        verify(memberRepository, times(1)).findById(1L);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }
}