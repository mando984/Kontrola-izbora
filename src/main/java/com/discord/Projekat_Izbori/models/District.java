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
@Table(name = "district")
public class District {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String districtName;

    @Column(nullable = false)
    private Integer totalVotersByDistrict;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Municipality> municipalities;


}
