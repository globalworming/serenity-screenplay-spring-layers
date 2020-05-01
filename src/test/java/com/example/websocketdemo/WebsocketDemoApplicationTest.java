package com.example.websocketdemo;

import com.example.websocketdemo.controller.ChatController;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsocketDemoApplicationTest {

	@Autowired
	ChatController chatController;

	@LocalServerPort
	int port;


	@Test
	public void randomPort() {
		assertTrue(port > 0);
	}

	@Test
	public void contextLoads() {
		assertThat(chatController, IsNot.not(IsNull.nullValue()));
	}



}
