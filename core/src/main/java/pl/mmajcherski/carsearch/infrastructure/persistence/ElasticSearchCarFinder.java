package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.interfaces.finder.CarFinder;
import pl.mmajcherski.carsearch.interfaces.finder.CarSearchCriteria;
import pl.mmajcherski.cqrs.annotation.Finder;
import pl.mmajcherski.cqrs.query.PaginatedResult;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.search.sort.SortBuilders.fieldSort;
import static pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchIndex.CAR;

@Finder
public class ElasticSearchCarFinder implements CarFinder {

	private Client esClient;
	private CarJsonConverter carJsonConverter;

	@Autowired
	public ElasticSearchCarFinder(Client esClient, CarJsonConverter carJsonConverter) {
		this.esClient = esClient;
		this.carJsonConverter = carJsonConverter;
	}

	@Override
	public PaginatedResult<Car> findCars(CarSearchCriteria criteria) {
		String phrase = criteria.getText();

		final QueryBuilder q = (phrase == null)
				? QueryBuilders.matchAllQuery()
				: QueryBuilders.queryString(phrase.toLowerCase())
					.field("make")
					.field("model")
					.analyzer("whitespace")
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);

		CountResponse countResponse = esClient.prepareCount(CAR.getIndex()).setTypes(CAR.getType())
				.setQuery(q)
				.execute()
				.actionGet();

		long totalCount = countResponse.getCount();

		SearchResponse searchResponse = esClient.prepareSearch(CAR.getIndex()).setTypes(CAR.getType())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(q)
				.addSort(fieldSort("id.value").order(SortOrder.ASC))
				.setFrom(criteria.getPageNumber() * criteria.getPageSize())
				.setSize(criteria.getPageSize())
				.execute()
				.actionGet();

		List<Car> cars = new ArrayList<>();
		for (SearchHit hit : searchResponse.getHits()) {
			String json = hit.getSourceAsString();
			cars.add(carJsonConverter.fromJson(json));
		}

		return new PaginatedResult<Car>(cars, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
	}

}
