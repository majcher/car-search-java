package pl.mmajcherski.carsearch.domain.car.service;

import pl.mmajcherski.carsearch.domain.car.model.Car;
import pl.mmajcherski.carsearch.domain.common.model.PagedResult;

import static com.google.common.base.Strings.isNullOrEmpty;

public interface CarFinder {

	public PagedResult<Car> findCars(SearchCriteria criteria);


	public static final class SearchCriteria {

		public static final int UNLIMITED_PAGE_SIZE = -1;

		private String make;
		private String model;
		private String color;

		private int pageNumber = 0;
		private int pageSize = UNLIMITED_PAGE_SIZE;

		private SearchCriteria() {}

		public static SearchCriteria anyCar() {
			return new SearchCriteria();
		}

		public SearchCriteria withMake(String make) {
			this.make = make;
			return this;
		}

		public SearchCriteria withModel(String model) {
			this.model = model;
			return this;
		}

		public SearchCriteria withColor(String color) {
			this.color = color;
			return this;
		}

		public SearchCriteria withPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}

		public SearchCriteria withPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public String getMake() {
			return make;
		}

		public String getModel() {
			return model;
		}

		public String getColor() {
			return color;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getPageSize() {
			return pageSize;
		}

		public boolean isEmpty() {
			return isNullOrEmpty(make) && isNullOrEmpty(model) && isNullOrEmpty(color);
		}

		public boolean isMakeGiven() {
			return !isNullOrEmpty(make);
		}

		public boolean isModelGiven() {
			return !isNullOrEmpty(model);
		}

		public boolean isColorGiven() {
			return !isNullOrEmpty(color);
		}
	}

}
