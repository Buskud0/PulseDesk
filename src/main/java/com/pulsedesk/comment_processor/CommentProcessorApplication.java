package com.pulsedesk.comment_processor;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommentProcessorApplication {

    public static void main(String[] args) {
        Application.launch(PulseDeskApplication.class, args);
    }

}
