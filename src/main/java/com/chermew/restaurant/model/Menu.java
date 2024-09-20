package com.chermew.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tb_menu")
@Data
public class Menu {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

}
