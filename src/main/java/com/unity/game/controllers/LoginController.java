package com.unity.game.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.unity.game.payload.response.LoginResponse;
import com.unity.game.service.UserDetailsService;
import com.unity.game.utils.CommonUtils;
import com.unity.game.utils.GenericConstants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@GetMapping("/{userId}/login")
	public ResponseEntity<?> authenticateUser(@Valid @NotNull @PathVariable(name = "userId") int userId) {
		//Fetch session ID for the given user
		LoginResponse loginResponse = userDetailsService.fetchExistingUserDetails(userId);
		
		try {
			//Create the user, if not existing
			//Throw Internal Server Error, in case any error occurred during user creation
			if(null == loginResponse) {			
				String sessionKeyGenerated = userDetailsService.createNewUser(userId);
				loginResponse = new LoginResponse(sessionKeyGenerated, userId);					
			}
		
			//Populate the session ID, if not expired
			if(null == loginResponse.getSessionkey()) {
				String sessionId = String.valueOf(userId).concat(GenericConstants.CONS_SYMBOL_UNDERSCORE).concat(CommonUtils.generateUniqueKeyUsingMessageDigest());
				loginResponse.setSessionkey(sessionId);
			}
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}	
		
		//Return the response as <sessionKey> only
		return ResponseEntity.ok(loginResponse.getSessionkey());
	}
}
