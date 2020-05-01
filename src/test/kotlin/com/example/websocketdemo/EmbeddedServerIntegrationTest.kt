package com.example.websocketdemo

import com.example.websocketdemo.controller.ChatController
import com.example.websocketdemo.screenplay.abilities.ConnectToChatViaWebsocket
import com.example.websocketdemo.screenplay.abilities.UseTheServiceLayer
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule
import net.serenitybdd.screenplay.actors.Cast
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.annotation.DirtiesContext

@RunWith(SerenityRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmbeddedServerIntegrationTest {

  @Rule @JvmField
  var springIntegrationMethodRule = SpringIntegrationMethodRule()

  @LocalServerPort
  internal var port: Int = 0

  @Autowired
  private lateinit var chatController: ChatController

  private val cast = Cast.whereEveryoneCan(ConnectToChatViaWebsocket())
  private val dana = cast.actorNamed("Dana")
  private val bob = cast.actorNamed("Bob")
  private val admin = cast.actorNamed("Admin")


  @Before
  fun setUp() {
    admin.can(UseTheServiceLayer(chatController))
    cast.actors.forEach { a -> a.remember("websocket.url", "ws://localhost:$port/ws") }
  }

  @Test
  fun `when actors join the chat they should see that they are connected`() {
    `when actors join the chat they should see that they are connected`(dana)
  }

  @Test
  fun `when another actor joins the chat, others are notified`() {
    `when another actor joins the chat, others are notified`(dana, bob)
  }

  @Test
  @DirtiesContext
  fun `given swear word filter is enabled, when an actor sends an offensive message, it is filtered`() {
    `given swear word filter is enabled, when an actor sends an offensive message, it is filtered`(dana, bob, admin)
  }

}
