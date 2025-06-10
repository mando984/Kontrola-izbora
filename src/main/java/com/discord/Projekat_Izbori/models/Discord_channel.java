package com.discord.Projekat_Izbori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discord_channel",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "discord_category_id"}))
public class Discord_channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false, unique = true)
    private String channelId;

    @Column( nullable = false)
    private String channelName;

    @ManyToOne
    @JoinColumn(name = "discord_category_id", nullable = false)
    @JsonIgnore
    private DiscordCategory discordCategory;

}
