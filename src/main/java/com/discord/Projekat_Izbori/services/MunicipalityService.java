package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.aggregators.VotingAggregator;
import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.mappers.MunicipalityMapper;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.repositories.MunicipalityRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;
    private final DistrictService districtService;
    private final VotingAggregator votingAggregator;


    public MunicipalityService(MunicipalityRepository municipalityRepository, MunicipalityMapper municipalityMapper, DistrictService districtService, VotingAggregator votingAggregator) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
        this.districtService = districtService;
        this.votingAggregator = votingAggregator;
    }

    public Map<String, Municipality> extractMunicipality(List<VotingRawDTO> dtos) throws IOException {
        Map<String, Integer> votersByMunicipality = votingAggregator.aggregateByMunicipality(dtos);
        Map<String, Municipality> result = new HashMap<>();

        for (VotingRawDTO dto : dtos){
            String municipalityName = dto.getMunicipalityName();
            if(!result.containsKey(municipalityName)){
                Municipality m = municipalityMapper.mapFrom(dto);
                m.setTotalVotersByMunicipality(votersByMunicipality.get(municipalityName));

                District district = districtService.extractDistrict(dtos);
                m.setDistrict(district);
                result.put(municipalityName, m);
            }
        }
    return result;
    }

    public void saveMunicipality(Municipality municipality){
        municipalityRepository.findByMunicipalityName(municipality.getMunicipalityName())
                .orElseGet(() -> municipalityRepository.save(municipality));
    }

}
