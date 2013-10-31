package pl.mmajcherski.carsearch.infrastructure.persistence;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mmajcherski.carsearch.domain.model.car.Car;
import pl.mmajcherski.carsearch.domain.model.car.CarId;
import pl.mmajcherski.carsearch.domain.model.common.Money;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class CarJsonConverter {

    private final ObjectMapper mapper;

    @Autowired
    public CarJsonConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String toJson(Car car) {
        try {
            return mapper.writeValueAsString(car);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Car fromJson(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            Long id = root.path("id").get("value").asLong();
            String make = root.path("make").asText();
            String model = root.path("model").asText();
            String color = root.path("color").asText();
            Double price = root.path("price").get("value").asDouble();
            String currency = root.path("price").get("currency").asText();

            Car.Builder b = new Car.Builder();
            b.setId(new CarId(id));
            b.setMake(make);
            b.setModel(model);
            b.setColor(color);
            b.setPrice(new Money(new BigDecimal(price), currency));

            return b.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
