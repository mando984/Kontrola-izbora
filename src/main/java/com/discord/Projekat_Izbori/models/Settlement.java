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
@Table(name = "settlement",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"settlement_name", "municipality_id"})})
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "settlement_name", nullable = false)
    private String settlementName;

    @Column(nullable = false)
    private Integer votersBySettlement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SettlementType settlementType;

    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    @JsonIgnore
    private Municipality municipality;

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL)
    private List<PollingPlace> pollingPlaces;
}
