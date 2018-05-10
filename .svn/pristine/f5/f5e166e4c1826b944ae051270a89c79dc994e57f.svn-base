package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "group_master")
@JsonFilter("GROUP_MASTER_FILTER")
public class GroupMaster extends BaseModel {
	
	private static final long serialVersionUID = 7988291730677121240L;

	private User createdBy;
	
	private String groupName;
	private LocalDateTime createdOn;
	
	@ManyToOne
	@JoinColumn(name="created_by")
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "group_name")
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Column(name = "created_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
}
