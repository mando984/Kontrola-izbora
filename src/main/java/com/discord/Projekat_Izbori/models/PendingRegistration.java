package com.discord.Projekat_Izbori.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "pending_registrations")
@Data
public class PendingRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    private String municipalityName;

    @Column(nullable = false, length = 50)
    private String settlementName;

    // Discord specifični podaci

    @Column(nullable = false)
    private String discordUserId;

    @Column(nullable = false)
    private String discordUsername;

    // Status registracije

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status;

    // Vremenski pečat kada je kreirano
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.status = RegistrationStatus.PENDING;
    }
}
