package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.models.PriceTableItem;
import com.enosnery.RestAPI.services.PriceTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PriceTableController {

    @Autowired
    PriceTableService priceTableService;

    @RequestMapping(value = "/prices/list/all", method = RequestMethod.GET, produces = "application/json")
    public String prices(){
        String json = new Gson().toJson(priceTableService.findAllItems());
        return json;
    }

    @PostMapping(value = "/prices/insert")
    public Long insertItem(@RequestBody PriceTableItem item){
        PriceTableItem newItem = new PriceTableItem(item.getRegion(), item.getState(), item.getCity(), item.getDistributor(), item.getInstallationCode(), item.getProduct(), item.getCollectDate(), item.getPurchasePrice(), item.getSalePrice(), item.getMeasurement(), item.getFlag());
        priceTableService.savePriceTableItem(newItem);
        return newItem.getId();
    }

    @PostMapping(value = "/prices/update")
    public String updateItem(@RequestParam Long id){
        PriceTableItem updateItem = priceTableService.findById(id);
        if(updateItem != null) {
            priceTableService.savePriceTableItem(updateItem);
            return updateItem.getId() != null ? "Atualizado!" : "Problemas ao atualizar.";
        }else{
            return "Esse item não existe.";
        }
    }

    @PostMapping(value = "/prices/delete")
    public String deleteItem (@RequestParam Long id){
        priceTableService.deleteItem(id);
        return "Item deletado.";
    }

    @RequestMapping(value = "/prices/average/city", method = RequestMethod.GET, produces = "application/json")
    public Double averageByCity(@RequestParam String city){
        return priceTableService.averageByCity(city);
    }


    @RequestMapping(value = "/prices/list/region", method = RequestMethod.GET, produces = "application/json")
    public String findByRegion(@RequestParam String region){
        return new Gson().toJson(priceTableService.findByRegion(region));
    }

    @RequestMapping(value = "/prices/list/group", method = RequestMethod.GET, produces = "application/json")
    public String groupByDistributor(){
        return new Gson().toJson(priceTableService.groupByDistributor());
    }





}
