package pl.mmajcherski.carsearch.infrastructure.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"pl.mmajcherski.carsearch"})
public class WebConfiguration {
}
