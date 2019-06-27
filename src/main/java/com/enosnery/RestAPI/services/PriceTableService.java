package com.enosnery.RestAPI.services;

import com.enosnery.RestAPI.interfaces.PriceTableItemRepository;
import com.enosnery.RestAPI.models.PriceTableItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceTableService {

    @Autowired
    PriceTableItemRepository priceTableItemRepository;

    public void savePriceTableItem(PriceTableItem item){
        priceTableItemRepository.save(item);
    }

    public List<PriceTableItem> findAllItems(){
        List<PriceTableItem> items = priceTableItemRepository.findAll();
        return items;
    }



}
