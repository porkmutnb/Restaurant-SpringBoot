package com.chermew.restaurant.controller;

import com.chermew.restaurant.model.ResponsePayload;
import com.chermew.restaurant.model.auth.AuthenModel;
import com.chermew.restaurant.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponsePayload> login(@RequestBody AuthenModel req) {
        try {
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(authService.login(req));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponsePayload> register(@RequestBody AuthenModel req) {
        try {
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(authService.register(req));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponsePayload> logout(@RequestBody AuthenModel req) {
        try {
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(authService.logout(req));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
