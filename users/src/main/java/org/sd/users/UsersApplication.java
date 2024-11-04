package org.sd.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersApplication {
    public static String sessionID;

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}
