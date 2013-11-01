package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;
import pl.mmajcherski.ddd.annotation.DomainRepository;

import static pl.mmajcherski.carsearch.infrastructure.persistence.ElasticSearchIndex.CAR;

@DomainRepository
public class ElasticSearchCarRepository implements CarRepository {

    private final Client esClient;
    private final CarJsonConverter carJsonConverter;

    @Autowired
    public ElasticSearchCarRepository(Client esClient, CarJsonConverter carJsonConverter) {
        this.esClient = esClient;
        this.carJsonConverter = carJsonConverter;
    }

    @Override
    public Car find(CarId id) {
        GetResponse response = esClient.prepareGet(CAR.getIndex(), CAR.getType(), String.valueOf(id.getValue()))
                .execute()
                .actionGet();

        return carJsonConverter.fromJson(response.getSourceAsString());
    }

    @Override
    public void save(Car car) {
        final String carJson = carJsonConverter.toJson(car);

        esClient.prepareIndex(CAR.getIndex(), CAR.getType(), String.valueOf(car.getId().getValue()))
                .setSource(carJson)
                .execute()
                .actionGet();
    }

}
