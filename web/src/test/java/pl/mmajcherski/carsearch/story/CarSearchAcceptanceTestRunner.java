package pl.mmajcherski.carsearch.story;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.web.selenium.WebDriverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.spring.TestConfiguration;
import pl.mmajcherski.carsearch.story.config.ConfiguredStoryEmbedder;
import pl.mmajcherski.carsearch.story.step.CarSearchWebSteps;

import javax.annotation.PostConstruct;
import java.util.Collections;

@DirtiesContext
@ContextConfiguration(classes = TestConfiguration.class)
public class CarSearchAcceptanceTestRunner extends AbstractTestNGSpringContextTests {

	private Embedder embedder;

	@Autowired private CarSearchWebSteps carSearchWebSteps;
	@Autowired private WebDriverProvider webDriverProvider;

	@PostConstruct
	public void initEmbedder() {
		this.embedder = new ConfiguredStoryEmbedder(carSearchWebSteps);
	}

	@BeforeClass
	public void initializeWebDriver() {
		webDriverProvider.initialize();
	}

	@DataProvider(name = "Acceptance test story provider")
	public Object[][] provideAcceptanceTestsStory() {
		return new Object[][]{
				{"carSearchWithMoreThanOneResult.story"},
				{"carSearchByMake.story"},
				{"carSearchByModel.story"},
				{"carSearchByMakeAndModel.story"},
				{"carSearchByColor.story"}
		};
	}

	@Test(dataProvider = "Acceptance test story provider")
	public void shouldPassAllAcceptanceTestsStories(String storyName) {
		embedder.runStoriesAsPaths(Collections.singletonList(storyName));
	}

	@AfterClass
	public void closeWebDriver() {
		webDriverProvider.end();
	}

}
