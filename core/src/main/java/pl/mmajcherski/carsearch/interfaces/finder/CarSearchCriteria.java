package pl.mmajcherski.carsearch.interfaces.finder;

import static com.google.common.base.Strings.isNullOrEmpty;

public final class CarSearchCriteria {

	public static final int UNLIMITED_PAGE_SIZE = -1;

	private String make;
	private String model;
	private String color;

	private int pageNumber = 0;
	private int pageSize = UNLIMITED_PAGE_SIZE;

	private CarSearchCriteria() {}

	public static CarSearchCriteria anyCar() {
		return new CarSearchCriteria();
	}

	public CarSearchCriteria withMake(String make) {
		this.make = make;
		return this;
	}

	public CarSearchCriteria withModel(String model) {
		this.model = model;
		return this;
	}

	public CarSearchCriteria withColor(String color) {
		this.color = color;
		return this;
	}

	public CarSearchCriteria withPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	public CarSearchCriteria withPageSize(int pageSize) {
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
