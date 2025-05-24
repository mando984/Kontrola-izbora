package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.aggregators.VotingAggregator;
import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.MunicipalityMapper;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.models.Municipality;
import com.discord.Projekat_Izbori.repositories.MunicipalityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;


    public MunicipalityService(MunicipalityRepository municipalityRepository, MunicipalityMapper municipalityMapper, DistrictService districtService, VotingAggregator votingAggregator) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
    }



    public Municipality findOrCreateMunicipality(VotingRowDTO dto, District district){
        Optional<Municipality> existingMunicipality = municipalityRepository.findById(dto.getMunicipalityId());

        if(existingMunicipality.isPresent()){

            return existingMunicipality.get();
        }else {

            Municipality newMunicipality = municipalityMapper.mapFrom(dto);
            newMunicipality.setDistrict(district);
            newMunicipality.setTotalVotersByMunicipality(0);
            return municipalityRepository.save(newMunicipality);
        }
    }


    public void updateMunicipalityTotalVoters(Integer municipality_id, Integer totalVoters){
        municipalityRepository.findById(municipality_id).ifPresent(municipality -> {
            municipality.setTotalVotersByMunicipality(totalVoters);
            municipalityRepository.save(municipality);
        });
    }

}
