package pl.mmajcherski.carsearch.interfaces.finder;

public final class CarSearchCriteria {

	public static final int UNLIMITED_PAGE_SIZE = -1;

	private String text;
	private int pageNumber = 0;
	private int pageSize = UNLIMITED_PAGE_SIZE;

	private CarSearchCriteria() {}

	public static CarSearchCriteria anyCar() {
		return new CarSearchCriteria();
	}

	public CarSearchCriteria containingText(String text) {
		this.text = text;
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

	public String getText() {
		return text;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}
}
