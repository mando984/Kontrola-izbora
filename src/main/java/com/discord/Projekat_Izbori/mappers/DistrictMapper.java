package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.models.District;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DistrictMapper {

    public static District mapFrom(VotingRawDTO dto){

        District district = new District();
        district.setId(dto.getDistrictId());
        district.setDistrictName(dto.getDistrictName());

        return district;
    }
}
