package pl.mmajcherski.carsearch.spring;

import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mmajcherski.carsearch.infrastructure.spring.CoreConfiguration;

@Configuration
public class TestConfiguration extends CoreConfiguration {

	@Bean
	public WebDriverProvider webDriverProvider() {
		return new PropertyWebDriverProvider();
	}

}
