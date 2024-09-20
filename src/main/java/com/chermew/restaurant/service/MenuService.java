package com.chermew.restaurant.service;

import com.chermew.restaurant.model.Menu;
import com.chermew.restaurant.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Integer id) {
        return menuRepository.findById(id).get();
    }

    public Menu addMenu(Menu req) {
        req.setCreateDate(new Date());
        return menuRepository.save(req);
    }

    public Menu updateMenu(Menu req) {
        Menu item = menuRepository.findById(req.getId()).get();
        item.setName(req.getName());
        item.setImage(req.getImage());
        item.setUpdateDate(new Date());
        return menuRepository.save(item);
    }

    public Menu editMenu(Menu req) {
        Menu item = menuRepository.findById(req.getId()).get();
        if(req.getName()!=null && !req.getName().isEmpty()) {
            item.setName(req.getName());
        }
        if(req.getImage()!=null && !req.getImage().isEmpty()) {
            item.setImage(req.getImage());
        }
        item.setUpdateDate(new Date());
        return menuRepository.save(item);
    }

    public void delete(Integer id) {
        if(menuRepository.isMenuUsing(id).size()==0) {
            menuRepository.deleteById(id);
        }else {
            throw new ExceptionInInitializerError("This Menu:"+id+" is used, can not deleted");
        }
    }
}
