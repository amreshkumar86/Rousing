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
@Table(name = "item_attribute_schedule")
@JsonFilter("SCHEDULE_FILTER")
public class ItemAttributeSchedule extends BaseModel {
	
	private static final long serialVersionUID = 1043202457501680227L;
	
	private CustomerItems customerItem;
	private LocalDateTime scheduleTime;
	private String timerValue;
	private Integer timerExecutedStatus;
	
	//transient objects
	private String scheduleDateTime;
	private String utcZone;
	
	@ManyToOne
	@JoinColumn(name="customer_item_id")
	public CustomerItems getCustomerItem() {
		return customerItem;
	}
	public void setCustomerItem(CustomerItems customerItem) {
		this.customerItem = customerItem;
	}
	
	@Column(name = "schedule_time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(LocalDateTime scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	
	@Column(name = "timer_value")
	public String getTimerValue() {
		return timerValue;
	}
	public void setTimerValue(String timerValue) {
		this.timerValue = timerValue;
	}
	
	@Column(name = "timer_executed")
	public Integer getTimerExecutedStatus() {
		return timerExecutedStatus;
	}
	public void setTimerExecutedStatus(Integer timerExecutedStatus) {
		this.timerExecutedStatus = timerExecutedStatus;
	}
	
	
	@Transient
	public String getScheduleDateTime() {
		return scheduleDateTime;
	}
	public void setScheduleDateTime(String scheduleDateTime) {
		this.scheduleDateTime = scheduleDateTime;
	}
	
	@Transient
	public String getUtcZone() {
		return utcZone;
	}
	public void setUtcZone(String utcZone) {
		this.utcZone = utcZone;
	}
	
	
}
