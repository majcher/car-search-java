package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.search.sort.SortBuilders.fieldSort;

@Component
public class ElasticSearchCarRepository implements CarRepository {

    public static final String ES_INDEX = "carsearch";
    public static final String ES_TYPE = "car";

    private final Client esClient;
    private final CarJsonConverter carJsonConverter;

    @Autowired
    public ElasticSearchCarRepository(Client esClient, CarJsonConverter carJsonConverter) {
        this.esClient = esClient;
        this.carJsonConverter = carJsonConverter;
    }

    @Override
    public Car find(CarId id) {
        GetResponse response = esClient.prepareGet(ES_INDEX, ES_TYPE, String.valueOf(id.getValue()))
                .execute()
                .actionGet();

        return carJsonConverter.fromJson(response.getSourceAsString());
    }

    @Override
    public List<Car> findByMakeOrModel(String phrase) {
	    QueryBuilder q = QueryBuilders.queryString(phrase.toLowerCase())
			    .field("make")
			    .field("model")
			    .analyzer("whitespace")
	            .defaultOperator(QueryStringQueryBuilder.Operator.AND);

        SearchResponse response = esClient.prepareSearch(ES_INDEX).setTypes(ES_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(q)
		        .addSort(fieldSort("id.value").order(SortOrder.ASC))
                .execute()
                .actionGet();

        List<Car> cars = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            cars.add(carJsonConverter.fromJson(hit.getSourceAsString()));
        }

        return cars;
    }

    @Override
    public void save(Car car) {
        final String carJson = carJsonConverter.toJson(car);

        esClient.prepareIndex(ES_INDEX, ES_TYPE, String.valueOf(car.getId().getValue()))
                .setSource(carJson)
                .execute()
                .actionGet();
    }

}
