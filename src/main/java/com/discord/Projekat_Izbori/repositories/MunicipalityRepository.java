package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    @Override
    List<Municipality> findAll();
}
