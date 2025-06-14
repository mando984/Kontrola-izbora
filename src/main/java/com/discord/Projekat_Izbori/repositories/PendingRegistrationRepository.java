package com.discord.Projekat_Izbori.repositories;

import com.discord.Projekat_Izbori.models.PendingRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingRegistrationRepository extends JpaRepository<PendingRegistration, Integer> {

}
