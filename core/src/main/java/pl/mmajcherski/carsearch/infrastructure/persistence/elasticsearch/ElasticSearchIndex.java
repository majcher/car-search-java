package pl.mmajcherski.carsearch.infrastructure.persistence.elasticsearch;

public enum ElasticSearchIndex {

	CAR("carsearch", "model");

	private final String index;
	private final String type;

	ElasticSearchIndex(String index, String type) {
		this.index = index;
		this.type = type;
	}

	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}
}
