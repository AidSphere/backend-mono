package org.spring.authenticationservice.repository.security.donor;


import org.spring.authenticationservice.model.donor.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor,Long> {

}
