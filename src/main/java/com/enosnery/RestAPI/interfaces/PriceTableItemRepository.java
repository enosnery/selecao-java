package com.enosnery.RestAPI.interfaces;

import com.enosnery.RestAPI.models.PriceTableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTableItemRepository extends JpaRepository<PriceTableItem, Long> {
}
