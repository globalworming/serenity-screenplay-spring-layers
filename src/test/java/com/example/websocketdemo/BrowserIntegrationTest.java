package com.example.websocketdemo;

import com.example.websocketdemo.feature.ChatScenarios;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@RunWith(SerenityRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BrowserIntegrationTest {

  @Rule
  public SpringIntegrationMethodRule springIntegrationMethodRule = new SpringIntegrationMethodRule();

  @LocalServerPort
  int port;

  private Cast cast = new OnlineCast();
  private Actor dana = cast.actorNamed("Dana");
  private Actor bob = cast.actorNamed("Bob");


  EnvironmentVariables environmentVariables;

  @Before
  public void setUp() {
    environmentVariables.setProperty("webdriver.base.url", "http://localhost:"+ port);
  }

  @Test
  public void whenActorsJoinTheChatTheyShouldSeeThatTheyAreConnected() {
    ChatScenarios.whenActorsJoinTheChatTheyShouldSeeThatTheyAreConnected(dana);
  }

}
