package com.discord.Projekat_Izbori.discord.interaction.dispatcher;

import com.discord.Projekat_Izbori.discord.interaction.modal.handle.ModalHandler;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ModalInteractionDispatcher {

    private final Map<String, ModalHandler> handlers = new HashMap<>();

    // Spring će automatski pronaći i injektovati SVE implementacije ModalHandler interfejsa
    public ModalInteractionDispatcher(List<ModalHandler> modalHandlers) {
        modalHandlers.forEach(handler -> handlers.put(handler.getModalId(), handler));
        System.out.println("✅ Registrovani modal handler-i: " + handlers.keySet());
    }

    public void dispatch(ModalInteractionEvent event) {
        ModalHandler handler = handlers.get(event.getModalId());
        if (handler != null) {
            handler.handle(event);
        } else {
            event.reply("Nepoznata modal interakcija.").setEphemeral(true).queue();
        }
    }


}
