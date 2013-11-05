package pl.mmajcherski.carsearch;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.web.selenium.WebDriverSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.infrastructure.test.BaseIntegrationTest;
import pl.mmajcherski.carsearch.spring.TestConfiguration;
import pl.mmajcherski.carsearch.step.CarSearchWebSteps;

import java.util.Collections;

@ContextConfiguration(classes = TestConfiguration.class)
public class CarSearchAcceptanceTestRunner extends BaseIntegrationTest {

	@Autowired private CarSearchWebSteps carSearchWebSteps;
	@Autowired private WebDriverSteps webDriverSteps;

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
		runStory(storyName, webDriverSteps, carSearchWebSteps);
	}

	private void runStory(String storyName, Object... storySteps) {
		final Embedder embedder = new ConfiguredStoryEmbedder(storySteps);
		embedder.runStoriesAsPaths(Collections.singletonList(storyName));
	}

}
