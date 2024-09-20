package com.chermew.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tb_order_detail")
@Data
public class OrderDetail {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
}
