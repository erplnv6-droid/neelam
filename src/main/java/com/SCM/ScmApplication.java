package com.SCM;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;


@SpringBootApplication
public class ScmApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ScmApplication.class, args);
	}


	  @Bean
	  FirebaseMessaging firebaseMessaging() throws IOException {
	  GoogleCredentials googleCredentials=GoogleCredentials.fromStream(new
	  ClassPathResource(
	  "neelamlnvadmin-firebase-adminsdk-obuw7-17a739b80f.json").
	  getInputStream()); 
	  FirebaseOptions firebaseOptions=FirebaseOptions.builder()
	  .setCredentials(googleCredentials).build(); 
	  FirebaseApp firebaseApp=FirebaseApp.initializeApp(firebaseOptions);
	  return FirebaseMessaging.getInstance(firebaseApp); 
	  }
	  
	  

}
