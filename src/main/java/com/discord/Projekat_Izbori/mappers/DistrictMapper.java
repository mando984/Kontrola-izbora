package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.District;
import org.springframework.stereotype.Component;

@Component
public class DistrictMapper {

    public static District mapFrom(VotingRowDTO dto){

        District district = new District();
        district.setId(dto.getDistrictId());
        district.setDistrictName(dto.getDistrictName());
        district.setTotalVotersByDistrict(0);

        return district;
    }
}
