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
    List<PriceTableDistributorGroup> groupByDistributor();

    @Query(nativeQuery = true, value = "SELECT collect_date AS date, COUNT(collect_date) as count " +
            "FROM price_table GROUP BY collect_date")
    List<PriceTableDateGroup> groupByDate();

    @Query(nativeQuery = true, value = "SELECT city, AVG(sale_price) as saleAverage, AVG(purchase_price) as purchaseAverage " +
            "FROM price_table GROUP BY city")
    List<PriceTableAverageCity> groupAverageByCity();

    @Query(nativeQuery = true, value = "SELECT flag, AVG(sale_price) as saleAverage, AVG(purchase_price) as purchaseAverage " +
            "FROM price_table GROUP BY flag")
    List<PriceTableAverageFlag> groupAverageByFlag();
}

