package com.discord.Projekat_Izbori.aggregators;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VotingAggregator {

    public Map<String, Integer> aggregateByDistrict(List<VotingRawDTO> data) {
        return data.stream()
                .collect(Collectors
                        .groupingBy(VotingRawDTO::getDistrictName,
                                Collectors.summingInt(VotingRawDTO::getNumberOfVoters)));

    }

    public Map<String, Integer> aggregateByMunicipality(List<VotingRawDTO> data){
        return  data.stream()
                .collect(Collectors
                        .groupingBy(VotingRawDTO::getMunicipalityName,
                                Collectors.summingInt(VotingRawDTO::getNumberOfVoters)));
    }


    public Map<String, Integer> aggregateBySettlement(List<VotingRawDTO> data){
        return data.stream()
                .collect(Collectors
                        .groupingBy(VotingRawDTO::getSettlement,
                                Collectors.summingInt(VotingRawDTO::getNumberOfVoters)));
    }
}
