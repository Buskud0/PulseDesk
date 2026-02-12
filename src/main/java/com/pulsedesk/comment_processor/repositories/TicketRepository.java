package com.pulsedesk.comment_processor.repositories;

import com.pulsedesk.comment_processor.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}