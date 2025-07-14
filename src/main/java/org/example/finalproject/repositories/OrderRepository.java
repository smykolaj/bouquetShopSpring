package org.example.finalproject.repositories;

import org.example.finalproject.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{
}
