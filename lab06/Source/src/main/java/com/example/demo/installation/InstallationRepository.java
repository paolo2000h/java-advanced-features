package com.example.demo.installation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
    @Query("SELECT c.installations FROM Client c WHERE c.id = :clientId")
    List<Installation> findInstallationsByClientId(@Param("clientId") Long clientId);
}
