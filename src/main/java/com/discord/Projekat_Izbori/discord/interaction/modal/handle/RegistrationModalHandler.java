package com.discord.Projekat_Izbori.discord.interaction.modal.handle;

import com.discord.Projekat_Izbori.config.DiscordBotConfig;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RegistrationModalHandler implements ModalHandler {

    private final DiscordBotConfig discordBotConfig;

    public RegistrationModalHandler(DiscordBotConfig discordBotConfig) {
        this.discordBotConfig = discordBotConfig;
    }
    @Override
    public String getModalId() {
        return discordBotConfig.getRegistrationModalId(); // Koristi ID iz konfiguracije
    }

    @Override
    public void handle(@NotNull ModalInteractionEvent event) {
        String firstName = event.getValue("FIRST_NAME_ID").getAsString();
        String lastName = event.getValue("LAST_NAME_ID").getAsString();
        String phoneNumber = event.getValue("PHONE_NUMBER_ID").getAsString();
        String settlement = event.getValue("SETTLEMENT_NAME_ID").getAsString();
        String municipality = event.getValue("MUNICIPALITY_NAME_ID").getAsString();

        System.out.println("📋 Novi unos:");
        System.out.println("Ime: " + firstName);
        System.out.println("Prezime: " + lastName);
        System.out.println("Telefon: " + phoneNumber);
        System.out.println("Naselje: " + settlement);
        System.out.println("Opština: " + municipality);

        // Ovde bi došla logika za upis u bazu, dodavanje uloge, prebacivanje kanala itd.


        event.reply("✅ Hvala na registraciji, " + firstName + "! Administratori su obavešteni o vašoj prijavi.").setEphemeral(true).queue();
    }



}
