package com.radud.batwatch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Assignment {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant assignedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", insertable = false, updatable = false)
    private AppUser assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", insertable = false, updatable = false)
    private AppUser assignedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;
}
