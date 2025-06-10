package com.discord.Projekat_Izbori.models;

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
@Table(name = "commission_member")
public class CommissionMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "polling_place_id", nullable = false)
    private PollingPlace pollingPlace;

    @OneToOne
    @JoinColumn(name = "political_party_id")
    private PoliticalParty politicalParty;
}
