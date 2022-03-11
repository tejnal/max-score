package com.unity.game.tests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.unity.game.models.Users;
import com.unity.game.payload.response.LoginResponse;
import com.unity.game.repository.UsersRepository;
import com.unity.game.service.impl.UserDetailsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

	@Mock
	UsersRepository usersRepository;
	
	@InjectMocks
	UserDetailsServiceImpl userDetailsService;
	
	@Test
	public void when_commit_user_it_should_return_sessionId() throws Exception {
		Users dummyUser = new Users();
		dummyUser.setUserId(0);
		dummyUser.setSessionkey("0_DUMMY_SESSION");
		Mockito.when(usersRepository.save(Mockito.any(Users.class))).thenReturn(dummyUser);
		String sessionIdGenerated = userDetailsService.createNewUser(0);
		assertNotNull(sessionIdGenerated);
		assertThat(sessionIdGenerated).isSameAs(dummyUser.getSessionkey());
	}
	
	@Test
	public void when_retrieve_existing_user_details_return_login_response() throws Exception {
		Users dummyUser = new Users();
		dummyUser.setUserId(0);
		dummyUser.setSessionkey("0_DUMMY_SESSION");
		dummyUser.setCreationTime(new Date());
		Mockito.when(usersRepository.findByUserId(Mockito.anyInt())).thenReturn(Optional.ofNullable(dummyUser));
		LoginResponse fetchedLoginResponse = userDetailsService.fetchExistingUserDetails(0);
		assertThat(fetchedLoginResponse.getSessionkey()).isSameAs(dummyUser.getSessionkey());
		assertThat(fetchedLoginResponse.getUserId()).isSameAs(dummyUser.getUserId());
	}
}
