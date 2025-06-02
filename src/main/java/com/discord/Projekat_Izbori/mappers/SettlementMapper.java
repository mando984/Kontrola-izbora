package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.models.SettlementType;
import org.springframework.stereotype.Component;

@Component
public class SettlementMapper {

    public static Settlement mapFrom(VotingRowDTO dto){

        Settlement settlement = new Settlement();
        settlement.setId(dto.getSettlementId());

        settlement.setSettlementName(dto.getSettlementName());
        settlement.setVotersBySettlement(0);
        try {
            if (dto.getSettlementType().equals("G")) {
                settlement.setSettlementType(SettlementType.CITY);
            } else if (dto.getSettlementType().equals("O")) {
                settlement.setSettlementType(SettlementType.VILLAGE);
            }
        }catch (NullPointerException e){
            settlement.setSettlementType(SettlementType.VILLAGE);
        }
        return settlement;
    }
}
