package com.unity.game.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unity.game.models.UsersScore;

@Repository
public interface UsersScoreRepository extends JpaRepository<UsersScore, Long> {
	@Query("from UsersScore a WHERE a.levelId=:levelId AND a.userId = :userId")
	Optional<UsersScore> findByUserIdAndLevelId(@Param("userId") int userId, @Param("levelId") int levelId);
	
	@Query("from UsersScore a WHERE a.levelId=:levelId order by a.score DESC")
	public List<UsersScore> fetchHighScoresByLevelId(@Param("levelId") int levelId);
	
}

