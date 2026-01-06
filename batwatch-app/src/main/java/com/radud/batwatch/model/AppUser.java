package com.radud.batwatch.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdOn DESC")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("assignedAt DESC")
    private List<Assignment> assignedToUser = new ArrayList<>();

    @OneToMany(mappedBy = "assignedByUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("assignedAt DESC")
    private List<Assignment> assignedByUser = new ArrayList<>();

    @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdOn DESC")
    private List<Comment> comments = new ArrayList<>();
}