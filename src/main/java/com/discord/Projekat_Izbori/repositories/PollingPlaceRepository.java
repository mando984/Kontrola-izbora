package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.PollingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollingPlaceRepository extends JpaRepository<PollingPlace, Integer> {
    Optional<PollingPlace> findByIdAndSettlementId(Integer id, Integer settlementId);
}
