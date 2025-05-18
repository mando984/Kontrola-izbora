package com.discord.Projekat_Izbori;

import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.repositories.DistrictRepository;
import com.discord.Projekat_Izbori.repositories.MunicipalityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class DiscordbotApplication {

	public static void main(String[] args) {

		SpringApplication.run(DiscordbotApplication.class, args);
	}

	@Bean
	CommandLineRunner run(DistrictRepository districtRepository, MunicipalityRepository municipalityRepository){
    	return args -> {
			Optional<District> optionalDistrict = districtRepository.findByDistrictName("Zapadnobački okrug");
			if (optionalDistrict.isPresent()) {
				District district = optionalDistrict.get();
				System.out.println(district.getDistrictName());
			} else {
				System.out.println("District nije pronađen.");
			}
			List<Municipality> municipalities = municipalityRepository.findAll().stream()
					.filter(m -> m.getMunicipalityName() != null && !m.getMunicipalityName().isEmpty())
					.collect(Collectors.toList());

			for (Municipality municipality : municipalities){

				System.out.println(municipality.getMunicipalityName());
			}

		};
	}

}
