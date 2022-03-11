package com.unity.game.tests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.unity.game.models.Users;
import com.unity.game.repository.UsersRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersRepositoryTest {
	@Autowired    
	TestEntityManager entityManager;
	
	@Autowired    
	UsersRepository usersRepository; 
	
	@Test    
	public void it_should_save_user_and_validate_by_user_id() 
	{        
		Users user = new Users();        
		user.setCreationTime(new Date());
		user.setUserId(0);
		user.setSessionkey("0_DUMMY_SESSION_KEY");
		user = entityManager.persistAndFlush(user);        
		assertThat(usersRepository.findByUserId(user.getUserId()).get()).isEqualTo(user); 
	}
	
	@Test    
	public void it_should_save_user_and_validate_by_session_key() 
	{        
		Users user = new Users();        
		user.setCreationTime(new Date());
		user.setUserId(0);
		user.setSessionkey("0_DUMMY_SESSION_KEY");
		user = entityManager.persistAndFlush(user);
		assertThat(usersRepository.findBySessionkey(user.getSessionkey()).get()).isEqualTo(user);  
	}
}
