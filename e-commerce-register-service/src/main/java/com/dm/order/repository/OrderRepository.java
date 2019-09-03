package com.dm.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.dm.order.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
