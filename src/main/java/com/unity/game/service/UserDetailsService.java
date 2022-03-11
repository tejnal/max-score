package com.unity.game.service;

import org.springframework.stereotype.Service;

import com.unity.game.payload.response.LoginResponse;

public interface UserDetailsService {
	public String createNewUser(int userId) throws Exception;
	public LoginResponse fetchExistingUserDetails(int userId);
}
