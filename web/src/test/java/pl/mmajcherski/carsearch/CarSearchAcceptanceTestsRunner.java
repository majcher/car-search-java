package pl.mmajcherski.carsearch;

import com.google.common.util.concurrent.MoreExecutors;
import org.jbehave.core.Embeddable;
import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;

@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure(using = SeleniumConfiguration.class, pendingStepStrategy = FailingUponPendingStep.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true,
		ignoreFailureInView = false, storyTimeoutInSecs = 6000, threads = 1)
@UsingSpring(resources = "pl.mmajcherski.carsearch.spring.TestConfiguration")
public class CarSearchAcceptanceTestsRunner extends InjectableEmbedder {

	private final SeleniumContext seleniumContext = new SeleniumContext();
	private final ContextView contextView = new LocalFrameContextView().sized(500, 100);
	private final Class<? extends Embeddable> embeddableClass = this.getClass();

	@Test
	public void run() {
		Embedder embedder = injectedEmbedder();
		embedder.useExecutorService(MoreExecutors.sameThreadExecutor());

		Configuration configuration = embedder.configuration();
		if (!(configuration instanceof SeleniumConfiguration)) {
			throw new IllegalStateException();
		}

		SeleniumConfiguration seleniumConfiguration = (SeleniumConfiguration) configuration;
		seleniumConfiguration.useSeleniumContext(seleniumContext)
			.useStepMonitor(new SeleniumStepMonitor(contextView, seleniumContext, new SilentStepMonitor()))
			.useStoryLoader(new LoadFromClasspath(embeddableClass))
			.useStoryReporterBuilder(new StoryReporterBuilder()
					.withCodeLocation(codeLocationFromClass(embeddableClass))
					.withDefaultFormats()
					.withFormats(CONSOLE, TXT, HTML, XML));

		injectedEmbedder().runStoriesAsPaths(storyPaths());
	}

	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(),
				asList("**/*.story"), null);
	}

}
