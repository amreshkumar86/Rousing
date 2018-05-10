package com.oen.core.domain.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "group_items")
@JsonFilter("GROUP_ITEM_FILTER")
public class GroupItemMapping extends BaseModel {
	
	private static final long serialVersionUID = 4434963807306471591L;
	
	private CustomerItems custItem;
	private GroupMaster group;
	
	@ManyToOne
	@JoinColumn(name="customer_item_id")
	public CustomerItems getCustItem() {
		return custItem;
	}
	public void setCustItem(CustomerItems custItem) {
		this.custItem = custItem;
	}
	
	@ManyToOne
	@JoinColumn(name="group_master_id")
	public GroupMaster getGroup() {
		return group;
	}
	public void setGroup(GroupMaster group) {
		this.group = group;
	}
	
	

}
