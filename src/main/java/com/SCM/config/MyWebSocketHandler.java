package com.SCM.config;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.SCM.dto.StaffLiveLocationDto;
import com.SCM.dto.StaffLocationDto;
import com.SCM.model.Staff;
import com.SCM.model.StaffLocation;
import com.SCM.repository.StaffLocationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    // 🔹 Active sessions (thread safe)
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    // 🔹 ObjectMapper with JavaTimeModule
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Autowired
    private StaffLocationRepo staffLocationRepo;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("✅ Native WS connected: " + session.getId());
        System.out.println("📡 Active sessions: " + sessions.size());
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        try {
            StaffLiveLocationDto dto = mapper.readValue(message.getPayload(), StaffLiveLocationDto.class);
          System.out.println("📩 Native WS received: " + dto);

            System.out.println("📩 Staff id : " + dto.getStaffid());

            // 🔹 Find existing location
            StaffLocation staffLocation = staffLocationRepo
                    .findByStaffId(dto.getStaffid())
                    .orElse(new StaffLocation()); // if not found → create new object

            // 🔹 Set Staff (important)
            Staff staff = new Staff();
            staff.setId(dto.getStaffid());
            staffLocation.setStaff(staff);

            // 🔹 Update fields (for both insert + update)
            staffLocation.setLatitude(dto.getLattitude());
            staffLocation.setLongitude(dto.getLongitude());
            staffLocation.setLastUpdated(LocalDateTime.now());

            // 🔹 Save → JPA will UPDATE if exists, INSERT if not
            staffLocationRepo.save(staffLocation);

            // 🔹 Send updated data to frontend
            StaffLocationDto response = new StaffLocationDto();
            response.setStaffId(dto.getStaffid());
            response.setLatitude(staffLocation.getLatitude());
            response.setLongitude(staffLocation.getLongitude());
            response.setLastUpdated(staffLocation.getLastUpdated().toString());

            String jsonResponse = mapper.writeValueAsString(response);

            synchronized (sessions) {
                Iterator<WebSocketSession> it = sessions.iterator();
                while (it.hasNext()) {
                    WebSocketSession s = it.next();
                    if (!s.isOpen()) {
                        it.remove();
                        continue;
                    }
                    s.sendMessage(new TextMessage(jsonResponse));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("❌ Error: " + e.getMessage()));
        }
    }


    
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        try {
//            // 🔹 Parse incoming JSON into DTO
//            StaffLiveLocationDto dto = mapper.readValue(message.getPayload(), StaffLiveLocationDto.class);
//            System.out.println("📩 Native WS received: " + dto);
//            System.out.println("📩 Staff id : " + dto.getStaffid());
//            
//            // 🔹 Update DB
//            staffLocationRepo.updateStaffLocationWebSocket(dto.getLattitude(), dto.getLongitude(), dto.getStaffid());
//
//            // 🔹 Fetch updated staff location
//            StaffLocation staffLocation = staffLocationRepo.findByStaffId(dto.getStaffid()).orElse(null);
//
//            if (staffLocation != null) {
//                // 🔹 Prepare safe DTO for frontend
//                StaffLocationDto response = new StaffLocationDto();
//                response.setStaffId(staffLocation.getStaff().getId());
//                response.setLatitude(staffLocation.getLatitude());
//                response.setLongitude(staffLocation.getLongitude());
//                response.setLastUpdated(
//                        staffLocation.getLastUpdated() != null ? staffLocation.getLastUpdated().toString() : null
//                );
//
//                String jsonResponse = mapper.writeValueAsString(response);
//
//                // 🔹 Broadcast to all active sessions
//                synchronized (sessions) {
//                    Iterator<WebSocketSession> it = sessions.iterator();
//                    while (it.hasNext()) {
//                        WebSocketSession s = it.next();
//                        if (!s.isOpen()) {
//                            it.remove(); // remove closed sessions
//                            continue;
//                        }
//                        s.sendMessage(new TextMessage(jsonResponse));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.sendMessage(new TextMessage("❌ Error: " + e.getMessage()));
//        }
//    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("❌ Native WS closed: " + session.getId());
        System.out.println("📡 Active sessions: " + sessions.size());
    }
}



//@Component
//public class MyWebSocketHandler extends TextWebSocketHandler {
//	
//	  private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
//	  private final ObjectMapper mapper = new ObjectMapper()
//		        .registerModule(new JavaTimeModule())
//		        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//	    @Autowired
//	    private StaffLocationRepo staffLocationRepo;
//
////	    private final ObjectMapper mapper = new ObjectMapper();
//
//	    @Override
//	    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//	        sessions.add(session);
//	        
//
//	        System.out.println("✅ Native WS connected: " + session.getId());
//	    }
//
//	    @Override
//	    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//	    	try {
//	            // Parse incoming JSON into DTO
//	            StaffLiveLocationDto dto = mapper.readValue(message.getPayload(), StaffLiveLocationDto.class);
//	            System.out.println("📩 Native WS received: " + dto);
//
//	            // Update DB
//	            staffLocationRepo.updateStaffLocationWebSocket(dto.getLattitude(), dto.getLongitude(), dto.getStaffid());
//
//	            StaffLocation staffLocation = staffLocationRepo.findByStaffId(dto.getStaffid()).orElse(null);
//
//	            // Broadcast updated location as DTO (safe for frontend)
//	            if (staffLocation != null) {
//	                StaffLocationDto response = new StaffLocationDto();
//	                response.setStaffId(staffLocation.getStaff().getId());
//	                response.setLatitude(staffLocation.getLatitude());
//	                response.setLongitude(staffLocation.getLongitude());
//	                response.setLastUpdated(
//	                    staffLocation.getLastUpdated() != null ? staffLocation.getLastUpdated().toString() : null
//	                );
//
//	                String jsonResponse = mapper.writeValueAsString(response);
//
//	                synchronized (sessions) {
//	                    for (WebSocketSession s : sessions) {
//	                        if (s.isOpen()) {
//	                            s.sendMessage(new TextMessage(jsonResponse));
//	                        }
//	                    }
//	                }
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            session.sendMessage(new TextMessage("❌ Error: " + e.getMessage()));
//	        }
//	    }
//
//	    @Override
//	    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//	        sessions.remove(session);
//	        System.out.println("❌ Native WS closed: " + session.getId());
//	    }
//	    
//	    
//
////    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.add(session);
////        System.out.println("✅ WS connected: " + session.getId());
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        System.out.println("📩 Received from " + session.getId() + ": " + payload);
////
////        // example: echo back or broadcast to all
////        synchronized (sessions) {
////            for (WebSocketSession s : sessions) {
////                if (s.isOpen()) {
////                    s.sendMessage(new TextMessage("Server received: " + payload));
////                }
////            }
////        }
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session);
////        System.out.println("❌ WS closed: " + session.getId() + " status: " + status);
////    }
//}