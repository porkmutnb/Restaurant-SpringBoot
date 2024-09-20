package com.chermew.restaurant.repository;

import com.chermew.restaurant.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {

    @Query(value = "select c.* from tb_config c where c.name = :name order by c.value asc", nativeQuery = true)
    List<Config> findByName(@Param("name") String name);

}
