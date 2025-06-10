package com.discord.Projekat_Izbori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "controller")
public class Controller {

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

    @Column(nullable = false)
    private String discordServerId;

    @Column
    private Enum<DiscordRole> discordRoleEnum;


    @ManyToMany
    @JoinTable(name = "controller_coordinator",
    joinColumns = @JoinColumn(name = "controller_id"),
    inverseJoinColumns = @JoinColumn(name = "coordinator_id"))
    private List<Coordinator> coordinators;

    @ManyToOne
    @JoinColumn(name = "discord_category_id", nullable = false)
    @JsonIgnore
    private DiscordCategory discordCategory;

    @ManyToOne
    @JoinColumn(name = "polling_place_id", nullable = false)
    @JsonIgnore
    private PollingPlace pollingPlace;

    @ManyToOne
    @JoinColumn(name = "settlement_id", nullable = false)
    @JsonIgnore
    private Settlement settlement;
}
