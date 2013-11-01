package pl.mmajcherski.cqrs.query;

import java.util.Collections;
import java.util.List;

public final class PaginatedResult<T> {
	private final List<T> items;
	private final int pageSize;
	private final int pageNumber;
	private final int pagesCount;
	private final long totalItemsCount;

	public PaginatedResult(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		items = Collections.emptyList();
		pagesCount = 0;
		totalItemsCount = 0;
	}

	public PaginatedResult(List<T> items, int pageNumber, int pageSize, long totalItemsCount) {
		this.items = items;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.pagesCount = countPages(pageSize, totalItemsCount);
		this.totalItemsCount = totalItemsCount;
	}

	private int countPages(int size, long itemsCount) {
		return (int) Math.ceil((double) itemsCount / size);
	}

	public List<T> getItems() {
		return items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public long getTotalItemsCount() {
		return totalItemsCount;
	}
}
