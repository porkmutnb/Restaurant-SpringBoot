package com.chermew.restaurant.service;

import com.chermew.restaurant.model.ServeTable;
import com.chermew.restaurant.repository.ServeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServeTableService {

    @Autowired
    private ServeTableRepository serveTableRepository;

    public List<ServeTable> findAll() {
        return serveTableRepository.findAll();
    }

    public ServeTable findById(Integer id) {
        return serveTableRepository.findById(id).get();
    }

    public ServeTable addMenu(ServeTable req) {
        req.setCreateDate(new Date());
        return serveTableRepository.save(req);
    }

    public ServeTable updateMenu(ServeTable req) {
        ServeTable item = serveTableRepository.findById(req.getId()).get();
        item.setName(req.getName());
        item.setSeat(req.getSeat());
        item.setUpdateDate(new Date());
        return serveTableRepository.save(item);
    }

    public ServeTable editMenu(ServeTable req) {
        ServeTable item = serveTableRepository.findById(req.getId()).get();
        if(req.getName()!=null && !req.getName().isEmpty()) {
            item.setName(req.getName());
        }
        if(req.getSeat()!=null) {
            item.setSeat(req.getSeat());
        }
        item.setUpdateDate(new Date());
        return serveTableRepository.save(item);
    }

    public void delete(Integer id) {
        if(serveTableRepository.isServeTableUsing(id).size()==0) {
            serveTableRepository.deleteById(id);
        }else {
            throw new ExceptionInInitializerError("This Menu:"+id+" is used, can not deleted");
        }
    }
}
