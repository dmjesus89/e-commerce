package com.dm.payments.repository;

import org.springframework.data.repository.CrudRepository;

import com.dm.payments.model.CreditCard;
import com.dm.payments.model.Payment;

public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {

}
