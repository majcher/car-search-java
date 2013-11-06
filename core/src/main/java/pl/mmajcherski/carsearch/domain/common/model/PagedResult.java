package pl.mmajcherski.carsearch.domain.common.model;

import java.util.Collections;
import java.util.List;

public final class PagedResult<T> {

	private final List<T> items;
	private final int pageSize;
	private final int currentPage;
	private final int pagesCount;
	private final long totalCount;

	private PagedResult(List<T> items, int currentPage, int pageSize, long totalCount) {
		this.items = items;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.pagesCount = countPages(pageSize, totalCount);
		this.totalCount = totalCount;
	}

	public static class Builder<E> {
		private List<E> items = Collections.emptyList();
		private Integer pageSize;
		private Integer currentPage;
		private long totalCount = 0;

		public Builder<E> with(List<E> items) {
			this.items = items;
			return this;
		}

		public Builder<E> currentPage(int currentPage) {
			this.currentPage = currentPage;
			return this;
		}

		public Builder<E> pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder<E> totalCount(long totalCount) {
			this.totalCount = totalCount;
			return this;
		}

		public PagedResult<E> build() {
			return new PagedResult<E>(items, currentPage, pageSize, totalCount);
		}
	}

	public List<T> getItems() {
		return items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	private int countPages(int size, long itemsCount) {
		return (int) Math.ceil((double) itemsCount / size);
	}
}
