package com.discord.Projekat_Izbori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "political_party_final_results",
        uniqueConstraints = @UniqueConstraint(columnNames = {"political_party_id", "final_results_id"}))
public class PoliticalPartyFinalResults {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer numberOfVotes;

    @ManyToOne
    @JoinColumn(name = "political_party_id", nullable = false)
    @JsonIgnore
    private PoliticalParty politicalParty;

    @ManyToOne
    @JoinColumn(name = "final_results_id", nullable = false)
    @JsonIgnore
    private FinalResults finalResults;

}
