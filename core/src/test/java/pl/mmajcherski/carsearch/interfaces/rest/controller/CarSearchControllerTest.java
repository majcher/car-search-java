package pl.mmajcherski.carsearch.interfaces.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.mmajcherski.carsearch.domain.car.model.CarRepository;
import pl.mmajcherski.carsearch.infrastructure.di.spring.WebConfiguration;
import pl.mmajcherski.carsearch.infrastructure.test.BaseIntegrationTest;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static pl.mmajcherski.carsearch.domain.car.model.TestCarBuilder.aCar;

@WebAppConfiguration
@ContextConfiguration(classes = WebConfiguration.class)
public class CarSearchControllerTest extends BaseIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private CarRepository carRepository;

	@BeforeMethod
	public void setup() {
		this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

		carRepository.deleteAll();
		carRepository.save(aCar().withId(1).withMake("Ford").withModel("Mustang").withColor("Some red color").build());
		carRepository.save(aCar().withId(2).withMake("Audi").withModel("A4").withColor("Some blue color").build());
		carRepository.save(aCar().withId(3).withMake("Audi").withModel("A5").withColor("Some green color").build());
		carRepository.save(aCar().withId(4).withMake("Audi").withModel("A6").withColor("Some blue color").build());
	}

	@Test
	public void shouldFindAllCars() throws Exception {
		mockMvc.perform(get("/cars/search"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(4)))
				.andExpect(jsonPath("$.items[0].make").value("Ford"))
				.andExpect(jsonPath("$.items[0].model").value("Mustang"))
				.andExpect(jsonPath("$.items[1].make").value("Audi"))
				.andExpect(jsonPath("$.items[1].model").value("A4"))
				.andExpect(jsonPath("$.items[2].make").value("Audi"))
				.andExpect(jsonPath("$.items[2].model").value("A5"))
				.andExpect(jsonPath("$.items[3].make").value("Audi"))
				.andExpect(jsonPath("$.items[3].model").value("A6"));
	}

	@Test
	public void shouldFindAllCarsByMake() throws Exception {
		mockMvc.perform(get("/cars/search?make=Audi"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(3)))
				.andExpect(jsonPath("$.items[0].make").value("Audi"))
				.andExpect(jsonPath("$.items[0].model").value("A4"))
				.andExpect(jsonPath("$.items[1].make").value("Audi"))
				.andExpect(jsonPath("$.items[1].model").value("A5"))
				.andExpect(jsonPath("$.items[2].make").value("Audi"))
				.andExpect(jsonPath("$.items[2].model").value("A6"));
	}

	@Test
	public void shouldFindAllCarsByModel() throws Exception {
		mockMvc.perform(get("/cars/search?model=A4"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(1)))
				.andExpect(jsonPath("$.items[0].make").value("Audi"))
				.andExpect(jsonPath("$.items[0].model").value("A4"));
	}

	@Test
	public void shouldFindAllCarsByColor() throws Exception {
		mockMvc.perform(get("/cars/search?color=green"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(1)))
				.andExpect(jsonPath("$.items[0].make").value("Audi"))
				.andExpect(jsonPath("$.items[0].model").value("A5"));
	}

	@Test
	public void shouldFindAllCarsByMakeAndColor() throws Exception {
		mockMvc.perform(get("/cars/search?make=audi&color=green"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(1)))
				.andExpect(jsonPath("$.items[0].make").value("Audi"))
				.andExpect(jsonPath("$.items[0].model").value("A5"));
	}

	@Test
	public void shouldFindAllCarsByMakeModelAndColor() throws Exception {
		mockMvc.perform(get("/cars/search?make=audi&model=a5&color=green"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(1)))
				.andExpect(jsonPath("$.items[0].make").value("Audi"))
				.andExpect(jsonPath("$.items[0].model").value("A5"));
	}

	@AfterClass
	public void deleteAfterTest() {
		carRepository.deleteAll();
	}
}
