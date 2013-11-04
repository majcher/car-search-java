package pl.mmajcherski.carsearch.infrastructure.persistence;

import com.google.common.base.Optional;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;
import pl.mmajcherski.ddd.annotation.DomainRepository;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchIndex.CAR;

@DomainRepository
public class ElasticSearchCarRepository implements CarRepository {

    private final Client client;
    private final CarJsonConverter carJsonConverter;

    @Autowired
    public ElasticSearchCarRepository(Client esClient, CarJsonConverter carJsonConverter) {
        this.client = esClient;
        this.carJsonConverter = carJsonConverter;
    }

    @Override
    public Optional<Car> find(CarId id) {
        GetResponse response = client.prepareGet(CAR.getIndex(), CAR.getType(), String.valueOf(id.getValue()))
                .execute()
                .actionGet();

	    if (!response.isExists()) {
		    return Optional.absent();
	    }

	    Car car = carJsonConverter.fromJson(response.getSourceAsString());
        return Optional.of(car);
    }

    @Override
    public void save(Car car) {
        final String carJson = carJsonConverter.toJson(car);

        client.prepareIndex(CAR.getIndex(), CAR.getType(), String.valueOf(car.getId().getValue()))
		        .setRefresh(true)
                .setSource(carJson)
                .execute()
                .actionGet();
    }

	@Override
	public void deleteAll() {
		if (!indexExists(CAR.getIndex())) {
			return;
		}

		client.prepareDeleteByQuery(CAR.getIndex())
				.setQuery(matchAllQuery())
				.execute()
				.actionGet();
	}

	private boolean indexExists(String index) {
		IndicesExistsResponse indexExistsResponse = client.admin().indices()
				.exists(new IndicesExistsRequest(index)).actionGet();

		return indexExistsResponse.isExists();
	}

}
