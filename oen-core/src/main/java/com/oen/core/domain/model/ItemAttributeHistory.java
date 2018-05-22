package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;


@Entity
@Table(name = "item_attribute_history")
@JsonFilter("ITEMS_ATTRIBUTE_HISTORY_FILTER")
public class ItemAttributeHistory extends BaseModel {

	private static final long serialVersionUID = 8744320330465957095L;
	
	private CustomerItems customerItem;
	private ItemAttributes itemAttribute;
	private User changedBy;
	
	private String attributeValue;
	private Double consumedPower = 0.00;

	@ManyToOne
	@JoinColumn(name="customer_item_id")
	public CustomerItems getCustomerItem() {
		return customerItem;
	}
	public void setCustomerItem(CustomerItems customerItem) {
		this.customerItem = customerItem;
	}

	@ManyToOne
	@JoinColumn(name="item_attribute_id")
	public ItemAttributes getItemAttribute() {
		return itemAttribute;
	}
	public void setItemAttribute(ItemAttributes itemAttribute) {
		this.itemAttribute = itemAttribute;
	}

	@ManyToOne
	@JoinColumn(name="changed_by")
	public User getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	@Column(name = "attribute_value")
	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	@Column(name = "consumed_power", nullable = false)
	public Double getConsumedPower() {
		return consumedPower;
	}
	public void setConsumedPower(Double consumedPower) {
		this.consumedPower = consumedPower;
	}
	
	

}
