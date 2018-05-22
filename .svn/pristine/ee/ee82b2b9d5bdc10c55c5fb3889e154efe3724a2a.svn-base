package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;


@Entity
@Table(name = "item_attributes")
@JsonFilter("ITEM_ATTRIBUTE_FILTER")
public class ItemAttributes extends BaseModel {
	
	private static final long serialVersionUID = 4797257679465818454L;
	
	private ItemMaster item;
	private AttributeMaster attribute;
	
	private Integer minimumInputValue;
	private Integer maximumInputValue;
	
	@ManyToOne
	@JoinColumn(name="item_id")
	public ItemMaster getItem() {
		return item;
	}
	public void setItem(ItemMaster item) {
		this.item = item;
	}
	
	@ManyToOne
	@JoinColumn(name="attribute_master_id")
	public AttributeMaster getAttribute() {
		return attribute;
	}
	public void setAttribute(AttributeMaster attribute) {
		this.attribute = attribute;
	}
	
	@Column(name = "minimum_input_value")
	public Integer getMinimumInputValue() {
		return minimumInputValue;
	}
	public void setMinimumInputValue(Integer minimumInputValue) {
		this.minimumInputValue = minimumInputValue;
	}
	
	@Column(name = "maximum_input_value")
	public Integer getMaximumInputValue() {
		return maximumInputValue;
	}
	public void setMaximumInputValue(Integer maximumInputValue) {
		this.maximumInputValue = maximumInputValue;
	}	
}
