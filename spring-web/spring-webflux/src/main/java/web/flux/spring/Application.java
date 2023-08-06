package web.flux.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.http.server.HttpServer;
import web.flux.spring.config.WebFluxConfig;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebFluxConfig.class);
        HttpServer server = context.getBean(HttpServer.class);
        server.bindNow();
        System.out.println("Server started");
        // Do not close the context, the server will keep running
    }
}
