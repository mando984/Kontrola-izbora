package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.DiscordCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordCategoryRepository extends JpaRepository<DiscordCategory, Integer> {
}
