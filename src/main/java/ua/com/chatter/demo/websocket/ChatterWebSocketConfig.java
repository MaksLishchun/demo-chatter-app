package ua.com.chatter.demo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;

@Configuration
@EnableWebSocket
public class ChatterWebSocketConfig implements WebSocketConfigurer {
    
    @Autowired
    @Qualifier("chatterWebSocketHandler")
    private final WebSocketHandler handler;

    public ChatterWebSocketConfig(@Lazy WebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        ExceptionWebSocketHandlerDecorator exceptionHandler = new ExceptionWebSocketHandlerDecorator(handler);
        registry.addHandler(exceptionHandler,  "/chat")
            .setAllowedOriginPatterns("*")
            .addInterceptors(new ChatterWebSocketInterceptor());
    }
}
