package com.chermew.restaurant.repository;

import com.chermew.restaurant.model.Menu;
import com.chermew.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query(value = "select m.* from tb_menu m inner join tb_order_detail od on od.menu_id = m.id where m.id = :menuId ", nativeQuery = true)
    List<User> isMenuUsing(@Param("menuId") Integer menuId);

}
