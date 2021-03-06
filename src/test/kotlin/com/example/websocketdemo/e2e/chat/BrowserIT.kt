package com.example.websocketdemo.e2e.chat

import com.example.websocketdemo.controller.ChatController
import com.example.websocketdemo.screenplay.abilities.UseTheServiceLayer
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule
import net.serenitybdd.screenplay.actors.OnlineCast
import net.thucydides.core.util.EnvironmentVariables
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@RunWith(SerenityRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BrowserIT {

  @Rule
  @JvmField
  var springIntegrationMethodRule = SpringIntegrationMethodRule()

  @LocalServerPort
  internal var port: Int = 0

  @Autowired
  private lateinit var chatController: ChatController

  private val cast = OnlineCast()
  private val dana = cast.actorNamed("Dana")
  private val bob = cast.actorNamed("Bob")
  private val admin = cast.actorNamed("Admin")

  internal var environmentVariables: EnvironmentVariables? = null

  @Before
  fun setUp() {
    admin.can(UseTheServiceLayer(chatController))
    environmentVariables!!.setProperty("webdriver.base.url", "http://localhost:$port")
  }

  @Test
  fun `when actors join the chat they should see that they are connected`() {
    com.example.websocketdemo.`when actors join the chat they should see that they are connected`(dana)
  }

  @Test
  fun `when another actor joins the chat, others are notified`() {
    com.example.websocketdemo.`when another actor joins the chat, others are notified`(dana, bob)
  }

  @Test
  fun `given swear word filter is enabled, when an actor sends an offensive message, it is filtered`() {
    com.example.websocketdemo.`given swear word filter is enabled, when an actor sends an offensive message, it is filtered`(dana, bob, admin)
  }


}
