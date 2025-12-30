package com.gym.bstrong.config;

import com.gym.bstrong.domain.Activity;
import com.gym.bstrong.dto.ActivityOutDto;
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

        return modelMapper;
    }
}
