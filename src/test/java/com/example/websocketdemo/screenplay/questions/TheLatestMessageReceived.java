package com.example.websocketdemo.screenplay.questions;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.abilities.ConnectToChatViaWebsocket;
import com.example.websocketdemo.screenplay.actor.Memories;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class TheLatestMessageReceived extends QuestionWithDefaultSubject<ChatMessage> {
  @Override
  public ChatMessage answeredBy(Actor actor) {
    if (actor.abilityTo(ConnectToChatViaWebsocket.class) != null) {
      return actor.recall(Memories.LATEST_CHAT_MESSAGE);
    }
    if (actor.abilityTo(BrowseTheWeb.class) != null) {
      List<WebElementFacade> chat_messages = Target.the("chat messages")
          .locatedBy("#messageArea li")
          .resolveAllFor(actor);
      WebElementFacade message = chat_messages.get(chat_messages.size() - 1);
      if (message.containsText(" joined!")) {
        return ChatMessage.join(StringUtils.left(message.getText(), message.getText().length() - " joined!".length()));
      }
      return ChatMessage.chat(message.thenFind("span").getText(), message.thenFind("p").getText());
    }

    throw new NoMatchingAbilityException(this.getClass().getSimpleName());
  }

}
