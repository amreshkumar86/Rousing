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
@Table(name = "user_feedback")
@JsonFilter("USER_FEEDBACK_FILTER")
public class UserFeedback extends BaseModel {
	
	private static final long serialVersionUID = -5682829764742719635L;

	private User feedbackGivenBy;
	
	private String title;
	private String query;
	private LocalDateTime givenOn;
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="feedback_by")
	public User getFeedbackGivenBy() {
		return feedbackGivenBy;
	}
	public void setFeedbackGivenBy(User feedbackGivenBy) {
		this.feedbackGivenBy = feedbackGivenBy;
	}
	
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "query")
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	@Column(name = "given_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getGivenOn() {
		return givenOn;
	}
	public void setGivenOn(LocalDateTime givenOn) {
		this.givenOn = givenOn;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
