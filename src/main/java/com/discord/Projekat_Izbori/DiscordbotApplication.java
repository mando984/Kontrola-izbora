package com.discord.Projekat_Izbori;

import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.repositories.DistrictRepository;
import com.discord.Projekat_Izbori.repositories.MunicipalityRepository;
import com.discord.Projekat_Izbori.services.RikProcessingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class DiscordbotApplication {

	public final RikProcessingService rikProcessingService;

    public DiscordbotApplication(RikProcessingService rikProcessingService) {
        this.rikProcessingService = rikProcessingService;
    }

    public static void main(String[] args) {

		SpringApplication.run(DiscordbotApplication.class, args);
	}

	@Bean
	CommandLineRunner run(){
    	return args -> {
        	rikProcessingService.processAll();

		};

	}

}
