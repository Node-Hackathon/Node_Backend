package com.example.nodebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NodeBackendApplication {
    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }
    public static void main(String[] args) {
        SpringApplication.run(NodeBackendApplication.class, args);
    }

}
