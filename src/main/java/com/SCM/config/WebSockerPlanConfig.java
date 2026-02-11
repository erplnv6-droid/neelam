package com.SCM.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSockerPlanConfig implements WebSocketConfigurer {

	
	
	  @Autowired
	    private MyWebSocketHandler nativeLocationHandler;

	    @Override
	    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	        registry.addHandler(nativeLocationHandler, "/wsnative")
	                .setAllowedOriginPatterns("*");
	    }
	
}
