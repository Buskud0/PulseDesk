package com.pulsedesk.comment_processor.controllers;

import com.pulsedesk.comment_processor.entities.Comment;
import com.pulsedesk.comment_processor.entities.Ticket;
import com.pulsedesk.comment_processor.repositories.CommentRepository;
import com.pulsedesk.comment_processor.repositories.TicketRepository;
import com.pulsedesk.comment_processor.services.AiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final AiService aiService;

    public CommentController(CommentRepository commentRepository, TicketRepository ticketRepository, AiService aiService) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.aiService = aiService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        Comment savedComment = commentRepository.save(comment);

        try {
            String aiResponse = aiService.analyzeComment(savedComment.getContent());
            System.out.println("DEBUG: AI gautas atsakymas -> " + aiResponse);
            String[] parts = aiResponse.split(";");

            if (parts[0].equals("yes") && parts.length == 4) {
                Ticket ticket = new Ticket();
                ticket.setOriginalComment(savedComment);
                ticket.setTitle("Problema #" + savedComment.getId());
                ticket.setPriority(parts[1]);
                ticket.setCategory(parts[2]);
                ticket.setSummary(parts[3]);

                ticketRepository.save(ticket);
                System.out.println("DEBUG: Ticket sėkmingai sukurtas!");
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Klaida analizuojant -> " + e.getMessage());
        }

        return savedComment;
    }
}