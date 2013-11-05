package pl.mmajcherski.carsearch.story.config;

import org.jbehave.core.reporters.*;
import org.jbehave.web.selenium.SeleniumContext;

public class SeleniumContextStoryReporterFormat extends Format {

	private SeleniumContext context;

	public SeleniumContextStoryReporterFormat(SeleniumContext context) {
		super("SeleniumContextStoryReporterFormat");

		this.context = context;
	}

	@Override
	public StoryReporter createStoryReporter(FilePrintStreamFactory factory, StoryReporterBuilder storyReporterBuilder) {
		return new SeleniumContextStoryReporter();
	}

	private class SeleniumContextStoryReporter extends NullStoryReporter {
		@Override
		public void beforeScenario(String scenarioTitle) {
			context.setCurrentScenario(scenarioTitle);
		}
	}
}
