package com.unity.game.tests.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unity.game.MaxScoreGameInvoker;
import com.unity.game.controllers.LoginController;
import com.unity.game.tests.config.SpringTestConfig;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        MaxScoreGameInvoker.class,
        SpringTestConfig.class
})
public class LoginControllerTest {
	@Autowired
	private LoginController loginController;
	
	@Test
    public void shouldReceive200Response() throws Exception {
		// given
        MockMvcRequestSpecification givenRestAssuredSpecification = RestAssuredMockMvc.given()
                .standaloneSetup(loginController);

        // when
		MockMvcResponse response = givenRestAssuredSpecification.when().get("/{userId}/login", 0);
		System.out.println(response.asString());

        // then
        response.then().statusCode(200);
	}	
}

