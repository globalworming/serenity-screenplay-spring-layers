package com.example.websocketdemo.screenplay.actions;

import com.example.websocketdemo.screenplay.abilities.UseTheServiceLayer;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class EnableSwearWordFilter implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    UseTheServiceLayer.as(actor).getChatController().enableSwearWordFilter();
  }

}
