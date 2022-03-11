package com.unity.game.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS_SCORE", uniqueConstraints = { @UniqueConstraint(columnNames = {"USER_ID", "LEVEL_ID"}) })
public class UsersScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "USER_ID")
	private int userId;

	@NotNull
	@Column(name = "SCORE")
	private int score;
	
	@NotNull
	@Column(name = "LEVEL_ID")
	private int levelId;
	
	@NotNull
	@Column(name = "CREATION_TIME")
	private Date creationTime;

	@Column(name = "LAST_UPDATED")
	private Date lastUpdatedTime;
	
	public UsersScore() {
		super();
	}

	public UsersScore(int userId, int score, int levelId, Date creationTime) {
		super();
		this.userId = userId;
		this.score = score;
		this.creationTime = creationTime;
		this.levelId = levelId;
	}

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
}
