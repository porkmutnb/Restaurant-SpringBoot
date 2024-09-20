package com.chermew.restaurant.controller;

import com.chermew.restaurant.model.ServeTable;
import com.chermew.restaurant.model.ResponsePayload;
import com.chermew.restaurant.service.AuthService;
import com.chermew.restaurant.service.ServeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/serveTable")
public class ServeTableController {

    @Autowired
    private ServeTableService serveTableService;

    @Autowired
    private AuthService authService;

    @GetMapping(value = {"/get","/get/{id}"})
    public ResponseEntity<ResponsePayload> getMenu(@RequestHeader(value = "token", required = false) String token, @PathVariable(value = "id", required = false) Integer id)  {
        try {
            authService.tokenIsAvailable(token);
            ResponsePayload res = new ResponsePayload();
            res.setCode(200);
            res.setMessage("Success");
            res.setData(id==null ? serveTableService.findAll() : serveTableService.findById(id));
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.OK);
        } catch (Exception|ExceptionInInitializerError e) {
            ResponsePayload res = new ResponsePayload();
            res.setCode(500);
            res.setMessage(e.getMessage());
            return new ResponseEntity<ResponsePayload>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponsePayload> addMenu(@RequestHeader(value = "token", required = false) String token, @RequestBody ServeTable req) {
        try {
            authService.tokenIsAvailable(token);
            ResponsePayload res = new ResponsePayload();
            req.setId(null);
            res.setData(serveTableService.addMenu(req));
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePayload> updateMenu(@RequestHeader(value = "token", required = false) String token, @PathVariable Integer id, @RequestBody ServeTable req) {
        try {
            authService.tokenIsAvailable(token);
            req.setId(id);
            ResponsePayload res = new ResponsePayload();
            res.setData(serveTableService.updateMenu(req));
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

    @PatchMapping("/edit/{id}")
    public ResponseEntity<ResponsePayload> editMenu(@RequestHeader(value = "token", required = false) String token, @PathVariable Integer id, @RequestBody ServeTable req) {
        try {
            authService.tokenIsAvailable(token);
            req.setId(id);
            ResponsePayload res = new ResponsePayload();
            res.setData(serveTableService.editMenu(req));
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponsePayload> deleteMenu(@RequestHeader(value = "token", required = false) String token, @PathVariable Integer id) {
        try {
            authService.tokenIsAvailable(token);
            ResponsePayload res = new ResponsePayload();
            serveTableService.delete(id);
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
