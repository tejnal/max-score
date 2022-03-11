package com.unity.game.service;

public interface UserScoreDetailsService {
	public int commitScore(int levelId, String sessionKey, int score);
	public String fetchMaxScoreListUsersByLevelId(int levelId) throws Exception;
}
