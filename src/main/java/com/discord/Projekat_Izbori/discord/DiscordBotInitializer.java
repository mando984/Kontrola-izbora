package com.discord.Projekat_Izbori.discord;

import com.discord.Projekat_Izbori.config.DiscordBotConfig;
import com.discord.Projekat_Izbori.discord.interaction.modal.handle.RegistrationModalHandler;
import com.discord.Projekat_Izbori.discord.listener.DiscordEventListener;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscordBotInitializer {

    private final DiscordBotConfig discordBotConfig;
    private final DiscordEventListener discordEventListener;
    private JDA jdaInstance;

    @Autowired
    public DiscordBotInitializer(DiscordBotConfig discordBotConfig, DiscordEventListener discordEventListener) {
        this.discordBotConfig = discordBotConfig;
        this.discordEventListener = discordEventListener;
    }

    @PostConstruct
    public void startBot() throws InterruptedException {

        JDABuilder builder = JDABuilder.createDefault(discordBotConfig.getToken());
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(discordEventListener);

        jdaInstance = builder.build();
        jdaInstance.awaitReady();
        System.out.println("✅ Discord bot is ready!");
    }

    public JDA getJdaInstance() {
        return jdaInstance;
    }

}
