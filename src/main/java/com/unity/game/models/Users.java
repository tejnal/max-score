package com.unity.game.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_ID") })
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "USER_ID")
	private int userId;

	@NotBlank
	@Column(name = "SESSION_KEY")
	private String sessionkey;
	
	@NotNull
	@Column(name = "CREATION_TIME")
	private Date creationTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Users() {
		super();
	}
	
	public Users(int userId, String sessionkey, Date creationTime) {
		super();
		this.userId = userId;
		this.sessionkey = sessionkey;
		this.creationTime = creationTime;
	}
}
