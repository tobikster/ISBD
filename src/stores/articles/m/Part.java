package stores.articles.m;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import stores.groups.m.ArticlesGroup;
import stores.producers.m.Producer;

/**
 *
 * @author tobikster
 */
public class Part implements Cloneable {
	private Integer _id;
	private ArticlesGroup _group;
	private Producer _producer;
	private String _catalogNumber;
	private String _name;
	private Float _margin;
	private String _marginText;
	private Float _grossPrice;
	private String _grossPriceText;
	private Float _count;
	private String _countText;
	private String _picture;
	private Map<ArticleAttribute, String> _attributes;

	public Part() {
	}

	public Part(int id, ArticlesGroup group, Producer producer, String name, Float margin, Float grossPrice, Float count) {
		this(id, group, producer, null, name, margin, grossPrice, count, null, new LinkedHashMap<ArticleAttribute, String>());
	}

	public Part(int id, ArticlesGroup group, Producer producer, String catalogNumber, String name, Float margin, Float grossPrice, Float count, String picture, Map<ArticleAttribute, String> attributes) {
		_id = id;
		_group = group;
		_producer = producer;
		_catalogNumber = catalogNumber;
		_name = name;
		_margin = margin;
		_grossPrice = grossPrice;
		_count = count;
		_picture = picture;
		_attributes = attributes;
	}

	public Integer getId() {
		return _id;
	}

	public String getCatalogNumber() {
		return _catalogNumber;
	}

	public Float getCount() {
		return _count;
	}
	
	public String getCountText() {
		return _countText;
	}

	public Float getGrossPrice() {
		return _grossPrice;
	}
	
	public String getGrossPricetext() {
		return _grossPriceText;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

	public Float getMargin() {
		return _margin;
	}
	
	public String getMarginText() {
		return _marginText;
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
	
	public Map<ArticleAttribute, String> getAttributes() {
		return _attributes;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setCatalogNumber(String catalogNumber) {
		_catalogNumber = catalogNumber;
	}

	public void setCount(float count) {
		_count = count;
	}
	
	public void setCountText(String countText) {
		_countText = countText;
		_count = null;
	}

	public void setGrossPrice(float grossPrice) {
		_grossPrice = grossPrice;
	}
	
	public void setGrossPriceText(String grossPriceText) {
		_grossPriceText = grossPriceText;
		_grossPrice = null;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}

	public void setMargin(float margin) {
		_margin = margin;
	}
	
	public void setMarginText(String marginText) {
		_marginText = marginText;
		_margin = null;
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
	
	public void setAttributes(Map<ArticleAttribute, String> attributes) {
		_attributes = attributes;
	}
  
  @Override
  public String toString() {
    return _name;
  }
  
  @Override
  public Part clone() {
	  Map<ArticleAttribute, String> attributes = new LinkedHashMap<>();
	  for(Entry<ArticleAttribute, String> attribute : _attributes.entrySet()) {
		  attributes.put(attribute.getKey().clone(), attribute.getValue());
	  }
    return new Part(_id, _group.clone(), _producer.clone(), _catalogNumber, _name, _margin, _grossPrice, _count, _picture, attributes);
  }
}
