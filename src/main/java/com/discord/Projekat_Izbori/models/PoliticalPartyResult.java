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
@Table(name = "political_party_score")
public class PoliticalPartyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer numberOfVotes;

    @OneToOne
    @JoinColumn(name = "political_party_id")
    @JsonIgnore
    private PoliticalParty politicalParty;

    @ManyToOne
    @JoinColumn(name = "final_results_id")
    @JsonIgnore
    private FinalResults finalResults;

}
