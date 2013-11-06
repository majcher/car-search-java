package pl.mmajcherski.carsearch.domain.common.model;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class PagedResultTest {

	private List<String> resultList;
	private PagedResult<String> results;

	@BeforeClass
	public void init() {
		resultList = new ArrayList<>();
		resultList.add("first");
		resultList.add("second");

		results = new PagedResult.Builder<String>()
				.with(resultList).currentPage(2).pageSize(100).totalCount(301).build();
	}

	@Test
	public void shouldContainItems() {
		assertThat(results.getItems()).isEqualTo(resultList);
	}

	@Test
	public void shouldProvidePageNumber() {
		assertThat(results.getCurrentPage()).isEqualTo(2);
	}

	@Test
	public void shouldProvidePageSize() {
		assertThat(results.getPageSize()).isEqualTo(100);
	}

	@Test
	public void shouldProvideTotalItemsCount() {
		assertThat(results.getTotalCount()).isEqualTo(301);
	}

	@Test
	public void shouldProvidePagesCount() {
		assertThat(results.getPagesCount()).isEqualTo(4);
	}

}
