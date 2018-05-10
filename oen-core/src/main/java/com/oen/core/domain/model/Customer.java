package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "customer")
@JsonFilter("CUSTOMER_FILTER")
public class Customer extends BaseModel {
	
	private static final long serialVersionUID = 6781898478512649235L;
	
	private String name;
	private String contactNumber;
	private LocalDateTime registeredOn;
	private String description;
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "contact_number")
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	@Column(name = "registered_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getRegisteredOn() {
		return registeredOn;
	}
	public void setRegisteredOn(LocalDateTime registeredOn) {
		this.registeredOn = registeredOn;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
