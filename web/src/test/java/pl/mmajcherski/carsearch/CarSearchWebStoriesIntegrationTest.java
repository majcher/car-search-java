package pl.mmajcherski.carsearch;

import com.google.common.util.concurrent.MoreExecutors;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.*;
import org.junit.runner.RunWith;
import pl.mmajcherski.carsearch.page.Pages;

import java.util.Arrays;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;

@RunWith(JUnitReportingRunner.class)
public class CarSearchWebStoriesIntegrationTest extends JUnitStories {

	private WebDriverProvider driverProvider = new PropertyWebDriverProvider();
	private WebDriverSteps lifecycleSteps = new PerStoriesWebDriverSteps(driverProvider);
	//private WebDriverSteps lifecycleSteps = new PerStoryWebDriverSteps(driverProvider);
	private Pages pages = new Pages(driverProvider);
	private SeleniumContext context = new SeleniumContext();
	private ContextView contextView = new LocalFrameContextView().sized(500, 100);

	public CarSearchWebStoriesIntegrationTest() {
		// If configuring lifecycle per-stories, you need to ensure that you a same-thread executor
		if ( lifecycleSteps instanceof PerStoriesWebDriverSteps ){
			configuredEmbedder().useExecutorService(MoreExecutors.sameThreadExecutor());
		}
	}

	@Override
	public Configuration configuration() {
		Class<? extends Embeddable> embeddableClass = this.getClass();
		return new SeleniumConfiguration()
				.useSeleniumContext(context)
				.useWebDriverProvider(driverProvider)
				.useStepMonitor(new SeleniumStepMonitor(contextView, context, new SilentStepMonitor()))
				.useStoryLoader(new LoadFromClasspath(embeddableClass))
				.useStoryReporterBuilder(new StoryReporterBuilder()
						.withCodeLocation(codeLocationFromClass(embeddableClass))
						.withDefaultFormats()
						.withFormats(CONSOLE, TXT, HTML, XML));
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		Configuration configuration = configuration();
		return new InstanceStepsFactory(configuration,
				new CarSearchWebSteps(pages),
				lifecycleSteps,
				new WebDriverScreenshotOnFailure(driverProvider, configuration.storyReporterBuilder()));
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(),
				Arrays.asList("**/*.story"), null);
	}

}
