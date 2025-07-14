package org.example.finalproject.repositories;

import org.example.finalproject.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
}
