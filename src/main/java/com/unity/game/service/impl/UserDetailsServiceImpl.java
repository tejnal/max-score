package com.unity.game.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unity.game.models.Users;
import com.unity.game.payload.response.LoginResponse;
import com.unity.game.repository.UsersRepository;
import com.unity.game.service.UserDetailsService;
import com.unity.game.utils.CommonUtils;
import com.unity.game.utils.GenericConstants;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public LoginResponse fetchExistingUserDetails(int userId) {
		List<LoginResponse> responses = usersRepository.findByUserId(userId).stream().map((Function<? super Users, ? extends LoginResponse>) (eachUser) -> {
			LoginResponse loginResponse = new LoginResponse(eachUser.getSessionkey(), eachUser.getUserId());
			//Set the session key to null, in case session is not active
			if(!CommonUtils.isSessionActive(eachUser.getCreationTime(), 10)) {
				loginResponse.setSessionkey(null);
			}
			return loginResponse;
		}).collect(Collectors.toList());
		
		if(!responses.isEmpty()) {
			return responses.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String createNewUser(int userId) throws Exception {
		try {
			String sessionIdGenerated = String.valueOf(userId).concat(GenericConstants.CONS_SYMBOL_UNDERSCORE).concat(CommonUtils.generateUniqueKeyUsingMessageDigest());
			Users savedUser = usersRepository.save(new Users(userId,sessionIdGenerated, new Date()));
			return savedUser.getSessionkey();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error while creating user - NoSuchAlgorithmException | ", e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			logger.error("Error while creating user - UnsupportedEncodingException | ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error while creating user - Exception | ", e);
			throw e;
		}
	}

	
}	
