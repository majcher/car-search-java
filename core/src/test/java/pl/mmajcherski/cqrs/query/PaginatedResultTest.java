package pl.mmajcherski.cqrs.query;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class PaginatedResultTest {

	private List<String> resultList;
	private PaginatedResult<String> results;

	@BeforeClass
	public void init() {
		resultList = new ArrayList<>();
		resultList.add("first");
		resultList.add("second");

		results = new PaginatedResult<>(resultList, 2, 100, 301);
	}

	@Test
	public void shouldContainItems() {
		assertThat(results.getItems()).isEqualTo(resultList);
	}

	@Test
	public void shouldProvidePageNumber() {
		assertThat(results.getPageNumber()).isEqualTo(2);
	}

	@Test
	public void shouldProvidePageSize() {
		assertThat(results.getPageSize()).isEqualTo(100);
	}

	@Test
	public void shouldProvideTotalItemsCount() {
		assertThat(results.getTotalItemsCount()).isEqualTo(301);
	}

	@Test
	public void shouldProvidePagesCount() {
		assertThat(results.getPagesCount()).isEqualTo(4);
	}

}
