package org.example.finalproject.repositories;

import org.example.finalproject.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long>
{
}
