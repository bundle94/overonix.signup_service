package com.overonix.signup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overonix.signup.model.SignupRequest;
import com.overonix.signup.model.SignupResponse;
import com.overonix.signup.repository.PlayerEntity;
import com.overonix.signup.repository.PlayerRepository;
import com.overonix.signup.service.SignupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
public  class SignupApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SignupService signupService;

	PlayerEntity player = new PlayerEntity();

	@BeforeEach
	public void setUp() {
		player.setEmail("akobundumichael94@gmail.com");
		player.setUuid("e7beb475-5479-4d83-a99d-5e1a3c5afeb5");
		player.setPassword("easypeasy");
		playerRepository.save(player);
	}

	@AfterEach
	public void destroy() {
		//delete the saved record to avoid flooding our database
		playerRepository.deleteById(player.getId());
	}

	@Test
	void contextLoads() {
	}

	@Test
	void signupCallWithValidParameters() throws Exception {

		SignupRequest request = new SignupRequest("testplayeremail@overonix.com", "Password123");
		mockMvc.perform(post("/api/v1/signup")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());

	}

	@Test
	void signupCallWithInvalidURL() throws Exception {

		SignupRequest request = new SignupRequest("testplayeremail@overonix.com", "Password123");
		mockMvc.perform(post("/api/v1/signin")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isNotFound());

	}

	@Test
	void queryByUuid_callWithUUIDThatExists_returnsPlayerRecord() throws Exception {

		SignupRequest request = new SignupRequest("testplayeremail@overonix.com", "Password123");
		mockMvc.perform(get("/api/v1/fetch/e7beb475-5479-4d83-a99d-5e1a3c5afeb5"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.email").value("akobundumichael94@gmail.com"));

	}

	@Test
	void queryByUuid_callWithUUIDThatDoesNotExist_returnsMessage() throws Exception {

		SignupRequest request = new SignupRequest("testplayeremail@overonix.com", "Password123");
		mockMvc.perform(get("/api/v1/fetch/d8ceb475-9979-4d83-a99d-7e1a4c6afeb6"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.message").value("No player record found"));

	}

	@Test
	void queryByUuid_withValidPArameter_returnsSignupResponse() throws Exception {
		String uuid = "e7beb475-5479-4d83-a99d-5e1a3c5afeb5";

		SignupResponse res = signupService.queryByUuid(uuid);

		assertEquals(res.getEmail(), "akobundumichael94@gmail.com");
		assertEquals(res.getUuid(), UUID.fromString("e7beb475-5479-4d83-a99d-5e1a3c5afeb5"));
	}

	@Test
	void queryByUuid_withUuidThatDoesNotExist_returnsErrorMessage() throws Exception {
		String uuid = "e7beb475-5479-7a83-a99d-5d1a2b5cffd8";

		SignupResponse res = signupService.queryByUuid(uuid);

		assertNotNull(res.getMessage());
		assertEquals(res.getMessage(), "No player record found");
	}



}
