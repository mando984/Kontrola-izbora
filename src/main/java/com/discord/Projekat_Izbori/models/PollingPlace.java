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
@Table(name = "polling_place", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "settlement_id"})
})
public class PollingPlace {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String pollingPlaceName;

    @Column(nullable = false)
    private String street;

    @Column(name = "official_registered_voters", nullable = false)
    private Integer officialRegisteredVoters;

    @Column(name = "polling_station_paper_voters")
    private Integer pollingStationPaperVoters;

    @Column(name = "ballot_count")
    private Integer ballotCount;

    @ManyToOne
    @JoinColumn(name = "settlement_id", nullable = false)
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