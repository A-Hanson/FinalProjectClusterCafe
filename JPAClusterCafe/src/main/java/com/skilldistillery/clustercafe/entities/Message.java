package com.skilldistillery.clustercafe.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	private String content;
	private Boolean enabled;
	private Boolean read;
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	private User creator;
	
	@Column(name="created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name="parent_message_id")
	private Message parentMessageId;
	
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private User recipient;

	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Message(int id, String title, String content, Boolean enabled, Boolean read, User creator,
			LocalDateTime createdAt, LocalDateTime updatedAt, Message parentMessageId, User recipient) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.enabled = enabled;
		this.read = read;
		this.creator = creator;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.parentMessageId = parentMessageId;
		this.recipient = recipient;
	}

	
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
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

	public Message getParentMessageId() {
		return parentMessageId;
	}

	public void setParentMessageId(Message parentMessageId) {
		this.parentMessageId = parentMessageId;
	}

	public User getrecipient() {
		return recipient;
	}

	public void setrecipient(User recipient) {
		this.recipient = recipient;
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
		Message other = (Message) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Message [id=" + id + ", title=" + title + ", content=" + content + ", enabled=" + enabled + ", read="
				+ read + ", creator=" + creator + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", parentMessageId=" + parentMessageId + ", recipient=" + recipient + "]";
	}
}
