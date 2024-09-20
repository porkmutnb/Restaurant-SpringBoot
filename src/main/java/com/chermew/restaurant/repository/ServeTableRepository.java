package com.chermew.restaurant.repository;

import com.chermew.restaurant.model.ServeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ServeTableRepository extends JpaRepository<ServeTable, Integer> {

    @Query(value = "select st.* from tb_serve_table st inner join tb_order o on o.serve_table_id = st.id where st.id = :serveTableId ", nativeQuery = true)
    List<ServeTable> isServeTableUsing(@Param("serveTableId") Integer serveTableId);

}
