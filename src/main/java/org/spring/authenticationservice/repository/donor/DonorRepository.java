package org.spring.authenticationservice.repository.donor;

import org.jetbrains.annotations.NotNull;
import org.spring.authenticationservice.model.donor.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    @NotNull Optional<Donor> findById(@NotNull Long id);
    Optional<Donor> findByEmail(String donorEmail);
}
