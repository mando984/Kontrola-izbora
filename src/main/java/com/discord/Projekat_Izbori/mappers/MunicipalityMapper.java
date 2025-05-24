package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.Municipality;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityMapper {

    public static Municipality mapFrom(VotingRowDTO dto){

        Municipality municipality = new Municipality();
        municipality.setMunicipalityName(dto.getMunicipalityName());
        municipality.setId(dto.getMunicipalityId());
        return municipality;
    }
}
