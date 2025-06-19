package com.discord.Projekat_Izbori.mappers;

import com.discord.Projekat_Izbori.dto.input.VotingRowDTO;
import com.discord.Projekat_Izbori.models.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class DistrictMapperTest {

    private DistrictMapper districtMapper;

    @BeforeEach
    void setUp() {
        districtMapper = new DistrictMapper();
    }

    @Test
    void shouldMapVotingRowDtoToDistrict() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );

        // ACT
        District district = districtMapper.mapFrom(dto);

        // ASSERT
        District expected = new District(4, "zapadnobacki", 0, null,null);
        assertEquals(expected.getDistrictName(), district.getDistrictName());
        assertEquals(expected.getId(), district.getId());
        assertEquals(expected.getTotalVotersByDistrict(), district.getTotalVotersByDistrict());
    }

    @Test
    void shouldHandleNullDistrictIdGracefully() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                null, "zapadnobacki", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );

        // ACT
        District district = districtMapper.mapFrom(dto);

        // ASSERT
        assertNull(dto.getDistrictId());
        assertEquals("zapadnobacki", district.getDistrictName());
        assertNull(district.getId());
    }

    @Test
    void shouldHandleNullDistrictNameGracefully() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, null, 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );

        // ACT
        District district = districtMapper.mapFrom(dto);

        // ASSERT
        assertNull(district.getDistrictName(), "District name should be null when input is null");
        assertEquals(4, district.getId());
    }

    @Test
    void shouldHandleEmptyDistrictName() {
        // ARRANGE
        VotingRowDTO dto = new VotingRowDTO(
                4, "", 1, "Sombor",
                2, "Miletic mz", 800,
                "Lemes","O", 803979, "Oktobarska"
        );

        // ACT
        District district = districtMapper.mapFrom(dto);

        // ASSERT
        assertTrue(StringUtils.isBlank(district.getDistrictName()), "District name should be blank");
        assertEquals(4, district.getId());
    }
}
