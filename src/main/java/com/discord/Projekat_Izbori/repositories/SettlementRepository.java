package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.models.Settlement;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Integer> {


    @NotNull
    @Override
    Optional<Settlement> findById(@NotNull Integer integer);


    Optional<Settlement> findBySettlementName(String settlementName);

    Optional<Settlement> findBySettlementNameAndMunicipalityId(String settlementName, Integer municipalityId);
}
