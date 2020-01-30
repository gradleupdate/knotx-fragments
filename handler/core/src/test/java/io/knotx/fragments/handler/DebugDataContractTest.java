package io.knotx.fragments.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.knotx.fragments.HoconLoader;
import io.knotx.junit5.util.FileReader;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
class DebugDataContractTest {

  @Test
  @DisplayName("Expect exception when consumer factory name is not found")
  void contractValidation(Vertx vertx, VertxTestContext testContext)
      throws Throwable {
    HoconLoader.verify("handler/debug/data-contract.conf", config -> {
      //given
      System.out.println(config.getJsonObject("graph"));
      JsonObject expectedJsonObject = new JsonObject(
          FileReader.readTextSafe("handler/debug/expected-contract.json"));
      assertEquals(expectedJsonObject, config.getJsonObject("graph"));
      testContext.completed();

    }, testContext, vertx);
  }

}
