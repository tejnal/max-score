package com.unity.game.tests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.unity.game.models.Users;
import com.unity.game.models.UsersScore;
import com.unity.game.repository.UsersRepository;
import com.unity.game.repository.UsersScoreRepository;
import com.unity.game.service.impl.UserScoreDetailsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserScoreDetailsServiceTest {

	@Mock
	UsersRepository usersRepository;
	
	@Mock
	UsersScoreRepository usersScoreRepository;
	
	@InjectMocks
	UserScoreDetailsServiceImpl userScoreDetailsServiceImpl;
	
	@Test
	public void when_list_max_score_by_level_id_return_user_equals_score_list() throws Exception {
		UsersScore dummyUserScore = new UsersScore();
		dummyUserScore.setUserId(0);
		dummyUserScore.setScore(1500);
		List<UsersScore> usersScoreList = new ArrayList<UsersScore>();
		usersScoreList.add(dummyUserScore);
		Mockito.when(usersScoreRepository.fetchHighScoresByLevelId(Mockito.anyInt())).thenReturn(usersScoreList);
		String userMaxScore = userScoreDetailsServiceImpl.fetchMaxScoreListUsersByLevelId(0);
		assertNotNull(userMaxScore);
		assertTrue(userMaxScore.contentEquals("0=1500"));
	}
	
	@Test
	public void when_commit_score_return_commit_status() throws Exception {
		Users dummyUser = new Users();
		dummyUser.setUserId(0);
		dummyUser.setSessionkey("0_DUMMY_SESSION");
		dummyUser.setCreationTime(new Date());
		
		UsersScore dummyUserScore = new UsersScore();
		dummyUserScore.setUserId(0);
		dummyUserScore.setScore(1500);
		List<UsersScore> usersScoreList = new ArrayList<UsersScore>();
		usersScoreList.add(dummyUserScore);
		Mockito.when(usersRepository.findBySessionkey(Mockito.anyString())).thenReturn(Optional.ofNullable(dummyUser));
		Mockito.when(usersScoreRepository.findByUserIdAndLevelId(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.ofNullable(dummyUserScore));
		int commitStatus = userScoreDetailsServiceImpl.commitScore(0, "0_DUMMY_SESSION", 1500);
		assertThat(commitStatus).isSameAs(0);
	}
	
	@Test
	public void when_commit_score_return_commit_status_2_when_session_Expired() throws Exception {
		Users dummyUser = new Users();
		dummyUser.setUserId(0);
		dummyUser.setSessionkey("0_DUMMY_SESSION");
		
		UsersScore dummyUserScore = new UsersScore();
		dummyUserScore.setUserId(0);
		dummyUserScore.setScore(1500);
		List<UsersScore> usersScoreList = new ArrayList<UsersScore>();
		usersScoreList.add(dummyUserScore);
		Mockito.when(usersRepository.findBySessionkey(Mockito.anyString())).thenReturn(Optional.ofNullable(dummyUser));
		int commitStatus = userScoreDetailsServiceImpl.commitScore(0, "0_DUMMY_SESSION", 1500);
		assertThat(commitStatus).isSameAs(2);
	}
}
