package com.chermew.restaurant.service;

import com.chermew.restaurant.model.Config;
import com.chermew.restaurant.model.Order;
import com.chermew.restaurant.model.OrderDetail;
import com.chermew.restaurant.model.ServeTable;
import com.chermew.restaurant.model.order.OrderDetailModel;
import com.chermew.restaurant.model.order.OrderModel;
import com.chermew.restaurant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private MenuRepository menuRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UtilService utilService;

    public List<OrderModel> findAll() {
        List<OrderModel> responseList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        for(Order order : orderList) {
            OrderModel orderModel = new OrderModel();
            orderModel.setOrderId(order.getId());
            orderModel.setFullname(order.getFullname());
            orderModel.setStatus(order.getStatus());
            orderModel.setGuest(order.getGuest());
            orderModel.setServeTableId(order.getServeTableId());
            orderModel.setServeTableName(serveTableRepository.findById(order.getServeTableId()).get().getName());
            List<OrderDetailModel> orderDetailModelList = new ArrayList<>();
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
            for(OrderDetail orderDetail : orderDetailList) {
                OrderDetailModel orderDetailModel = new OrderDetailModel();
                orderDetailModel.setQty(orderDetail.getQty());
                orderDetailModel.setMenuId(orderDetail.getMenuId());
                orderDetailModel.setMenuName(menuRepository.findById(orderDetail.getMenuId()).get().getName());
                orderDetailModel.setMenuImage(menuRepository.findById(orderDetail.getMenuId()).get().getImage());
                orderDetailModelList.add(orderDetailModel);
            }
            orderModel.setMenuList(orderDetailModelList);
            responseList.add(orderModel);
        }
        return responseList;
    }

    public OrderModel findById(Integer id) {
        OrderModel response = new OrderModel();
        Order order = orderRepository.findById(id).get();
        response.setOrderId(order.getId());
        response.setFullname(order.getFullname());
        response.setStatus(order.getStatus());
        response.setGuest(order.getGuest());
        response.setServeTableId(order.getServeTableId());
        response.setServeTableName(serveTableRepository.findById(order.getServeTableId()).get().getName());
        List<OrderDetailModel> orderDetailModelList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
        for(OrderDetail orderDetail : orderDetailList) {
            OrderDetailModel orderDetailModel = new OrderDetailModel();
            orderDetailModel.setQty(orderDetail.getQty());
            orderDetailModel.setMenuId(orderDetail.getMenuId());
            orderDetailModel.setMenuName(menuRepository.findById(orderDetail.getMenuId()).get().getName());
            orderDetailModel.setMenuImage(menuRepository.findById(orderDetail.getMenuId()).get().getImage());
            orderDetailModelList.add(orderDetailModel);
        }
        response.setMenuList(orderDetailModelList);
        return response;
    }

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
        order.setFullname(req.getFullname());
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
