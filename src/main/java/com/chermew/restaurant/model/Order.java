package com.chermew.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tb_order")
@Data
public class Order {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'Waiting'")
    private String status;
    @Column(name = "guest")
    private Integer guest;
    @Column(name = "serve_table_id")
    private Integer serveTableId;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

}
