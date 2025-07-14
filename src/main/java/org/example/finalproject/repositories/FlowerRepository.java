package org.example.finalproject.repositories;

import org.example.finalproject.models.Flower;
import org.springframework.data.repository.CrudRepository;

public interface FlowerRepository extends CrudRepository<Flower, Long>
{
}
