package com.unity.game.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unity.game.service.UserScoreDetailsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/")
public class ScoreController {
	
	@Autowired
	UserScoreDetailsService userScoreDetailsService;

	@PostMapping(path = "/{levelId}/score", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> postUserScoreToLevel(@Valid @NotNull @PathVariable(name = "levelId") int levelId, 
			@Valid @NotBlank @RequestParam(required = true, name = "sessionKey") String sessionKey,
			@Valid @NotNull @RequestBody(required = true) String score) {
		//Check whether the session is still within 10 minutes, and identify the user (if identified save the score and return 1, else return 0)
		int scoreInt = 0;
		try {
			scoreInt = Integer.parseInt(score);
		} catch (NumberFormatException nfe ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		int commitResponse = userScoreDetailsService.commitScore(levelId, sessionKey, scoreInt);
		
		//If 0 not received the user is not present or session key expired
		if(commitResponse != 0) {
			//Return an error
			return (commitResponse == 3) ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
					: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		//Return a score
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/{levelId}/highscorelist")
	public ResponseEntity<?> getHighScoreForLevel(@Valid @NotNull @PathVariable(name = "levelId") int levelId) {
		String maxScoreUsersList = null;
		try {
			maxScoreUsersList = userScoreDetailsService.fetchMaxScoreListUsersByLevelId(levelId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		//If the user is not present or session key expired
		if(null == maxScoreUsersList || maxScoreUsersList.isBlank()) {
			//Return an error
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		//Return a score
		return ResponseEntity.ok(maxScoreUsersList);
	}
}
