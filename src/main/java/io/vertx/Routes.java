package io.vertx;

import io.vertx.controllers.HealthController;
import io.vertx.controllers.UserController;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

final class Routes {
    static Router buildRoutes(Vertx vertx) {
        UserController userController = new UserController();
        HealthController healthController = new HealthController();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/check").handler(healthController::check);

        router.get("/user/:id").handler(userController::get);
        router.post("/user").handler(userController::add);

        return router;
    }
}
