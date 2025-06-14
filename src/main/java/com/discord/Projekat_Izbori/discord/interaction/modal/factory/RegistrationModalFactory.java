package com.discord.Projekat_Izbori.discord.interaction.modal.factory;

import com.discord.Projekat_Izbori.config.DiscordBotConfig;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.springframework.stereotype.Component;

@Component
public class RegistrationModalFactory implements ModalFactory {

    private final DiscordBotConfig discordBotConfig;

    public RegistrationModalFactory(DiscordBotConfig discordBotConfig) {
        this.discordBotConfig = discordBotConfig;
    }

    @Override
    public String getModalId() {
        return discordBotConfig.getRegistrationModalId();
    }

    @Override
    public Modal createModal(GenericInteractionCreateEvent event) {

        TextInput firstName = TextInput.create("FIRST_NAME_ID", "Molimo vas unesite vase ime", TextInputStyle.SHORT)
                .setRequiredRange(1, 15)
                .setPlaceholder("Unesi ime")
                .setRequired(true)
                .build();

        TextInput lastName = TextInput.create("LAST_NAME_ID", "Molimo vas unesite vase prezime", TextInputStyle.SHORT)
                .setRequiredRange(1, 20)
                .setPlaceholder("Unesi prezime")
                .setRequired(true)
                .build();

        TextInput phoneNumber = TextInput.create("PHONE_NUMBER_ID", "Molimo vas unesite vas broj telefona", TextInputStyle.SHORT)
                .setRequiredRange(1, 15)
                .setPlaceholder("Unesi broj telefona")
                .setRequired(true)
                .build();

        TextInput settlementName = TextInput.create("SETTLEMENT_NAME_ID", "Unesite naziv naselja gde cete biti kontrolor", TextInputStyle.SHORT)
                .setRequiredRange(1, 30)
                .setPlaceholder("Unesi naziv naselja")
                .setRequired(true)
                .build();

        TextInput municipalityName = TextInput.create("MUNICIPALITY_NAME_ID", "Unesite naziv opstine gde cete biti kontrolor", TextInputStyle.SHORT)
                .setRequiredRange(1, 30)
                .setPlaceholder("Unesi naziv opstine")
                .setRequired(true)
                .build();


        return Modal.create(discordBotConfig.getRegistrationModalId(), "Unesite vase podatke za registraciju")
                .addActionRow(firstName)
                .addActionRow(lastName)
                .addActionRow(phoneNumber)
                .addActionRow(municipalityName)
                .addActionRow(settlementName)
                .build();



    }
}
