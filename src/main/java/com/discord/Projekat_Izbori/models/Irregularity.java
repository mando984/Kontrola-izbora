package com.discord.Projekat_Izbori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "irregularity")
public class Irregularity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime incidentTime;

    @Lob
    @Column(nullable = false)
    private String incidentDescription;

    private String linkToImage;

    @ManyToOne
    @JoinColumn(name = "polling_place_id")
    @JsonIgnore
    private PollingPlace pollingPlace;
}
