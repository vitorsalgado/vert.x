//package io.vertx.integration;
//
//import io.vertx.core.Vertx;
//import io.vertx.ext.unit.Async;
//import io.vertx.ext.unit.TestContext;
//import io.vertx.ext.unit.junit.VertxUnitRunner;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//
//@RunWith(VertxUnitRunner.class)
//public class HealthCheckTest {
//    private Vertx vertx;
//
//    @Before
//    public void setUp(TestContext context) throws IOException {
//        vertx = VertxHelper.init(context);
//    }
//
//    @After
//    public void tearDown(TestContext context) {
//        //VertxHelper.finish(context);
//    }
//
//    @Test
//    public void healthCheckShouldReturnOk(TestContext context) {
//        final Async async = context.async();
//
//        vertx.createHttpClient().getNow(8080, "localhost", "/check",
//                response ->
//                        response.handler(body -> {
//                            context.assertTrue(body.toString().contains("ok"));
//                            context.assertTrue(response.headers().get("content-type").contains("text/plain"));
//
//                            async.complete();
//                        }));
//    }
//}
