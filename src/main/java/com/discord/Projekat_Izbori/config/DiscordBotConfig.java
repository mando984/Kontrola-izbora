package com.discord.Projekat_Izbori.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class DiscordBotConfig {

    @Value("${discord.token}")
    private String token;

    @Value("${discord.channel.registration.id}")
    private long registrationChannelId;

    @Value("${discord.button.registration.id}")
    private String registrationButtonId;

    @Value("${discord.modal.registration.id}")
    private String registrationModalId;


}
