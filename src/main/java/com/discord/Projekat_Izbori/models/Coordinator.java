package com.discord.Projekat_Izbori.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coordinator")
public class Coordinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String authorId;

    @Column(nullable = false, unique = true)
    private String discordUsername;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "discord_id", nullable = false)
    private District district;

    @ManyToMany(mappedBy = "coordinators")
    private List<Controller> controllers;




}
