package org.example.finalproject.repositories;

import org.example.finalproject.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    Optional<Customer> findFirstByOrderByIdAsc();

}
