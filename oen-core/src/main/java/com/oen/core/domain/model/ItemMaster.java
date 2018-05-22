package com.oen.core.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "item_master")
@JsonFilter("ITEM_MASTER_FILTER")
public class ItemMaster extends BaseModel {

	private static final long serialVersionUID = 4447750822653854873L;
	
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
 