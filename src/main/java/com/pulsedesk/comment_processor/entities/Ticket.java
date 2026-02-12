package com.pulsedesk.comment_processor.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category; // bug / feature / billing / account / other
    private String priority; // low / medium / high

    @Column(columnDefinition = "TEXT")
    private String summary;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment originalComment;
}