package com.gym.bstrong;

import com.gym.bstrong.domain.Activity;
import com.gym.bstrong.domain.Booking;
import com.gym.bstrong.domain.Member;
import com.gym.bstrong.dto.BookingInDto;
import com.gym.bstrong.dto.BookingOutDto;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.repository.ActivityRepository;
import com.gym.bstrong.repository.BookingRepository;
import com.gym.bstrong.repository.MemberRepository;
import com.gym.bstrong.service.BookingService;
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
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAll() {
        Booking booking1 = new Booking(1L, LocalDate.now(), true, 5, "Genial", 10f, new Member(), new Activity());
        Booking booking2 = new Booking(2L, LocalDate.now(), false, null, null, 10f, new Member(), new Activity());
        List<Booking> mockBookings = List.of(booking1, booking2);

        BookingOutDto dto1 = new BookingOutDto(1L, LocalDate.now(), true, 5, "Genial", 10f, "Juan", "Yoga");
        BookingOutDto dto2 = new BookingOutDto(2L, LocalDate.now(), false, null, null, 10f, "Ana", "Zumba");
        List<BookingOutDto> mockDtos = List.of(dto1, dto2);

        when(bookingRepository.findAll()).thenReturn(mockBookings);
        when(modelMapper.map(mockBookings, new TypeToken<List<BookingOutDto>>() {}.getType())).thenReturn(mockDtos);

        List<BookingOutDto> result = bookingService.findAll(null, null, null);

        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getMemberName());

        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void testAddBooking() throws MemberNotFoundException, ActivityNotFoundException {
        BookingInDto inputDto = new BookingInDto(LocalDate.now(), true, 5, "Top", 10f, 1L, 2L);

        Member member = new Member();
        member.setId(1L);
        member.setFirstName("Juan");

        Activity activity = new Activity();
        activity.setId(2L);
        activity.setName("Yoga");

        Booking mappedBooking = new Booking();
        mappedBooking.setPricePaid(10f);

        Booking savedBooking = new Booking(100L, LocalDate.now(), true, 5, "Top", 10f, member, activity);

        BookingOutDto outputDto = new BookingOutDto(100L, LocalDate.now(), true, 5, "Top", 10f, "Juan", "Yoga");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(activityRepository.findById(2L)).thenReturn(Optional.of(activity));

        when(modelMapper.map(inputDto, Booking.class)).thenReturn(mappedBooking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);
        when(modelMapper.map(savedBooking, BookingOutDto.class)).thenReturn(outputDto);

        BookingOutDto result = bookingService.addBooking(inputDto);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Juan", result.getMemberName());
        assertEquals("Yoga", result.getActivityName());

        verify(memberRepository, times(1)).findById(1L);
        verify(activityRepository, times(1)).findById(2L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
}