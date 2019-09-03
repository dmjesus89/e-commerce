package com.dm.payments.repository;

import org.springframework.data.repository.CrudRepository;

import com.dm.payments.model.Payment;

public interface PaymentRepository  extends CrudRepository<Payment, Integer> {

}
