package com.oen.core.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "attribute_master")
@JsonFilter("ATTRIBUTE_MASTER_FILTER")
public class AttributeMaster extends BaseModel {
	
	
	private static final long serialVersionUID = 3976767865627495545L;
	
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
