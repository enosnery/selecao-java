package com.enosnery.RestAPI.services;

import com.enosnery.RestAPI.interfaces.PriceTableItemRepository;
import com.enosnery.RestAPI.interfaces.PriceTableResultInterface;
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

    public PriceTableItem findById(Long id){
        if(priceTableItemRepository.findById(id).isPresent()) {
            return priceTableItemRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public void deleteItem(Long id){
        priceTableItemRepository.deleteById(id);
    }

    public Double averageByCity(String city){
        Double sum = 0d;
        Double average = 0d;
        int amount = 0;
        List<PriceTableItem> list = priceTableItemRepository.findByCity(city);
        for (PriceTableItem pp : list){
            sum = sum + pp.getSalePrice();
            amount++;
        }
            average = (sum/amount);
        return average;
    }

    public List<PriceTableItem> findByRegion(String region){
        return priceTableItemRepository.findByRegion(region);
    }

    public List<PriceTableResultInterface> groupByDistributor(){
        return priceTableItemRepository.groupByDistributor();
    }



}
