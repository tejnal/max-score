package com.unity.game.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
	@JsonProperty("user_id")
	private Integer userId;
	
	@JsonProperty("session_key")
	private String sessionkey;

	public LoginResponse(String sessionkey, Integer userId) {
		super();
		this.sessionkey = sessionkey;
		this.userId = userId;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
