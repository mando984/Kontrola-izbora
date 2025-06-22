package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {

    @NotNull
    @Override
    Optional<Municipality> findById(@NotNull Integer integer);

    Optional<Municipality> findByMunicipalityName(String municipalityName);
}
