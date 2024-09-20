package com.chermew.restaurant.repository;

import com.chermew.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from tb_user where token = :token", nativeQuery = true)
    List<User> findByToken(@Param("token") String token);

    @Query(value = "select * from tb_user where username = :username and password = :password", nativeQuery = true)
    List<User> login(@Param("username") String username, @Param("password") String password);
}
