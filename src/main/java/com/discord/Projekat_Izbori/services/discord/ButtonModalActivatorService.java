package com.discord.Projekat_Izbori.services.discord;

import com.discord.Projekat_Izbori.config.DiscordBotConfig;
import com.discord.Projekat_Izbori.discord.DiscordBotInitializer;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.stereotype.Component;

@Component
public class ButtonModalActivatorService {

    private final DiscordBotInitializer discordBotInitializer;
    private final DiscordBotConfig discordBotConfig;

    public ButtonModalActivatorService(DiscordBotInitializer discordBotInitializer, DiscordBotConfig discordBotConfig) {
        this.discordBotInitializer = discordBotInitializer;
        this.discordBotConfig = discordBotConfig;
    }

    @PostConstruct
    public void buttunSetup(){

        JDA jda = discordBotInitializer.getJdaInstance();
        TextChannel regChannel = jda.getTextChannelById(discordBotConfig.getRegistrationChannelId());

        if (regChannel == null) {
            System.err.println("❗ Greška: Kanal nije pronađen.");
            return;
        }

        Button registerButton = Button.primary(discordBotConfig.getRegistrationButtonId(), "📋 Registruj se");
        regChannel.sendMessage("👋 Dobrodošao! Klikni na dugme ispod da se registruješ:")
                .addActionRow(registerButton)
                .queue((Message msg) -> {
                    // Opciono: obriši sve prethodne poruke iz bota u kanalu i ostavi samo ovu
                    // ili pinuj ovu poruku
                    regChannel.getIterableHistory().takeAsync(50).thenAccept(messages -> {
                        messages.stream()
                                .filter(m -> m.getAuthor().isBot() && !m.getId().equals(msg.getId()))
                                .forEach(m -> m.delete().queue());
                    });
                    // ➕ Pinuj poruku
                    msg.pin().queue();
                });
    }
}
