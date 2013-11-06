package pl.mmajcherski.carsearch.test.e2e.story.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CarColorMakeModelStringParser {

	private static final Pattern PATTERN = Pattern.compile("([A-Z][\\w ]*) ([A-Z][\\w]*) ([A-Z][\\w]*)");

	private final Matcher matcher;

	private CarColorMakeModelStringParser(String text) {
		this.matcher = PATTERN.matcher(text);
		this.matcher.find();
	}

	public static CarColorMakeModelStringParser forText(String text) {
		return new CarColorMakeModelStringParser(text);
	}

	public String getColor() {
		return matcher.group(1);
	}

	public String getMake() {
		return matcher.group(2);
	}

	public String getModel() {
		return matcher.group(3);
	}

}
