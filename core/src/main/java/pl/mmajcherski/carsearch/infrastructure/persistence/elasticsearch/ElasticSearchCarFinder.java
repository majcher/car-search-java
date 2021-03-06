package pl.mmajcherski.carsearch.infrastructure.persistence.elasticsearch;

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
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.domain.car.service.CarFinder;
import pl.mmajcherski.carsearch.domain.common.model.PagedResult;
import pl.mmajcherski.carsearch.infrastructure.persistence.elasticsearch.converter.CarJsonConverter;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.search.sort.SortBuilders.fieldSort;

@Component
public class ElasticSearchCarFinder implements CarFinder {

	private final Client client;
	private final CarJsonConverter carJsonConverter;

	@Autowired
	public ElasticSearchCarFinder(Client esClient, CarJsonConverter carJsonConverter) {
		this.client = esClient;
		this.carJsonConverter = carJsonConverter;
	}

	@Override
	public PagedResult<Car> findCars(SearchCriteria criteria) {
		final QueryBuilder query = buildQuery(criteria);

		long totalCount = getTotalCount(query);
		List<Car> cars = getCars(query, criteria.getPageNumber(), criteria.getPageSize());

		return new PagedResult.Builder<Car>().with(cars)
				.currentPage(criteria.getPageNumber())
				.pageSize(criteria.getPageSize())
				.totalCount(totalCount)
				.build();
	}

	private QueryBuilder buildQuery(SearchCriteria criteria) {
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
				bq.must(matchQuery("color", criteria.getColor().toLowerCase()));
			}

			q = bq;
		}
		return q;
	}

	private List<Car> getCars(QueryBuilder q, int pageNumber, int pageSize) {
		SearchResponse searchResponse = client.prepareSearch(ElasticSearchIndex.CAR.getIndex()).setTypes(ElasticSearchIndex.CAR.getType())
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
		CountResponse countResponse = client.prepareCount(ElasticSearchIndex.CAR.getIndex()).setTypes(ElasticSearchIndex.CAR.getType())
				.setQuery(q)
				.execute()
				.actionGet();

		return countResponse.getCount();
	}

}
