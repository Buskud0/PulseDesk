package com.pulsedesk.comment_processor;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CommentProcessorApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CommentProcessorApplication.class, args);
        Application.launch(PulseDeskApplication.class, args);
    }

}
