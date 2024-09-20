package com.chermew.restaurant.repository;

import com.chermew.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select o.* from tb_order o join tb_config c on c.value = o.status where o.serve_table_id = 2 and o.status <> 'Done'", nativeQuery = true)
    List<Order> isServeTableReady(@Param("serveTableId") Integer serveTableId);

}
