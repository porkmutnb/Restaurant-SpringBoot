package com.chermew.restaurant.model.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderModel {
    private Integer orderId;
    private String fullname;
    private String status;
    private Integer guest;
    private Integer serveTableId;
    private String serveTableName;
    private List<OrderDetailModel> menuList;

}
