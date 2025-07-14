package org.example.finalproject.repositories;

import org.example.finalproject.models.EmployeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeOrderRepository extends CrudRepository<EmployeeOrder, Long>
{
}
