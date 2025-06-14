package com.discord.Projekat_Izbori.discord.interaction.modal.factory;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.modals.Modal;

public interface ModalFactory {
    String getModalId();
    Modal createModal(GenericInteractionCreateEvent event);
}
