package com.pulsedesk.comment_processor.controllers;

import com.pulsedesk.comment_processor.entities.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketViewController {

    @FXML
    private ListView<String> ticketList;
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, Long> colID;
    @FXML
    private TableColumn<Ticket, String> colPriority;
    @FXML
    private TableColumn<Ticket, String> colCategory;
    @FXML
    private TableColumn<Ticket, String> colSummary;
    @FXML
    private TextArea originalTextField;
    @FXML
    private TextArea commentField;


    private final RestTemplate restTemplate = new RestTemplate();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSummary.setCellValueFactory(new PropertyValueFactory<>("summary"));

        ticketTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillForm(newSelection);
            }
        });
    }

    private void fillForm(Ticket newSelection) {
        originalTextField.setText(newSelection.getOriginalComment().getContent());
    }

    @FXML
    private void handleSend() {
        if(commentField.getText()!="") {
            Map<String, String> requestData = new HashMap<>();
            requestData.put("content", commentField.getText());
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/comments";

            new Thread(() -> {
                try {
                    restTemplate.postForObject(url, requestData, String.class);

                    javafx.application.Platform.runLater(() -> {
                        commentField.clear();
                        handleRefresh();
                    });
                } catch (Exception e) {
                    System.out.println("Klaida siunčiant duomenis: " + e.getMessage());
                }
            }).start();
        }
    }

    @FXML
    public void handleRefresh() {
        try {
            Ticket[] tickets = restTemplate.getForObject("http://localhost:8080/tickets", Ticket[].class);
            if (tickets != null) {
                ticketTable.getItems().setAll(tickets);
            }
        } catch (Exception e) {
            System.out.println("Klaida atnaujinant lentelę: " + e.getMessage());
        }
    }
}