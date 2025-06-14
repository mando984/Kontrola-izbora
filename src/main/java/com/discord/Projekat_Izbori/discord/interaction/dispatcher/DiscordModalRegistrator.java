package com.discord.Projekat_Izbori.discord.interaction.dispatcher;

import com.discord.Projekat_Izbori.discord.interaction.modal.factory.ModalFactory;
import com.discord.Projekat_Izbori.discord.interaction.modal.factory.RegistrationModalFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DiscordModalRegistrator {

    private final Map<String, ModalFactory> registry = new HashMap<>();

    private final RegistrationModalFactory registrationModalFactory;

    public DiscordModalRegistrator(RegistrationModalFactory registrationModalFactory) {
        this.registrationModalFactory = registrationModalFactory;
    }

    @PostConstruct
    public void init(){
        register(registrationModalFactory);
    }

    public void register(ModalFactory modalFactory){
            registry.put(modalFactory.getModalId(), modalFactory);
        }

        public ModalFactory getFactory(String modalId){
            return registry.get(modalId);
        }
}
