package stores.articles.m;

import stores.producers.m.Producer;
import stores.groups.m.ArticlesGroup;

/**
 *
 * @author tobikster
 */
public class Part {
	private Integer _id;
	private ArticlesGroup _group;
	private Producer _producer;
	private String _catalogNumber;
	private String _name;
	private Float _margin;
	private Float _grossPrice;
	private Integer _count;
	private String _picture;

	public Part() {
		this(null, null, null, null, null, null, null, null);
	}

	public Part(ArticlesGroup group, Producer producer, String name, Float margin, Float grossPrice, Integer count) {
		this(group, producer, null, name, margin, grossPrice, count, null);
	}

	public Part(ArticlesGroup group, Producer producer, String catalogNumber, String name, Float margin, Float grossPrice, Integer count, String picture) {
		_group = group;
		_producer = producer;
		_catalogNumber = catalogNumber;
		_name = name;
		_margin = margin;
		_grossPrice = grossPrice;
		_count = count;
		_picture = picture;
	}

	public Integer getId() {
		return _id;
	}

	public String getCatalogNumber() {
		return _catalogNumber;
	}

	public Integer getCount() {
		return _count;
	}

	public Float getGrossPrice() {
		return _grossPrice;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

	public Float getMargin() {
		return _margin;
	}

	public String getName() {
		return _name;
	}

	public String getPicture() {
		return _picture;
	}

	public Producer getProducer() {
		return _producer;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setCatalogNumber(String catalogNumber) {
		_catalogNumber = catalogNumber;
	}

	public void setCount(Integer count) {
		_count = count;
	}

	public void setGrossPrice(Float grossPrice) {
		_grossPrice = grossPrice;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}

	public void setMargin(Float margin) {
		_margin = margin;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPicture(String picture) {
		_picture = picture;
	}

	public void setProducer(Producer producer) {
		_producer = producer;
	}

	@Override
	public String toString() {
		return _name;
	}
}
