package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.District;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {


    @NotNull
    @Override
    Optional<District> findById(@NotNull Integer integer);

    Optional<District> findByDistrictName(String districtName);
}
