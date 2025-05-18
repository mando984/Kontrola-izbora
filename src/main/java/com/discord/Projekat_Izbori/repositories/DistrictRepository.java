package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {

    @Override
    Optional<District> findById(Long aLong);
    Optional<District> findByDistrictName(String districtName);
}
