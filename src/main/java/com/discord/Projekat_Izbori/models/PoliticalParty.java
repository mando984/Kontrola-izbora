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
@Table(name = "political_party")
public class PoliticalParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String politicalPartyName;

    @OneToMany(mappedBy = "politicalParty")
    private List<PoliticalPartyFinalResults> politicalPartyFinalResultsList;

    @OneToOne(mappedBy = "politicalParty")
    private CommissionMember commissionMember;
}
