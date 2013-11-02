package pl.mmajcherski.carsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.model.car.CarRepository;
import pl.mmajcherski.carsearch.infrastructure.spring.WebConfiguration;
import pl.mmajcherski.carsearch.infrastructure.test.BaseIntegrationTest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static pl.mmajcherski.carsearch.testdatabuilder.TestCarBuilder.aCar;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfiguration.class)
public class CarSearchControllerTest extends BaseIntegrationTest {

    private MockMvc mockMvc;

    @Autowired private WebApplicationContext webApplicationContext;
	@Autowired private CarRepository carRepository;

    @BeforeClass
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

	    carRepository.deleteAll();
	    carRepository.save(aCar().withId(1).withMake("Ford").withModel("Mustang").build());
	    carRepository.save(aCar().withId(2).withMake("Audi").withModel("A4").build());
	    carRepository.save(aCar().withId(3).withMake("Audi").withModel("A5").build());
    }

    @Test
    public void shouldListAllCars() throws Exception {
        mockMvc.perform(get("/cars/list"))
		        .andDo(print())
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$.items", hasSize(3)))
		        .andExpect(jsonPath("$.items[0].make").value("Ford"))
		        .andExpect(jsonPath("$.items[0].model").value("Mustang"))
		        .andExpect(jsonPath("$.items[1].make").value("Audi"))
		        .andExpect(jsonPath("$.items[1].model").value("A4"))
		        .andExpect(jsonPath("$.items[2].make").value("Audi"))
		        .andExpect(jsonPath("$.items[2].model").value("A5"));
    }

	@Test
	public void shouldSearchCars() throws Exception {
		mockMvc.perform(get("/cars/search?name=Audi"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(2)))
				.andExpect(jsonPath("$.items[0].make").value("Audi"))
				.andExpect(jsonPath("$.items[0].model").value("A4"))
				.andExpect(jsonPath("$.items[1].make").value("Audi"))
				.andExpect(jsonPath("$.items[1].model").value("A5"));
	}

	@AfterClass
	public void deleteAfterTEst() {
		carRepository.deleteAll();
	}
}
