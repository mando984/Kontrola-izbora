package com.discord.Projekat_Izbori.discord.interaction.modal.handle;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public interface ModalHandler {

    String getModalId();
    void handle(ModalInteractionEvent event);
}
