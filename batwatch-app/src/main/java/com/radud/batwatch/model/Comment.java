package com.radud.batwatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @CreationTimestamp
    private Instant createdOn;
}
