package com.unity.game.tests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.unity.game.models.UsersScore;
import com.unity.game.repository.UsersScoreRepository;
import com.unity.game.utils.GenericConstants;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersScoreRepositoryTest {
	@Autowired    
	TestEntityManager entityManager;
	
	@Autowired    
	UsersScoreRepository usersScoreRepository; 
	
	@Test    
	public void it_should_save_users_score() 
	{        
		UsersScore usersScore = new UsersScore();        
		usersScore.setCreationTime(new Date());
		usersScore.setLevelId(0);
		usersScore.setScore(1500);
		usersScore.setUserId(0);
		usersScore = entityManager.persistAndFlush(usersScore);        
		assertThat(usersScoreRepository.findByUserIdAndLevelId(usersScore.getUserId(), usersScore.getLevelId()).get()).isEqualTo(usersScore);
	}
	
	@Test    
	public void it_should_get_the_high_scores() 
	{               
		UsersScore usersScore = new UsersScore();        
		usersScore.setCreationTime(new Date());
		usersScore.setLevelId(0);
		usersScore.setScore(1500);
		usersScore.setUserId(0);
		usersScore = entityManager.persistAndFlush(usersScore);
		//Check for result from high scores
		assertThat(usersScoreRepository.fetchHighScoresByLevelId(usersScore.getLevelId()).stream().map((Function<? super UsersScore, ? extends String>) (eachUserScore) -> {
			return String.valueOf(eachUserScore.getUserId()).concat(GenericConstants.CONS_SYMBOL_EQUALS).concat(String.valueOf(eachUserScore.getScore()));
		}).collect(Collectors.joining(GenericConstants.CONS_SYMBOL_COMMA))).isEqualTo("0=1500");
	}
}
