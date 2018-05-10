package com.oen.core.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "customer_items")
@JsonFilter("CUSTOMER_ITEMS_FILTER")
public class CustomerItems extends BaseModel {
	
	private static final long serialVersionUID = 6060080335859436809L;
	
	private Customer customer;
	private ItemMaster item;
	private Manufacture manufacture;
	
	private String itemUniqueNumber;
	private String itemBatchNumber;
	private String nickName;
	private Integer itemThresholdKWH;
	
	//child table mappings 
	//used mainly for cascade delete of child tables.
	private List<ItemAttributeState> itemAttributeState;
	private List<ItemAttributeHistory> itemAttributeHistory;
	private List<ItemAttributeSchedule> ItemAttributeSchedule;
	private List<GroupItemMapping> groupItems;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ManyToOne
	@JoinColumn(name="item_id")
	public ItemMaster getItem() {
		return item;
	}
	public void setItem(ItemMaster item) {
		this.item = item;
	}
	
	@ManyToOne
	@JoinColumn(name="manufacture_id")
	public Manufacture getManufacture() {
		return manufacture;
	}
	public void setManufacture(Manufacture manufacture) {
		this.manufacture = manufacture;
	}
	
	@Column(name = "item_unique_number")
	public String getItemUniqueNumber() {
		return itemUniqueNumber;
	}
	public void setItemUniqueNumber(String itemUniqueNumber) {
		this.itemUniqueNumber = itemUniqueNumber;
	}
	
	@Column(name = "item_batch_number")
	public String getItemBatchNumber() {
		return itemBatchNumber;
	}
	public void setItemBatchNumber(String itemBatchNumber) {
		this.itemBatchNumber = itemBatchNumber;
	}
	
	@Column(name = "item_nickname")
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Column(name = "item_threshold_kwh")
	public Integer getItemThresholdKWH() {
		return itemThresholdKWH;
	}
	public void setItemThresholdKWH(Integer itemThresholdKWH) {
		this.itemThresholdKWH = itemThresholdKWH;
	}
	
	
	
	@OneToMany(mappedBy = "customerItem", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<ItemAttributeState> getItemAttributeState() {
		return itemAttributeState;
	}
	public void setItemAttributeState(List<ItemAttributeState> itemAttributeState) {
		this.itemAttributeState = itemAttributeState;
	}
	
	@OneToMany(mappedBy = "customerItem", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<ItemAttributeHistory> getItemAttributeHistory() {
		return itemAttributeHistory;
	}
	public void setItemAttributeHistory(List<ItemAttributeHistory> itemAttributeHistory) {
		this.itemAttributeHistory = itemAttributeHistory;
	}
	
	@OneToMany(mappedBy = "customerItem", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<ItemAttributeSchedule> getItemAttributeSchedule() {
		return ItemAttributeSchedule;
	}
	public void setItemAttributeSchedule(List<ItemAttributeSchedule> itemAttributeSchedule) {
		ItemAttributeSchedule = itemAttributeSchedule;
	}
	
	@OneToMany(mappedBy = "custItem", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<GroupItemMapping> getGroupItems() {
		return groupItems;
	}
	public void setGroupItems(List<GroupItemMapping> groupItems) {
		this.groupItems = groupItems;
	}
}
