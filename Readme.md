### same steps for different UIs with Serenity BDD

With the screenplay pattern it is possible to use the same test steps with different implementations (controlled by abilities) to test different UIs. Especially with spring you can run e2e tests against the service layer directly, against the web layer or against the browser.
This example is one way of leveraging actors and abilities.
I've implemented the websocket and browser side of things.  
[BrowserIT](https://github.com/globalworming/serenity-screenplay-spring-layers/blob/master/src/test/kotlin/com/example/websocketdemo/e2e/chat/BrowserIT.kt)  
[WebsocketIT](https://github.com/globalworming/serenity-screenplay-spring-layers/blob/master/src/test/kotlin/com/example/websocketdemo/e2e/chat/WebsocketIT.kt)  
both use the steps defined in the [ChatScenarios](src/test/kotlin/com/example/websocketdemo/chat/ChatScenarios.kt) so you got a single file where you can describe intended behavior. Looks a little un-DRY but thhttps://github.com/globalworming/serenity-screenplay-spring-layers/blob/master/src/test/kotlin/com/example/websocketdemo/e2e/chat/BrowserIT.ktis way you can create a single report for both UIs and mark specific tests as pending or ignored or don't implement them at all. The alternative might be a UI parameter and using `Assume` to decide if a test should run. See [BotcHappyPathIT.kt](https://github.com/globalworming/botc-e2e/blob/v0.1.1/src/test/kotlin/com/headissue/botc_unofficial/e2e/test/happy/BotcHappyPathIT.kt) for an example how this could look like.

_Is it fancy?_ I guess.  
_Is it easy?_ I guess not so much.   
_Is it useful?_ Maybe? Why start automated tests against a browser when you are not sure that the backend is behaving as intended. And when you have more UIs (like REST, CLI...) you can with a single step description ensure the same behavior over all UIs.


circleci build [![CircleCI](https://circleci.com/gh/globalworming/serenity-screenplay-spring-layers/tree/master.svg?style=svg)](https://circleci.com/gh/globalworming/serenity-screenplay-spring-layers/tree/master)

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Chrome


## Run the tests

```bash
mvn clean verify
```
