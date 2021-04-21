package com.skilldistillery.clustercafe.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="group_message")
public class GroupMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	private String title;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	private User creator;
	
	@ManyToOne
	@JoinColumn(name="cluster_group_id")
	private ClusterGroup clusterGroup;
	
	@Column(name="created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	private Boolean enabled = true;
	
	@ManyToOne
	@JoinColumn(name="parent_message_id")
	private GroupMessage parentMessage = null;
	
//	Constructor
	public GroupMessage() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public ClusterGroup getClusterGroup() {
		return clusterGroup;
	}

	public void setClusterGroup(ClusterGroup clusterGroup) {
		this.clusterGroup = clusterGroup;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public GroupMessage getParentMessage() {
		return parentMessage;
	}

	public void setParentMessage(GroupMessage parentMessage) {
		this.parentMessage = parentMessage;
	}

	@Override
	public String toString() {
		return "GroupMessage [id=" + id + ", title=" + title + ", content=" + content + ", creator=" + creator
				+ ", clusterGroup=" + clusterGroup + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", enabled=" + enabled + ", parentMessage=" + parentMessage + "]";
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
		GroupMessage other = (GroupMessage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
