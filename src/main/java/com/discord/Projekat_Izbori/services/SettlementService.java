package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.SettlementMapper;
import com.discord.Projekat_Izbori.models.Municipality; // Potrebno za povezivanje
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.repositories.SettlementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;

    public SettlementService(SettlementRepository settlementRepository, SettlementMapper settlementMapper) {
        this.settlementRepository = settlementRepository;
        this.settlementMapper = settlementMapper;
    }


    public Settlement findOrCreateSettlement(VotingRowDTO dto, Municipality municipality) {
        Optional<Settlement> existingSettlement =
                settlementRepository.findBySettlementNameAndMunicipalityId(dto.getSettlement(), municipality.getId());

        if (existingSettlement.isPresent()) {
            return existingSettlement.get();
        } else {
            Settlement newSettlement = settlementMapper.mapFrom(dto);
            newSettlement.setMunicipality(municipality);

            return settlementRepository.save(newSettlement);
        }
    }

    public void updateSettlementTotalVoters(Integer settlementId, Integer totalVoters) {
        settlementRepository.findById(settlementId).ifPresent(settlement -> {
            settlement.setVotersBySettlement(totalVoters);
            settlementRepository.save(settlement);
        });
    }

}