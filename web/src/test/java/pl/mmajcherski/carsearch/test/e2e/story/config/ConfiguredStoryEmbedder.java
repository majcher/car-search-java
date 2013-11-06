package pl.mmajcherski.carsearch.test.e2e.story.config;

import com.google.common.util.concurrent.MoreExecutors;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.*;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;

public class ConfiguredStoryEmbedder extends Embedder {

	private final SeleniumContext seleniumContext = new SeleniumContext();
	private final ContextView contextView = new LocalFrameContextView().sized(500, 100);

	private Object[] stepsInstances;

	public ConfiguredStoryEmbedder(Object... stepsInstances) {
		this.stepsInstances = stepsInstances;

		this.useExecutorService(MoreExecutors.sameThreadExecutor());
	}

	@Override
	public Configuration configuration() {
		final SeleniumConfiguration configuration = new SeleniumConfiguration();

		configuration.useSeleniumContext(seleniumContext)
				.useStepMonitor(new SeleniumStepMonitor(contextView, seleniumContext, new SilentStepMonitor()))
				.useStoryLoader(new LoadFromClasspath(this.getClass()))
				.usePendingStepStrategy(new FailingUponPendingStep())
				.useStoryReporterBuilder(new StoryReporterBuilder()
						.withCodeLocation(codeLocationFromClass(this.getClass()))
						.withDefaultFormats()
						.withFormats(CONSOLE, TXT, HTML, XML, new SeleniumContextStoryReporterFormat(seleniumContext)));

		return configuration;
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), stepsInstances);
	}

}
