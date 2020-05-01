package com.example.websocketdemo;

import com.example.websocketdemo.feature.ChatScenarios;
import com.example.websocketdemo.screenplay.abilities.ConnectToChatViaWebsocket;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.Cast;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@RunWith(SerenityRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmbeddedServerIntegrationTest {

  @Rule
  public SpringIntegrationMethodRule springIntegrationMethodRule = new SpringIntegrationMethodRule();

  @LocalServerPort
  int port;

  private Cast cast = Cast.whereEveryoneCan(new ConnectToChatViaWebsocket());
  private Actor dana = cast.actorNamed("Dana");
  private Actor bob = cast.actorNamed("Bob");

  @Before
  public void setUp() {
    cast.getActors().forEach(a -> a.remember("websocket.url", "ws://localhost:" + port + "/ws"));
  }

  @Test
  public void whenActorsJoinTheChatTheyShouldSeeThatTheyAreConnected() {
    ChatScenarios.whenActorsJoinTheChatTheyShouldSeeThatTheyAreConnected(dana);
  }
}
