package com.unity.game.tests.config;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.unity.game.payload.response.LoginResponse;
import com.unity.game.service.impl.UserDetailsServiceImpl;
import com.unity.game.service.impl.UserScoreDetailsServiceImpl;
import com.unity.game.utils.GenericConstants;

@TestConfiguration
public class SpringTestConfig {
	private RestTemplate template = new RestTemplate();
	
	@Bean
	public RestTemplate restTemplate() {
		return template;
	}
	
	@Bean
    public UserScoreDetailsServiceImpl userScoreDetailsService() throws Exception {
		UserScoreDetailsServiceImpl userScoreDetailsService = Mockito.mock(UserScoreDetailsServiceImpl.class);
        Mockito.when(userScoreDetailsService.fetchMaxScoreListUsersByLevelId(Mockito.anyInt())).thenReturn(
                Stream.of("0=1500", "1=1000").map(String::new).collect(Collectors.joining(GenericConstants.CONS_SYMBOL_COMMA)));
        //Return success
        Mockito.when(userScoreDetailsService.commitScore(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt())).thenReturn(0);
        return userScoreDetailsService;
    }
	
	@Bean
    public UserDetailsServiceImpl userDetailsService() throws Exception {
		UserDetailsServiceImpl userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);
        Mockito.when(userDetailsService.fetchExistingUserDetails(Mockito.anyInt())).thenReturn(
                Stream.of("0,0_DUMMY_SESSION_KEY").map((Function<? super String, ? extends LoginResponse>) (eachData) -> {
                	return new LoginResponse(eachData.split(GenericConstants.CONS_SYMBOL_COMMA)[1], 
                			Integer.valueOf(eachData.split(GenericConstants.CONS_SYMBOL_COMMA)[0]));
                }).collect(Collectors.toList()).get(0));
        return userDetailsService;
    }
}
