//package com.SCM.config;
//
//import java.security.Principal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessagingException;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//
//import com.SCM.serviceimpl.UserDetailsServiceImpl;
//
//@Component
//public class StompAuthChannelInterceptor implements ChannelInterceptor {
//
//    @Autowired
//    private com.SCM.jwt.JwtUtils jwtUtils;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService; // adapt to your service
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor =
//            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//        if (accessor == null) {
//            return message;
//        }
//
//        // Only handle CONNECT command
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String authHeader = accessor.getFirstNativeHeader("Authorization");
//            if (authHeader == null) {
//                authHeader = accessor.getFirstNativeHeader("authorization"); // just in case
//            }
//
//            if (authHeader == null || authHeader.isBlank()) {
//                // no token -> reject connection (optional) or allow anonymous
//                throw new MessagingException("Missing Authorization header in STOMP CONNECT");
//            }
//
//            String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
//            try {
//                if (jwtUtils.validateJwtToken(token)) {
//                    String username = jwtUtils.getUserNameFromJwtToken(token);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    userDetails, null, userDetails.getAuthorities());
//
//                    // set the user/principal for the WebSocket session
//                    accessor.setUser(authentication);
//                } else {
//                    throw new MessagingException("Invalid JWT token");
//                }
//            } catch (Exception ex) {
//                // you can log and reject
//                throw new MessagingException("STOMP authentication failed: " + ex.getMessage());
//            }
//        }
//        return message;
//    }
//}
