package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.models.Municipality;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityMapper {

    public static Municipality mapFrom(VotingRawDTO dto){

        Municipality municipality = new Municipality();
        municipality.setMunicipalityName(dto.getMunicipalityName());
        municipality.setRikCode(dto.getMunicipalityId());
        return municipality;
    }
}
