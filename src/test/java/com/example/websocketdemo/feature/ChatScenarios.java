package com.example.websocketdemo.feature;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.screenplay.actions.JoinChat;
import com.example.websocketdemo.screenplay.questions.TheLatestMessageReceived;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ChatScenarios {

  public static void whenActorsJoinTheChatTheyShouldSeeThatTheyAreConnected(Actor actor) {
    actor.attemptsTo(new JoinChat());
    actor.should(eventually(seeThat(
        new TheLatestMessageReceived(),
        equalTo(ChatMessage.join(actor.getName())))));
  }

}
