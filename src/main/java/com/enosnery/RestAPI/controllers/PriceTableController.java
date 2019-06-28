package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.interfaces.PriceTableAverageCity;
import com.enosnery.RestAPI.interfaces.PriceTableAverageFlag;
import com.enosnery.RestAPI.interfaces.PriceTableDateGroup;
import com.enosnery.RestAPI.interfaces.PriceTableDistributorGroup;
import com.enosnery.RestAPI.models.PriceTableItem;
import com.enosnery.RestAPI.services.PriceTableService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


@Api(value = "Controle de Tabela de Preços", description = "Métodos para inclusão, alteração, remoção e chamadas personalizadas." )
@RestController
public class PriceTableController {

    @Autowired
    PriceTableService priceTableService;

    @ApiOperation(value = "Lista de todos os itens da tabela de preços.", produces = "application/json", httpMethod = "GET")
    @RequestMapping(value = "/prices/list/all", method = RequestMethod.GET, produces = "application/json")
    public String prices(){
        String json = new Gson().toJson(priceTableService.findAllItems());
        return json;
    }

    @ApiOperation(value = "Salvar novo item na tabela de preços.", httpMethod = "POST")
    @PostMapping(value = "/prices/insert")
    public Long insertItem(@RequestBody PriceTableItem item){
        PriceTableItem newItem = new PriceTableItem(item.getRegion(), item.getState(), item.getCity(), item.getDistributor(), item.getInstallationCode(), item.getProduct(), item.getCollectDate(), item.getPurchasePrice(), item.getSalePrice(), item.getMeasurement(), item.getFlag());
        priceTableService.savePriceTableItem(newItem);
        return newItem.getId();
    }

    @ApiOperation(value = "Atualizar item na tabela de preços.", httpMethod = "POST")
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

    @ApiOperation(value = "Remover item da tabela de preços.", httpMethod = "POST")
    @PostMapping(value = "/prices/delete")
    public String deleteItem (@RequestParam Long id){
        priceTableService.deleteItem(id);
        return "Item deletado.";
    }

    @ApiOperation(value = "Média de preço de venda de combustível dado uma cidade específica.", httpMethod = "GET")
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
        HashMap<String, Integer> map = new HashMap<>();
        List<PriceTableDistributorGroup> list = priceTableService.groupByDistributor();
        for(PriceTableDistributorGroup ll : list){
            map.put(ll.getDistributor(), ll.getCount());

        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/prices/list/date", method = RequestMethod.GET, produces = "application/json")
    public String groupByDate(){
        HashMap<String, Integer> map = new HashMap<>();
        List<PriceTableDateGroup> list = priceTableService.groupByDate();
        for(PriceTableDateGroup dd : list){
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String date = format.format(dd.getDate());
            map.put(date, dd.getCount());

        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/prices/list/city", method = RequestMethod.GET, produces = "application/json")
    public List<PriceTableAverageCity> groupAverageByCity(){
        List<PriceTableAverageCity> list = priceTableService.groupAveragesByCity();
        return list;
    }

    @RequestMapping(value = "/prices/list/flag", method = RequestMethod.GET, produces = "application/json")
    public List<PriceTableAverageFlag> groupAverageByFlag(){
        List<PriceTableAverageFlag> list = priceTableService.groupAveragesByFlag();
        return list;
    }

    @PostMapping(value = "/prices/import", consumes = {"multipart/form-data"})
    public String importCSV(@RequestPart("file") MultipartFile file){

        return "CSV importado!";
    }

}
