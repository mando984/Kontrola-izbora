package com.discord.Projekat_Izbori.discord.interaction.modal.handle;

import com.discord.Projekat_Izbori.config.DiscordBotConfig;
import com.discord.Projekat_Izbori.dto.input.RegistrationRequestDto;
import com.discord.Projekat_Izbori.services.discord.RegistrationService;
import jakarta.validation.ConstraintViolationException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class RegistrationModalHandler implements ModalHandler {

    private final DiscordBotConfig discordBotConfig;
    private final RegistrationService registrationService;

    public RegistrationModalHandler(DiscordBotConfig discordBotConfig, RegistrationService registrationService) {
        this.discordBotConfig = discordBotConfig;
        this.registrationService = registrationService;
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
        String settlementName = event.getValue("SETTLEMENT_NAME_ID").getAsString();
        String municipalityName = event.getValue("MUNICIPALITY_NAME_ID").getAsString();

        System.out.println("📋 Novi unos:");
        System.out.println("Ime: " + firstName);
        System.out.println("Prezime: " + lastName);
        System.out.println("Telefon: " + phoneNumber);
        System.out.println("Naselje: " + settlementName);
        System.out.println("Opština: " + municipalityName);

        // Ovde bi došla logika za upis u bazu, dodavanje uloge, prebacivanje kanala itd.

        // Dohvati Discord ID korisnika koji je poslao modal
        String discordUserId = event.getUser().getId();
        String discordUsername = event.getUser().getName();


        // 2. Kreiranje DTO objekta
        RegistrationRequestDto requestDto = new RegistrationRequestDto();
        requestDto.setFirstName(firstName);
        requestDto.setLastName(lastName);
        requestDto.setPhoneNumber(phoneNumber);
        requestDto.setMunicipalityName(municipalityName);
        requestDto.setSettlementName(settlementName);
        requestDto.setDiscordUserId(discordUserId);
        requestDto.setDiscordUsername(discordUsername);



        try {
            boolean success = registrationService.processRegistration(requestDto);

            if (success) {
                event.reply("✅ Hvala na registraciji, " + requestDto.getFirstName() + "! Vaši podaci su uspešno primljeni i prosleđeni na obradu.").setEphemeral(true).queue();
            } else {
                event.reply("❌ Došlo je do greške prilikom čuvanja vaše registracije. Molimo pokušajte ponovo.").setEphemeral(true).queue();
            }
        } catch (ConstraintViolationException e) {
            // Validacija je pala! Izvadi poruke o greškama i pošalji ih korisniku.
            StringBuilder errorMessage = new StringBuilder("❌ Greška u validaciji:\n");
            e.getConstraintViolations().forEach(violation ->
                    errorMessage.append("- ").append(violation.getMessage()).append("\n")
            );
            event.reply(errorMessage.toString()).setEphemeral(true).queue();
        } catch (Exception e) {
            // Neka druga neočekivana greška
            System.err.println("Neočekivana greška u handle metodi: " + e.getMessage());
            e.printStackTrace();
            event.reply("❌ Došlo je do neočekivane greške. Molimo kontaktirajte podršku.").setEphemeral(true).queue();
        }
    }



}
