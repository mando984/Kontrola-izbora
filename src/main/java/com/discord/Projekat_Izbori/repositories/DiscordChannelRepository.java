package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.DiscordChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordChannelRepository extends JpaRepository<DiscordChannel, Integer> {
}
