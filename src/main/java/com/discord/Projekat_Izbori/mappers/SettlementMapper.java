package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.models.SettlementType;
import org.springframework.stereotype.Component;

@Component
public class SettlementMapper {

    public static Settlement mapFrom(VotingRowDTO dto){

        Settlement settlement = new Settlement();
        settlement.setSettlementName(dto.getSettlement());

        settlement.setVotersBySettlement(0);

        if (dto.getSettlement().toUpperCase().equals(dto.getMunicipalityName().toUpperCase())) {
            settlement.setSettlementType(SettlementType.CITY);
        } else {
            settlement.setSettlementType(SettlementType.VILLAGE);
        }

        return settlement;
    }
}
