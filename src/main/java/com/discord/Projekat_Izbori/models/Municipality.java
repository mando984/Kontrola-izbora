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
@Table(name = "municipality")
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String municipalityName;

    @Column(nullable = false)
    private Integer totalVotersByMunicipality;

    @Column(nullable = false)
    private Integer rikCode;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    private List<Settlement> settlements;

}
