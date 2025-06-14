package com.discord.Projekat_Izbori.discord.listener;

import com.discord.Projekat_Izbori.config.DiscordBotConfig;
import com.discord.Projekat_Izbori.discord.interaction.dispatcher.DiscordModalRegistrator;
import com.discord.Projekat_Izbori.discord.interaction.dispatcher.ModalInteractionDispatcher;
import com.discord.Projekat_Izbori.discord.interaction.modal.factory.ModalFactory;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class DiscordEventListener extends ListenerAdapter{

    private final DiscordBotConfig discordBotConfig;
    private final DiscordModalRegistrator discordModalRegistrator;
    private final ModalInteractionDispatcher modalInteractionDispatcher;

    public DiscordEventListener(DiscordBotConfig discordBotConfig, DiscordModalRegistrator discordModalRegistrator, ModalInteractionDispatcher modalInteractionDispatcher) {
        this.discordBotConfig = discordBotConfig;
        this.discordModalRegistrator = discordModalRegistrator;
        this.modalInteractionDispatcher = modalInteractionDispatcher;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        String content = event.getMessage().getContentRaw();
        System.out.println("Primljen porka : " + content );
        if (content.equalsIgnoreCase("ping")) {
            event.getChannel().sendMessage("Pong! ✅").queue();
        }
    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        modalInteractionDispatcher.dispatch(event);
    }


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getComponentId().equals(discordBotConfig.getRegistrationButtonId())){
            // Pošalji modal
            ModalFactory factory = discordModalRegistrator.getFactory(discordBotConfig.getRegistrationModalId());

            if(factory != null){
                Modal modal = factory.createModal(event);
                event.replyModal(modal).queue();
            }else {
                event.reply("Greška: Modal za registraciju nije pronađen!").setEphemeral(true).queue();
            }

        }
    }
}
