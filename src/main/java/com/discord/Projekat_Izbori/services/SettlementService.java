package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.aggregators.VotingAggregator;
import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.mappers.SettlementMapper;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.models.Settlement;
import com.discord.Projekat_Izbori.models.SettlementType;
import com.discord.Projekat_Izbori.repositories.MunicipalityRepository;
import com.discord.Projekat_Izbori.repositories.SettlementRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;
    private final MunicipalityService municipalityService;
    private final VotingAggregator votingAggregator;
    private final MunicipalityRepository municipalityRepository;


    public SettlementService(SettlementRepository settlementRepository, SettlementMapper settlementMapper, MunicipalityService municipalityService, VotingAggregator votingAggregator, MunicipalityRepository municipalityRepository) {
        this.settlementRepository = settlementRepository;
        this.settlementMapper = settlementMapper;
        this.municipalityService = municipalityService;
        this.votingAggregator = votingAggregator;
        this.municipalityRepository = municipalityRepository;
    }

    public Map<String, Settlement> extractSettlement(List<VotingRawDTO> dtos) throws IOException {
        Map<String, Integer> votersBySettlement = votingAggregator.aggregateBySettlement(dtos);
        Map<String, Settlement> result = new HashMap<>();

        for (VotingRawDTO dto : dtos){
            String settlementName = dto.getSettlement();

            if(!result.containsKey(settlementName)){
                Settlement s = settlementMapper.mapFrom(dto);
                s.setVotersBySettlement(votersBySettlement.get(settlementName));
                if(settlementName.toUpperCase().equals(dto.getMunicipalityName().toUpperCase())){
                    s.setSettlementType(SettlementType.CITY);
                }else {
                    s.setSettlementType(SettlementType.VILLAGE);
                }
            s.setMunicipality(municipalityService.extractMunicipality(dtos).get(dto.getMunicipalityName()));
            result.put(settlementName, s);
            }
        }
        return result;
    }

    public void saveSettlement(Settlement settlement){
        Municipality municipality = settlement.getMunicipality();

        Optional<Municipality> existing = municipalityRepository.findByMunicipalityName(municipality.getMunicipalityName());
        if(existing.isPresent()){
            settlement.setMunicipality(existing.get());
        }else {
            municipalityRepository.save(municipality);
        }

        Optional<Settlement> existingSettlement =
                settlementRepository.findBySettlementName(settlement.getSettlementName());

        if (existingSettlement.isEmpty()) {
            settlementRepository.save(settlement);
        }
    }

}
