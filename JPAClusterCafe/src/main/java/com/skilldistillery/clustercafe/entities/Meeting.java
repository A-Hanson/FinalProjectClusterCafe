package com.skilldistillery.clustercafe.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Meeting {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private String name;

	private String description;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private Boolean enabled;

	private Boolean flagged;
	
	@ManyToOne
	@JoinColumn(name="store_id")
	private Store store;
	
	@Column(name="created_at")
	private LocalDateTime creationDate;
	
	@Column(name="updated_at")
	private LocalDateTime updatedDate;
	
	@Column(name="meeting_date")
	private LocalDateTime meetingDate;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "meetings")
	private List<User> attendees;

	public Meeting() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getFlagged() {
		return flagged;
	}

	public void setFlagged(Boolean flagged) {
		this.flagged = flagged;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public LocalDateTime getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(LocalDateTime meetingDate) {
		this.meetingDate = meetingDate;
	}
	
	public List<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}
	
	public void addAttendee(User user) {
		if (attendees == null) {
			attendees = new ArrayList<User>();
		}
		if (!attendees.contains(user)) {
			attendees.add(user);
			user.addMeeting(this);
		}
	}
	
	public void removeAttendee(User user) {
		if (attendees != null && attendees.contains(user)) {
			attendees.remove(user);
			user.removeMeeting(this);
		}
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", user=" + user + ", enabled=" + enabled + ", flagged=" + flagged + ", store=" + store
				+ ", creationDate=" + creationDate + ", updatedDate=" + updatedDate + ", meetingDate=" + meetingDate
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
