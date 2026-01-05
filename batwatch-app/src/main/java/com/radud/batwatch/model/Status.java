package com.radud.batwatch.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant setAt;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser setByUser;
}
