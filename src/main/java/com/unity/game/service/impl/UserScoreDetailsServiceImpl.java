package com.unity.game.service.impl;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unity.game.models.Users;
import com.unity.game.models.UsersScore;
import com.unity.game.repository.UsersRepository;
import com.unity.game.repository.UsersScoreRepository;
import com.unity.game.service.UserScoreDetailsService;
import com.unity.game.utils.CommonUtils;
import com.unity.game.utils.GenericConstants;

@Service
public class UserScoreDetailsServiceImpl implements UserScoreDetailsService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserScoreDetailsServiceImpl.class);
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private UsersScoreRepository usersScoreRepository;
	
	/**
	 * 0 - Success
	 * 1 - User not present
	 * 2 - Session expired
	 * 3 - Other errors
	 */
	@Override
	public int commitScore(int levelId, String sessionKey, int score) {
		int returnVal = 1;
		try {
			Users currentUser = usersRepository.findBySessionkey(sessionKey).get();
			if(null != currentUser) {
				if(null != currentUser.getCreationTime() && CommonUtils.isSessionActive(currentUser.getCreationTime(), 10)) {
					//Identify existing levelId Record
					UsersScore usersScore = null;
					try {
						usersScore = usersScoreRepository.findByUserIdAndLevelId(currentUser.getUserId(), levelId).get();
					} catch (NoSuchElementException e) {
						logger.info("No existing record for given level and user, proceeding with creating one.");
					}
					
					//Populate id no existing record
					if(usersScore == null) {
						usersScore = new UsersScore(currentUser.getUserId(), score, levelId, new Date());
					} else {
						usersScore.setScore(score);
						usersScore.setLastUpdatedTime(new Date());
					}
					
					//Commit the score to DB
					usersScoreRepository.save(usersScore);
					returnVal = 0;
				} else {
					returnVal = 2;
				}
			}
		} catch (Exception e) {
			logger.error("Error occured while committing the score | ", e);
			returnVal = 3;
		}
		
		return returnVal;
	}

	@Override
	public String fetchMaxScoreListUsersByLevelId(int levelId) throws Exception {
		String concatenatedUsersScoreList = null;
		try {
			concatenatedUsersScoreList = usersScoreRepository.fetchHighScoresByLevelId(levelId).stream().map((Function<? super UsersScore, ? extends String>) (eachUserScore) -> {
				return String.valueOf(eachUserScore.getUserId()).concat(GenericConstants.CONS_SYMBOL_EQUALS).concat(String.valueOf(eachUserScore.getScore()));
			}).collect(Collectors.joining(GenericConstants.CONS_SYMBOL_COMMA));
		} catch (Exception e) {
			logger.error("Error occured while populating user max score at a level | ", e);
			throw e;
		}
		return concatenatedUsersScoreList;
	}
}
