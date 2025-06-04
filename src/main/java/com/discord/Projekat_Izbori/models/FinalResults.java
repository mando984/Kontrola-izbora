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
@Setter
@Getter
@Entity
@Table(name = "final_results")
public class FinalResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String linkToReport;

    @Column(nullable = false)
    private Integer invalidBallots;

    @Column(nullable = false)
    private Integer unusedBallots;

    @OneToOne
    @JoinColumn(name = "polling_place_id", nullable = false, unique = true)
    @JsonIgnore
    private PollingPlace pollingPlace;

    @OneToMany(mappedBy = "finalResults", cascade = CascadeType.ALL)
    private List<PoliticalPartyFinalResults> politicalPartyFinalResultsList;

}
