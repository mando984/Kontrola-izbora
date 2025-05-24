package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.Irregularity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrregularityRepositiry extends JpaRepository<Irregularity, Integer> {
}
