package com.chermew.restaurant.controller;

import com.chermew.restaurant.model.ResponsePayload;
import com.chermew.restaurant.service.AuthService;
import com.chermew.restaurant.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/util")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilController {

    @Autowired
    private UtilService utilService;

    @Autowired
    private AuthService authService;

    @GetMapping("/findByConfig/{configName}")
    public ResponseEntity<ResponsePayload> findByConfig(@RequestHeader(value = "token", required = false) String token, @PathVariable("configName") String configName) {
        try {
            if(!configName.equalsIgnoreCase("Gender")) {
                authService.tokenIsAvailable(token);
            }
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(utilService.findByConfig(configName));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
