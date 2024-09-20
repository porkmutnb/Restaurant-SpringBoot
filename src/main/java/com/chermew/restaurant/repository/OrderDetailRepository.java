package com.chermew.restaurant.repository;

import com.chermew.restaurant.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query(value = "select * from tb_order_detail where order_id = :orderId", nativeQuery = true)
    List<OrderDetail> findByOrderId(@Param("orderId") Integer orderId);
}
