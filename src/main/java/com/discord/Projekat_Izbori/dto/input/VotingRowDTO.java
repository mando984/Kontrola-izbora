package com.discord.Projekat_Izbori.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotingRowDTO {

    private Integer districtId;

    private String districtName;

    private Integer municipalityId;

    private String municipalityName;

    private Integer pollingPlaceId;

    private String pollingPlaceName;

    private Integer numberOfVoters;

    private String settlement;

    private String street;
}

