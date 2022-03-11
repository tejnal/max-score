package com.unity.game.tests.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unity.game.MaxScoreGameInvoker;
import com.unity.game.controllers.ScoreController;
import com.unity.game.tests.config.SpringTestConfig;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        MaxScoreGameInvoker.class,
        SpringTestConfig.class
})
public class ScoreControllerTest {
	@Autowired
	private ScoreController scoreController;
	
	@Test
    public void shouldReceive200Response() throws Exception {
		// given
        MockMvcRequestSpecification givenRestAssuredSpecification = RestAssuredMockMvc.given()
                .standaloneSetup(scoreController).body("1500").contentType(MediaType.TEXT_PLAIN_VALUE);

        // when
		MockMvcResponse response = givenRestAssuredSpecification.when()
				.post("/0/score?sessionKey=0_DUMMY_SESSION_KEY");

        // then
        response.then().statusCode(200);
	}
	
	@Test
    public void shouldReceive415mediaUnsupportedResponse() throws Exception {
		// given
        MockMvcRequestSpecification givenRestAssuredSpecification = RestAssuredMockMvc.given()
                .standaloneSetup(scoreController).body("1500");

        // when
		MockMvcResponse response = givenRestAssuredSpecification.when()
				.post("/0/score?sessionKey=0_DUMMY_SESSION_KEY");

        // then
        response.then().statusCode(415);
	}
	
	@Test
    public void shouldReceive400ErrorResponse() throws Exception {
		// given
        MockMvcRequestSpecification givenRestAssuredSpecification = RestAssuredMockMvc.given()
                .standaloneSetup(scoreController).body("1500").contentType(MediaType.TEXT_PLAIN_VALUE);

        // when
		MockMvcResponse response = givenRestAssuredSpecification.when()
				.post("/0/score");

        // then
        response.then().statusCode(400);
	}
	
}
