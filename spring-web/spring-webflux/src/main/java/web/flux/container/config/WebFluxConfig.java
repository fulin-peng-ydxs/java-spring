package web.flux.container.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

@Configuration
public class WebFluxConfig {

    /**处理器：绑定路由
     * 2023/8/7-1:24
     * @author pengfulin
     */
    @Bean
    public HttpHandler httpHandler(RouterFunction<?> routerFunction) {
        return WebHttpHandlerBuilder.webHandler((WebHandler) RouterFunctions.toHttpHandler(routerFunction,
                        HandlerStrategies.builder().build()))
                .build();
    }

    /**处理器适配器-绑定处理器
     * 2023/8/7-1:25
     * @author pengfulin
     */
    @Bean
    public ReactorHttpHandlerAdapter httpHandlerAdapter(HttpHandler httpHandler) {
        return new ReactorHttpHandlerAdapter(httpHandler);
    }

    /**路由
     * 2023/8/7-1:25
     * @author pengfulin
     */
    @Bean
    public RouterFunction<?> route() {
        return RouterFunctions.route()
                .GET("/hello", request -> ServerResponse.ok().bodyValue("Hello, WebFlux!"))
                .build();
    }
    /**服务器-绑定处理器适配器
     * 2023/8/7-1:30
     * @author pengfulin
    */
    @Bean
    public HttpServer httpServer(ReactorHttpHandlerAdapter httpHandlerAdapter) {
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(httpHandlerAdapter);
        return httpServer;
    }
}
