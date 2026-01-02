package com.gym.bstrong.config;

import com.gym.bstrong.domain.Activity;
import com.gym.bstrong.domain.Booking;
import com.gym.bstrong.dto.ActivityOutDto;
import com.gym.bstrong.dto.BookingOutDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(Activity.class, ActivityOutDto.class).addMappings(mapper ->
                mapper.map(src -> src.getMonitor().getName(), ActivityOutDto::setMonitorName)
                );

        modelMapper.typeMap(Booking.class, BookingOutDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getMember().getFirstName(), BookingOutDto::setMemberName);
            mapper.map(src -> src.getActivity().getName(), BookingOutDto::setActivityName);
        });

        return modelMapper;
    }
}
