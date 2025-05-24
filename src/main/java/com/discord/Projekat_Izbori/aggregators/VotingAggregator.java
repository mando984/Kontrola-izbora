package com.discord.Projekat_Izbori.aggregators;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VotingAggregator {


    public Map<Integer, Integer> aggregateByDistrictId(List<VotingRowDTO> data) {
        return data.stream()
                .collect(Collectors.groupingBy(VotingRowDTO::getDistrictId,
                        Collectors.summingInt(VotingRowDTO::getNumberOfVoters)));
    }


    public Map<Integer, Integer> aggregateByMunicipalityId(List<VotingRowDTO> data){
        return  data.stream()
                .collect(Collectors.groupingBy(VotingRowDTO::getMunicipalityId,
                        Collectors.summingInt(VotingRowDTO::getNumberOfVoters)));
    }


    public Map<String, Integer> aggregateBySettlementNameAndMunicipalityId(List<VotingRowDTO> data){
        return data.stream()
                .collect(Collectors.groupingBy(dto -> dto.getSettlement() + "_" + dto.getMunicipalityId(),
                        Collectors.summingInt(VotingRowDTO::getNumberOfVoters)));
    }


}