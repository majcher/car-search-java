package pl.mmajcherski.carsearch.infrastructure.spring;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(basePackages = {"pl.mmajcherski.carsearch"},
        excludeFilters = {@Filter(value = WebConfiguration.class, type = FilterType.ASSIGNABLE_TYPE)})
public class CoreConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
