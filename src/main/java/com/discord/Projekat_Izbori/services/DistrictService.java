package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.mappers.DistrictMapper;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    public DistrictService(DistrictRepository districtRepository, DistrictMapper districtMapper) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
    }

    public District findOrCreateDistrict(VotingRowDTO dto) {
        Optional<District> existingDistrict = districtRepository.findById(dto.getDistrictId());

        if (existingDistrict.isPresent()) {
            return existingDistrict.get();
        } else {
            District newDistrict = districtMapper.mapFrom(dto);

            return districtRepository.save(newDistrict);
        }
    }

    public void updateDistrictTotalVoters(Integer districtId, Integer totalVoters) {
        districtRepository.findById(districtId).ifPresent(district -> {
            district.setTotalVotersByDistrict(totalVoters);
            districtRepository.save(district);
        });
    }

}