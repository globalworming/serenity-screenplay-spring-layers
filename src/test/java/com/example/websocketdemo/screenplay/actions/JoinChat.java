package com.example.websocketdemo.screenplay.actions;

import com.example.websocketdemo.screenplay.page.StartPage;
import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.abilities.ConnectToChatViaWebsocket;
import com.example.websocketdemo.screenplay.actor.Memories;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.Keys;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JoinChat implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    if (actor.abilityTo(ConnectToChatViaWebsocket.class) != null) {
      connectViaWebsocket(actor);
      return;
    }
    if (actor.abilityTo(BrowseTheWeb.class) != null) {
      actor.attemptsTo(Open.browserOn(new StartPage()));
      actor.attemptsTo(Enter.theValue(actor.getName())
          .into(".e2e-name-input")
          .thenHit(Keys.ENTER));
      return;
    }
    throw new NoMatchingAbilityException(this.getClass().getSimpleName());
  }


  private <T extends Actor> void connectViaWebsocket(T actor) {
    WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
    List<Transport> transports = new ArrayList<>(1);
    transports.add(new WebSocketTransport(simpleWebSocketClient));
    SockJsClient sockJsClient = new SockJsClient(transports);
    WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    String url = actor.recall("websocket.url");
    MyStompSessionHandler sessionHandler = new MyStompSessionHandler(actor);
    try {
      stompClient.connect(url, sessionHandler).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private class MyStompSessionHandler extends StompSessionHandlerAdapter {


    private final Actor actor;
    private StompSession session;

    public <T extends Actor> MyStompSessionHandler(T actor) {
      this.actor = actor;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
      session.subscribe("/topic/public", this);
      session.send("/app/chat.addUser", ChatMessage.join(actor.getName()));
      this.session = session;
      actor.remember(Memories.WEBSOCKET_SESSION, session);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
      return ChatMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
      actor.remember(Memories.LATEST_CHAT_MESSAGE, payload);
    }

  }
}
