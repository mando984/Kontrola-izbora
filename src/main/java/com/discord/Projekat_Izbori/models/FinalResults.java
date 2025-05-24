package com.discord.Projekat_Izbori.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "polling_place_id")
    @JsonIgnore
    private PollingPlace pollingPlace;


    @ManyToOne
    @JoinColumn(name = "final_results_id")
    @JsonIgnore
    private FinalResults finalResults;

}
