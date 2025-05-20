package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRawDTO;
import com.discord.Projekat_Izbori.models.PollingPlace;
import org.springframework.stereotype.Component;

@Component
public class PollingPlaceMapper {

    public static PollingPlace mapFrom(VotingRawDTO dto){

        PollingPlace pollingPlace = new PollingPlace();
        pollingPlace.setPollingPlaceName(dto.getPollingPlaceName());
        pollingPlace.setId(dto.getPollingPlaceId());
        pollingPlace.setStreet(dto.getStreet());
        pollingPlace.setNumberOfVoters(dto.getNumberOfVoters());

        return pollingPlace;
    }
}
