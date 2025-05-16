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
@Table(name = "polling_place")
public class PollingPlace {

    @Id
    private Long id;

    @Column(nullable = false)
    private String pollingPlaceName;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer numberOfVoters;

    private Integer paperVoterCount;

    @ManyToOne
    @JoinColumn(name = "settlement_id")
    @JsonIgnore
    private Settlement settlement;

    @OneToMany(mappedBy = "pollingPlace", cascade = CascadeType.ALL)
    private List<HourlyTurnout> hourlyTurnouts;

    @OneToMany(mappedBy = "pollingPlace", cascade = CascadeType.ALL)
    private List<Irregularity> irregularities;

    @OneToOne
    @JoinColumn(name = "final_results_id")
    @JsonIgnore
    private FinalResults finalResults;

}
