package com.discord.Projekat_Izbori.services;

import com.discord.Projekat_Izbori.aggregators.VotingAggregator;
import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.mappers.DistrictMapper;
import com.discord.Projekat_Izbori.models.District;
import com.discord.Projekat_Izbori.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final VotingAggregator votingAggregator;

    public DistrictService(DistrictRepository districtRepository, DistrictMapper districtMapper, VotingAggregator votingAggregator) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
        this.votingAggregator = votingAggregator;
    }

    public District extractDistrict(List<VotingRawDTO> dtos) throws IOException {

        VotingRawDTO firstRow = dtos.get(0);
        District district = districtMapper.mapFrom(firstRow);
        district.setTotalVotersByDistrict(votingAggregator.aggregateByDistrict(dtos).get(firstRow.getDistrictName()));
        return district;
    }

    public void saveDistrict(District d){
        districtRepository.findByDistrictName(d.getDistrictName())
                .orElseGet(() -> districtRepository.save(d));

    }

}
