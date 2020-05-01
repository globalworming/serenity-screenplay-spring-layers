package com.example.websocketdemo.chat

import com.example.websocketdemo.model.ChatMessage
import com.example.websocketdemo.screenplay.actions.EnableSwearWordFilter
import com.example.websocketdemo.screenplay.actions.JoinChat
import com.example.websocketdemo.screenplay.actions.SendsChatMessage
import com.example.websocketdemo.screenplay.questions.TheLatestMessageReceived
import net.serenitybdd.screenplay.Actor

import net.serenitybdd.screenplay.EventualConsequence.eventually
import net.serenitybdd.screenplay.GivenWhenThen.seeThat
import org.hamcrest.core.IsEqual.equalTo

fun `when actors join the chat they should see that they are connected`(actor: Actor) {
  actor.attemptsTo(JoinChat())
  actor.should(eventually(seeThat(
      TheLatestMessageReceived(),
      equalTo(ChatMessage.join(actor.name)))))
}

fun `when another actor joins the chat, others are notified`(
    dana: Actor, bob: Actor
) {
  dana.attemptsTo(JoinChat())
  bob.attemptsTo(JoinChat())
  dana.should(eventually(seeThat(
      TheLatestMessageReceived(),
      equalTo(ChatMessage.join(bob.name)))))
}

fun `given swear word filter is enabled, when an actor sends an offensive message, it is filtered`(
    dana: Actor, bob: Actor, admin: Actor
) {
  `when another actor joins the chat, others are notified`(dana, bob)
  admin.attemptsTo(EnableSwearWordFilter())
  dana.attemptsTo(SendsChatMessage.saying("fuck"))
  bob.should(eventually(seeThat(
      TheLatestMessageReceived(), equalTo(ChatMessage.chat(dana.name, "***")))))

}


