package com.chermew.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "fullname", nullable = false)
    private String fullname;
    @Column(name = "gender_id")
    private Integer genderId;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "token")
    private String token;

}
