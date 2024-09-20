package com.chermew.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_config")
@Data
public class Config {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value")
    private String value;

}
