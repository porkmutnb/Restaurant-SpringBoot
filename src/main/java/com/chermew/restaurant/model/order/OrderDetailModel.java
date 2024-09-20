package com.chermew.restaurant.model.order;

import lombok.Data;

@Data
public class OrderDetailModel {

    private Integer menuId;
    private String menuName;
    private String menuImage;
    private Integer qty;

}
