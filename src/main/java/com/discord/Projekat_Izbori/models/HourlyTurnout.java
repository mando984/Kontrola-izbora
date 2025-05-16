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
@Table(name = "hourly_turnout")
public class HourlyTurnout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportHours reportHours;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedTime;

    @Column(nullable = false)
    private Integer turnout;

    @ManyToOne
    @JoinColumn(name = "polling_place_id")
    @JsonIgnore
    private PollingPlace pollingPlace;
}
