package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.PoliticalParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticalPartyRepository extends JpaRepository<PoliticalParty, Long> {
}
