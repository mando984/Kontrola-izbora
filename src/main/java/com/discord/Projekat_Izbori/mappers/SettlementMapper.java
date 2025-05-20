package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.models.Settlement;
import org.springframework.stereotype.Component;

@Component
public class SettlementMapper {

    public static Settlement mapFrom(VotingRawDTO dto){

        Settlement settlement = new Settlement();
        settlement.setSettlementName(dto.getSettlement());
        return settlement;

    }
}
