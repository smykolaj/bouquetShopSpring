package org.example.finalproject.repositories;

import org.example.finalproject.models.FlowerBouquet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlowerBouquetRepository extends CrudRepository<FlowerBouquet, Long>
{
    @Query("SELECT fb FROM FlowerBouquet fb WHERE fb.bouquet.id = :id")
    List<FlowerBouquet> findFlowerBouquetsByBouquetId(@Param("id") Long bouquetId);
}
