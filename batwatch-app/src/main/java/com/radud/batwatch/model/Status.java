package com.radud.batwatch.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Status {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant setAt;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;
}
