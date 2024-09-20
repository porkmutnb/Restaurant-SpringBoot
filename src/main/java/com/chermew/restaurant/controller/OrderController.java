package com.chermew.restaurant.controller;

import com.chermew.restaurant.model.Menu;
import com.chermew.restaurant.model.ResponsePayload;
import com.chermew.restaurant.model.order.OrderModel;
import com.chermew.restaurant.service.AuthService;
import com.chermew.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthService authService;

    @GetMapping(value = {"/get","/get/{id}"})
    public ResponseEntity<ResponsePayload> getOrder(@RequestHeader(value = "token", required = false) String token, @PathVariable(value = "id", required = false) Integer id)  {
        try {
            authService.tokenIsAvailable(token);
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(id==null ? orderService.findAll() : orderService.findById(id));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponsePayload> addOrder(@RequestHeader(value = "token", required = false) String token, @RequestBody OrderModel req) {
        try {
            authService.tokenIsAvailable(token);
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(orderService.addOrder(req));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePayload> updateOrder(@RequestHeader(value = "token", required = false) String token, @PathVariable Integer id, @RequestBody OrderModel req) {
        try {
            authService.tokenIsAvailable(token);
            req.setOrderId(id);
            ResponsePayload res = new ResponsePayload();
            res.setData(orderService.updateOrder(req));
            res.setCode(200);
            res.setMessage("Success");
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
