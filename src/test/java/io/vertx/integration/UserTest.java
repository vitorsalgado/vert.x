package io.vertx.integration;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(VertxUnitRunner.class)
public class UserTest {
    private Vertx vertx;

    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = VertxHelper.init(context);
    }

    @After
    public void tearDown(TestContext context) {
        //VertxHelper.finish(context);
    }

    @Test
    public void saveUser(TestContext context) {
        final Async async = context.async();

        User user = new User("vitor", "email@email.com.br");

        final String json = Json.encodePrettily(user);
        final String length = Integer.toString(json.length());

        vertx.createHttpClient().post(8080, "localhost", "/user")
                .putHeader("content-type", "application/json")
                .putHeader("content-length", length)
                .handler(response -> {
                    context.assertEquals(response.statusCode(), HttpResponseStatus.CREATED.code());
                    context.assertTrue(response.headers().get("content-type").contains("application/json"));

                    response.handler(body -> {
                        final User usr = Json.decodeValue(body.toString(), User.class);

                        context.assertEquals(usr.getName(), "vitor");
                        context.assertEquals(usr.getEmail(), "email@email.com.br");
                        context.assertNotNull(usr.getId());

                        async.complete();
                    });
                })
                .write(json)
                .end();

        vertx.createHttpClient().post(8080, "localhost", "/user")
                .putHeader("content-type", "application/json")
                .putHeader("content-length", length)
                .handler(response -> {
                    context.assertEquals(response.statusCode(), HttpResponseStatus.CREATED.code());
                    context.assertTrue(response.headers().get("content-type").contains("application/json"));

                    response.handler(body -> {
                        final User usr = Json.decodeValue(body.toString(), User.class);

                        context.assertEquals(usr.getName(), "vitor");
                        context.assertEquals(usr.getEmail(), "email@email.com.br");
                        context.assertNotNull(usr.getId());

                        async.complete();
                    });
                })
                .write(json)
                .end();
    }
}
