package com.dm.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dm.order.model.Order;
import com.dm.order.service.OrderService;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Order> save(@RequestBody  Order order){
        return ResponseEntity.ok(orderService.save(order));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Order>> findAll(){
        return ResponseEntity.ok().body(orderService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
}
