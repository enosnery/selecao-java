package com.enosnery.RestAPI.interfaces;

import com.enosnery.RestAPI.models.PriceTableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceTableItemRepository extends JpaRepository<PriceTableItem, Long> {

    List<PriceTableItem> findByCity(String city);

    List<PriceTableItem> findByRegion(String region);

    @Query(nativeQuery = true, value = "SELECT distributor, COUNT(distributor) as count " +
            "FROM price_table GROUP BY distributor")
    List<PriceTableResultInterface> groupByDistributor();
}

