package io.takima.demo.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            FileInputStream refreshToken = new FileInputStream("src/main/java/io/takima/demo/onlinecv-83159-firebase-adminsdk-nofc8-1f5165cafe.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://onlinecv-83159.firebaseio.com/")
                    .build();

            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

            System.out.println(defaultApp.getName());  // "[DEFAULT]"

// Retrieve services by passing the defaultApp variable...
            FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

// ... or use the equivalent shorthand notation
            defaultAuth = FirebaseAuth.getInstance();
            defaultDatabase = FirebaseDatabase.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}