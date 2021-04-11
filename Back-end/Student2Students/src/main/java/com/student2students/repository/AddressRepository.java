package com.student2students.repository;

import com.student2students.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCity(String city);
}
