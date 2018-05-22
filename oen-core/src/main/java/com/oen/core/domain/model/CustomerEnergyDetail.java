package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;




@Entity
@Table(name = "customer_energy_detail")
@JsonFilter("CUSTOMER_ENERGY_DETAIL_FILTER")
public class CustomerEnergyDetail extends BaseModel {
	
	private static final long serialVersionUID = -4591022761196030936L;
	
	private Customer customer;
	private ItemMaster itemMaster;
	
	private LocalDateTime calculatedAt;
	private Double wattage;
	private Double powerConsumed;
	
	
	//transient object
	private String timeValue;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ManyToOne
	@JoinColumn(name="item_master_id")
	public ItemMaster getItemMaster() {
		return itemMaster;
	}
	public void setItemMaster(ItemMaster itemMaster) {
		this.itemMaster = itemMaster;
	}
	
	@Column(name = "time_value")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCalculatedAt() {
		return calculatedAt;
	}
	public void setCalculatedAt(LocalDateTime calculatedAt) {
		this.calculatedAt = calculatedAt;
	}
	
	@Column(name = "wattage")
	public Double getWattage() {
		return wattage;
	}
	public void setWattage(Double wattage) {
		this.wattage = wattage;
	}
	
	@Column(name = "power_consumed")
	public Double getPowerConsumed() {
		return powerConsumed;
	}
	public void setPowerConsumed(Double powerConsumed) {
		this.powerConsumed = powerConsumed;
	}
	
	
	@Transient
	public String getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}
	
	
	

}
