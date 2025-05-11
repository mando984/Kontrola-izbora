package com.discord.Projekat_Izbori.models;

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

    private String municipalityName;

    private Integer totalVoters;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    private List<Settlement> settlements;
}
