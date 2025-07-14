package org.example.finalproject.repositories;

import org.example.finalproject.models.Bouquet;
import org.springframework.data.repository.CrudRepository;


public interface BouquetRepository extends CrudRepository<Bouquet, Long>
{
    Bouquet getReferenceById(Long id);



}
