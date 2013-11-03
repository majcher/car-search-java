package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.search.sort.SortBuilders.fieldSort;
import static pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchIndex.CAR;

@Finder
public class ElasticSearchCarFinder implements CarFinder {

	private final Client client;
	private final CarJsonConverter carJsonConverter;

	@Autowired
	public ElasticSearchCarFinder(Client esClient, CarJsonConverter carJsonConverter) {
		this.client = esClient;
		this.carJsonConverter = carJsonConverter;
	}

	@Override
	public PaginatedResult<Car> findCars(CarSearchCriteria criteria) {
		final QueryBuilder q = buildQuery(criteria);

		long totalCount = getTotalCount(q);
		List<Car> cars = getCars(q, criteria.getPageNumber(), criteria.getPageSize());

		return new PaginatedResult<Car>(cars, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
	}

	private QueryBuilder buildQuery(CarSearchCriteria criteria) {
		final QueryBuilder q;

		if (criteria.isEmpty()) {
			q = QueryBuilders.matchAllQuery();
		} else {
			BoolQueryBuilder bq = QueryBuilders.boolQuery();

			if (criteria.isMakeGiven()) {
				bq.must(termQuery("make", criteria.getMake().toLowerCase()));
			}

			if (criteria.isModelGiven()) {
				bq.must(termQuery("model", criteria.getModel().toLowerCase()));
			}

			if (criteria.isColorGiven()) {
				bq.must(termQuery("color", criteria.getColor().toLowerCase()));
			}

			q = bq;
		}
		return q;
	}

	private List<Car> getCars(QueryBuilder q, int pageNumber, int pageSize) {
		SearchResponse searchResponse = client.prepareSearch(CAR.getIndex()).setTypes(CAR.getType())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(q)
				.addSort(fieldSort("id.value").order(SortOrder.ASC))
				.setFrom(pageNumber * pageSize)
				.setSize(pageSize)
				.execute()
				.actionGet();

		List<Car> cars = new ArrayList<>();
		for (SearchHit hit : searchResponse.getHits()) {
			String json = hit.getSourceAsString();
			cars.add(carJsonConverter.fromJson(json));
		}
		return cars;
	}

	private long getTotalCount(QueryBuilder q) {
		CountResponse countResponse = client.prepareCount(CAR.getIndex()).setTypes(CAR.getType())
				.setQuery(q)
				.execute()
				.actionGet();

		return countResponse.getCount();
	}

}
