package com.sales_record.sales_record.config;

import com.sales_record.sales_record.dto.SalesDetailDto;
import com.sales_record.sales_record.entity.SalesSummary;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        mapper.typeMap(SalesSummary.class, SalesDetailDto.class).addMappings(modelmapper -> {
            modelmapper.map(SalesSummary::getId, SalesDetailDto::setSummaryId);
        });
        return mapper;
    }
}
