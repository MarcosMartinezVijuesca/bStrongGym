package com.gym.bstrong.service;

import com.gym.bstrong.domain.Activity;
import com.gym.bstrong.domain.Booking;
import com.gym.bstrong.domain.Member;
import com.gym.bstrong.dto.BookingInDto;
import com.gym.bstrong.dto.BookingOutDto;
import com.gym.bstrong.exception.ActivityNotFoundException;
import com.gym.bstrong.exception.BookingNotFoundException;
import com.gym.bstrong.exception.MemberNotFoundException;
import com.gym.bstrong.repository.ActivityRepository;
import com.gym.bstrong.repository.BookingRepository;
import com.gym.bstrong.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<BookingOutDto> findAll(Boolean attended, Long memberId, Long activityId) {
        List<Booking> bookings;
        if (attended == null && memberId == null && activityId == null) {
            bookings = bookingRepository.findAll();
        } else {
            bookings = bookingRepository.findByFilters(attended, memberId, activityId);
        }
        return modelMapper.map(bookings, new TypeToken<List<BookingOutDto>>() {}.getType());
    }

    public BookingOutDto findById(long id) throws BookingNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
        return modelMapper.map(booking, BookingOutDto.class);
    }

    public BookingOutDto addBooking(BookingInDto bookingInDto) throws MemberNotFoundException, ActivityNotFoundException {
        Booking booking = modelMapper.map(bookingInDto, Booking.class);

        Member member = memberRepository.findById(bookingInDto.getMemberId())
                .orElseThrow(MemberNotFoundException::new);
        Activity activity = activityRepository.findById(bookingInDto.getActivityId())
                .orElseThrow(ActivityNotFoundException::new);

        booking.setMember(member);
        booking.setActivity(activity);

        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingOutDto.class);
    }

    public BookingOutDto modifyBooking(long id, BookingInDto bookingInDto) throws BookingNotFoundException, MemberNotFoundException, ActivityNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);

        modelMapper.map(bookingInDto, booking);
        booking.setId(id);

        if (booking.getMember().getId() != bookingInDto.getMemberId()) {
            Member member = memberRepository.findById(bookingInDto.getMemberId())
                    .orElseThrow(MemberNotFoundException::new);
            booking.setMember(member);
        }
        if (booking.getActivity().getId() != bookingInDto.getActivityId()) {
            Activity activity = activityRepository.findById(bookingInDto.getActivityId())
                    .orElseThrow(ActivityNotFoundException::new);
            booking.setActivity(activity);
        }

        Booking modifiedBooking = bookingRepository.save(booking);
        return modelMapper.map(modifiedBooking, BookingOutDto.class);
    }

    public void deleteBooking(long id) throws BookingNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
        bookingRepository.delete(booking);
    }
}