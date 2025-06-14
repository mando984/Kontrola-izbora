package com.discord.Projekat_Izbori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discord_category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "polling_place_id"}))
public class DiscordCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String categoryId;

    @Column(nullable = false)
    private String categoryName;

    @OneToOne
    @JoinColumn( name = "polling_place_id", nullable = false, unique = true)
    @JsonIgnore
    private PollingPlace pollingPlace;

    @OneToMany(mappedBy = "discordCategory", cascade = CascadeType.ALL)
    private List<DiscordChannel> discordChannels;

    @OneToMany(mappedBy = "discordCategory", cascade = CascadeType.ALL)
    private List<Controller> controllers;

}
