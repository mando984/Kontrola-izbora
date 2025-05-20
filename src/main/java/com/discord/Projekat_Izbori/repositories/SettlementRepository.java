package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    Optional<Settlement> findBySettlementName(String settlementName);
}
