package com.example.websocketdemo.screenplay.abilities;

import com.example.websocketdemo.controller.ChatController;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class UseTheServiceLayer extends AbilityWithDefaultDescription implements RefersToActor {

  private final ChatController chatController;

  public UseTheServiceLayer(ChatController chatController) {
    this.chatController = chatController;
  }

  public static UseTheServiceLayer as(Actor actor) {
    if (actor.abilityTo(UseTheServiceLayer.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(UseTheServiceLayer.class).asActor(actor);
  }

  @Override
  public <T extends Ability> T asActor(Actor actor) {
    return (T) this;
  }

  public ChatController getChatController() {
    return chatController;
  }
}
