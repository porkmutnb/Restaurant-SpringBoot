package com.chermew.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tb_serve_table")
@Data
public class ServeTable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "seat", nullable = false)
    private Integer seat;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

}
