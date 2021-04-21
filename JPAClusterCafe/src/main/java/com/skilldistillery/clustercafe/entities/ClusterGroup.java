package com.skilldistillery.clustercafe.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cluster_group")
public class ClusterGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String description;
	
	@Column(name="created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private Boolean enabled = true;
	
	@Column(name="img_url")
	private String imgUrl;
	
	@ManyToOne
	@JoinColumn(name="moderator_id")
	private User moderator;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "clusterGroups")
	private List<User> users;
	
//	Constructor
	public ClusterGroup() {}

public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	//	Methods
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public User getModerator() {
		return moderator;
	}

	public void setModerator(User moderator) {
		this.moderator = moderator;
	}

	@Override
	public String toString() {
		return "ClusterGroup [id=" + id + ", name=" + name + ", description=" + description + ", createdAt=" + createdAt
				+ ", enabled=" + enabled + ", imgUrl=" + imgUrl + ", moderator=" + moderator + "]";
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
		ClusterGroup other = (ClusterGroup) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
