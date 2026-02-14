package com.pulsedesk.comment_processor.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

public class TicketViewController {

    @FXML
    private ListView<String> ticketList;

    private final RestTemplate restTemplate = new RestTemplate();

    @FXML
    public void handleRefresh() {
        try {
            // Kreipiamės į tavo paties sukurtą API!
            List<Map<String, Object>> tickets = restTemplate.getForObject("http://localhost:8080/tickets", List.class);

            ticketList.getItems().clear();
            if (tickets != null) {
                for (Map<String, Object> t : tickets) {
                    ticketList.getItems().add(t.get("category") + " | " + t.get("summary"));
                }
            }
        } catch (Exception e) {
            ticketList.getItems().add("Klaida: Nepavyko pasiekti API. Įsitikink, kad Spring veikia.");
        }
    }
}