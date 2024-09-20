package com.chermew.restaurant.service;

import com.chermew.restaurant.model.Config;
import com.chermew.restaurant.model.Order;
import com.chermew.restaurant.model.OrderDetail;
import com.chermew.restaurant.model.ServeTable;
import com.chermew.restaurant.model.order.OrderDetailModel;
import com.chermew.restaurant.model.order.OrderModel;
import com.chermew.restaurant.repository.OrderDetailRepository;
import com.chermew.restaurant.repository.OrderRepository;
import com.chermew.restaurant.repository.ServeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ServeTableRepository serveTableRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UtilService utilService;

    public Order addOrder(OrderModel req) throws Exception {
        ServeTable serveTable = serveTableRepository.findById(req.getServeTableId()).get();
        if(serveTable!=null) {
            if(serveTable.getSeat()<req.getGuest()) {
                throw new ExceptionInInitializerError("serveTableId:"+req.getServeTableId()+" not enough");
            }
            List<Order> orderWithServeTableisReady = orderRepository.isServeTableReady(req.getServeTableId());
            if(orderWithServeTableisReady.size()>0) {
                throw new ExceptionInInitializerError("serveTableId:"+req.getServeTableId()+" have in using");
            }
        }else {
            throw new ExceptionInInitializerError("serveTableId:"+req.getServeTableId()+" not have in system");
        }
        List<Config> configStatusOrder = utilService.getStatusOrderAll();
        configStatusOrder = configStatusOrder.stream().filter(c -> c.getValue().equals("Waiting")).collect(Collectors.toList());
        Date nowdate = new Date();
        Order order = new Order();
        order.setUserId(authService.getUserId());
        order.setGuest(req.getGuest());
        order.setStatus(configStatusOrder.get(0).getValue());
        order.setServeTableId(req.getServeTableId());
        order.setCreateDate(nowdate);
        orderRepository.save(order);
        for (OrderDetailModel reqDetail: req.getMenuList()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setMenuId(reqDetail.getMenuId());
            orderDetail.setQty(reqDetail.getQty());
            orderDetail.setCreateDate(nowdate);
            orderDetailRepository.save(orderDetail);
        }
        return order;
    }

    public Order updateOrder(OrderModel req) {
        List<Config> configStatusOrder = utilService.getStatusOrderAll();
        configStatusOrder = configStatusOrder.stream().filter(c -> c.getValue().equals(req.getStatus())).collect(Collectors.toList());
        if(configStatusOrder.size()==0) {
            throw new ExceptionInInitializerError("status:"+req.getStatus()+" is not format");
        }
        Order order = orderRepository.findById(req.getOrderId()).get();
        order.setStatus(req.getStatus());
        order.setUpdateDate(new Date());
        orderRepository.save(order);
        return order;
    }
}
