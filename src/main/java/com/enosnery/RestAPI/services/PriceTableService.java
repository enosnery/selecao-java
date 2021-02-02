package com.enosnery.RestAPI.services;

import com.enosnery.RestAPI.interfaces.*;
import com.enosnery.RestAPI.models.PriceTableItem;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class PriceTableService {

    @Autowired
    PriceTableItemRepository priceTableItemRepository;

    public PriceTableItem updatePriceTableItem(PriceTableItem item){
        PriceTableItem updateItem = priceTableItemRepository.findById(item.getId()).get();
        updateItem.setCity(item.getCity());
        updateItem.setCnpj(item.getCnpj());
        updateItem.setCollectDate(item.getCollectDate());
        updateItem.setDistributor(item.getDistributor());
        updateItem.setFlag(item.getFlag());
        updateItem.setMeasurement(item.getMeasurement());
        updateItem.setProduct(item.getProduct());
        updateItem.setPurchasePrice(item.getPurchasePrice());
        updateItem.setRegion(item.getRegion());
        updateItem.setSalePrice(item.getSalePrice());
        updateItem.setState(item.getState());
        priceTableItemRepository.save(updateItem);
        return updateItem;
    }

    public void saveAll(List<String[]> list) throws ParseException {
        PriceTableItem item;
        for(String[] itemString : list){
            if(itemString.length != 1) {
                item = new PriceTableItem(itemString);
                priceTableItemRepository.save(item);
            }
        }
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
        Double average;
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

    public List<PriceTableDistributorGroup> groupByDistributor(){
        return priceTableItemRepository.groupByDistributor();
    }

    public List<PriceTableDateGroup> groupByDate(){
        return priceTableItemRepository.groupByDate();
    }

    public List<PriceTableAverageCity> groupAveragesByCity(){
        return priceTableItemRepository.groupAverageByCity();
    }

    public List<PriceTableAverageFlag> groupAveragesByFlag(){
        return priceTableItemRepository.groupAverageByFlag();
    }

    public void savePriceTableItem(PriceTableItem newItem) {
        priceTableItemRepository.save(newItem);
    }
}
