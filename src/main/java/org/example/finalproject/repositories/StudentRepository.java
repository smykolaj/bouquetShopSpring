package org.example.finalproject.repositories;

import org.example.finalproject.models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long>
{
}
