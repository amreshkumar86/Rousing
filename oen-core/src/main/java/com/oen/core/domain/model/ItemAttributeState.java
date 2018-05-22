package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;


@Entity
@Table(name = "item_attribute_state")
@JsonFilter("ITEM_ATTRIBUTE_STATE_FILTER")
public class ItemAttributeState extends BaseModel {

	private static final long serialVersionUID = 3272857616705308037L;
	
	private CustomerItems customerItem;
	private AttributeMaster attribute;
	
	private String attributeValue;
	private Double energyConsumed = 0.00;

	
	@ManyToOne
	@JoinColumn(name="customer_item_id")
	public CustomerItems getCustomerItem() {
		return customerItem;
	}
	public void setCustomerItem(CustomerItems customerItem) {
		this.customerItem = customerItem;
	}

	
	@ManyToOne
	@JoinColumn(name="attributes_master_id")
	public AttributeMaster getAttribute() {
		return attribute;
	}
	public void setAttribute(AttributeMaster attribute) {
		this.attribute = attribute;
	}

	
	@Column(name = "attribute_value")
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
	@Column(name = "energy_consumed")
	public Double getEnergyConsumed() {
		return energyConsumed;
	}
	public void setEnergyConsumed(Double energyConsumed) {
		this.energyConsumed = energyConsumed;
	}
	
	
	

}
