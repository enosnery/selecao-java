package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.interfaces.PriceTableAverageCity;
import com.enosnery.RestAPI.interfaces.PriceTableAverageFlag;
import com.enosnery.RestAPI.interfaces.PriceTableDateGroup;
import com.enosnery.RestAPI.interfaces.PriceTableDistributorGroup;
import com.enosnery.RestAPI.models.AverageResponseItem;
import com.enosnery.RestAPI.models.GroupResponseItem;
import com.enosnery.RestAPI.models.PriceTableItem;
import com.enosnery.RestAPI.models.User;
import com.enosnery.RestAPI.services.PriceTableService;
import com.enosnery.RestAPI.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Response;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Api(value = "Controle de Tabela de Preços", description = "Métodos para inclusão, alteração, remoção e chamadas personalizadas." )
@RestController
@CrossOrigin
public class PriceTableController {

    @Autowired
    PriceTableService priceTableService;

    @ApiOperation(value = "Lista de todos os itens da tabela de preços.", produces = "application/json", httpMethod = "GET")
    @GetMapping(value = "/prices", produces = "application/json")
    public HashMap<String, Object> getAllItems(){
        HashMap<String, Object> response = new HashMap<>();
        List<PriceTableItem> list = priceTableService.findAllItems();
        if(list.size() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_DATA);
        }else{
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, list);
        }
        return response;
    }

    @GetMapping(value = "/prices/{itemId}")
    public HashMap<String, Object> getUser(@PathVariable("itemId") Long itemId){
        HashMap<String, Object> response = new HashMap<>();
        PriceTableItem item = priceTableService.findById(itemId);
        if(item == null || item.getId() == null || item.getId() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.ERROR_NO_USER);
        }else{
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, item);
        }
        return response;
    }

    @ApiOperation(value = "Salvar novo item na tabela de preços.", httpMethod = "POST")
    @PostMapping(value = "/prices")
    public HashMap<String, Object> insertItem(@RequestBody PriceTableItem item){
        HashMap<String, Object> response = new HashMap<>();
        PriceTableItem newItem = new PriceTableItem(item.getRegion(), item.getState(), item.getCity(), item.getDistributor(), item.getCnpj(), item.getProduct(), item.getCollectDate(), item.getPurchasePrice(), item.getSalePrice(), item.getMeasurement(), item.getFlag());
        priceTableService.savePriceTableItem(newItem);
        if(newItem.getId() == null || newItem.getId() == 0){
            response.put(Constants.CODE, Response.SC_NOT_ACCEPTABLE);
            response.put(Constants.MESSAGE, Constants.ERROR_SAVE);
        }else{
            HashMap<String, Long> map = new HashMap<>();
            String sb = "itemId";
            map.put(sb, newItem.getId());
            response.put(Constants.CODE, Response.SC_CREATED);
            response.put(Constants.RESPONSE, map);
        }
        return response;

    }

    @PutMapping(value = "/prices")
    public HashMap<String, Object> updateItem(@RequestBody PriceTableItem item){
        HashMap<String, Object> response = new HashMap<>();

        if(item != null) {
            PriceTableItem updateItem = priceTableService.updatePriceTableItem(item);
            if (updateItem.getId() != null){
                response.put(Constants.CODE, Response.SC_OK);
                response.put(Constants.RESPONSE, Constants.SUCCESS_UPDATED_ITEM);
            }else{
                response.put(Constants.CODE, Response.SC_NOT_ACCEPTABLE);
                response.put(Constants.MESSAGE, Constants.ERROR_UPDATE);
            }
        }else{
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_ITEM);
        }

        return response;
    }

    @DeleteMapping(value = "/prices/{id}")
    public  HashMap<String, Object> deleteItem (@PathVariable("id") Long id){
        HashMap<String, Object> response = new HashMap<>();
        if(id == null || id == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_ID);
        }else {
            priceTableService.deleteItem(id);
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, Constants.SUCCESS_DELETED);
        }
        return response;
    }

    @ApiOperation(value = "Média de preço de venda de combustível dado uma cidade específica.", httpMethod = "GET")
    @GetMapping(value = "/prices/average/city", produces = "application/json")
    public HashMap<String, Object> averageByCity(@RequestParam String city){
        HashMap<String, Object> response = new HashMap<>();
        if(city == null || city.equals("") || city.isEmpty()){
            response.put(Constants.CODE, Response.SC_NOT_ACCEPTABLE);
            response.put(Constants.MESSAGE, Constants.ERROR_WRONG_DATA);
        }else {
            Double result = priceTableService.averageByCity(city);
            System.out.println(result);
            if (result == null || result == 0d || result.isNaN()) {
                response.put(Constants.CODE, Response.SC_NO_CONTENT);
                response.put(Constants.MESSAGE, Constants.REQUEST_NO_CITY);
            }else {
                response.put(Constants.CODE, Response.SC_OK);
                String sb = "Média da Cidade " + city + " -> " + result;
                response.put(Constants.RESPONSE, sb);
            }
        }

        return response;
    }


    @GetMapping(value = "/prices/list/region", produces = "application/json")
    public  HashMap<String, Object>  findByRegion(@RequestParam String region){
        HashMap<String, Object> response = new HashMap<>();
        if(region == null || region.equals("") || region.isEmpty()){
            response.put(Constants.CODE, Response.SC_NOT_ACCEPTABLE);
            response.put(Constants.MESSAGE, Constants.ERROR_WRONG_DATA);
        }else {
            List<PriceTableItem> list = priceTableService.findByRegion(region);
            if (list.size() == 0) {
                response.put(Constants.CODE, Response.SC_NO_CONTENT);
                response.put(Constants.MESSAGE, Constants.REQUEST_NO_CONTENT);
            } else {
                response.put(Constants.CODE, Response.SC_OK);
                response.put(Constants.RESPONSE, list);
            }
        }

        return response;
    }

    @GetMapping(value = "/prices/list/group", produces = "application/json")
    public  HashMap<String, Object> groupByDistributor(){
        HashMap<String, Object> response = new HashMap<>();
        List<GroupResponseItem> listGroupDistributor = new ArrayList<>();
        List<PriceTableDistributorGroup> list = priceTableService.groupByDistributor();
        if(list.size() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_CONTENT);
        }else {
            for (PriceTableDistributorGroup ll : list) {
                GroupResponseItem item = new GroupResponseItem(ll.getDistributor(), ll.getCount());
                listGroupDistributor.add(item);
            }
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, listGroupDistributor);
        }
        return response;
    }

    @GetMapping(value = "/prices/list/date", produces = "application/json")
    public HashMap<String, Object> groupByDate(){
        HashMap<String, Object> response = new HashMap<>();
        List<GroupResponseItem> listGroupDate = new ArrayList<>();
        List<PriceTableDateGroup> list = priceTableService.groupByDate();
        if(list.size() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_CONTENT);
        }else {
            for (PriceTableDateGroup dd : list) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String date = format.format(dd.getDate());
                GroupResponseItem item = new GroupResponseItem(date, dd.getCount() );
                listGroupDate.add(item);
            }
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, listGroupDate);
        }

        return response;
    }

    @GetMapping(value = "/prices/list/city", produces = "application/json")
    public HashMap<String, Object> groupAverageByCity(){
        HashMap<String, Object> response = new HashMap<>();
        List<AverageResponseItem> listAverageCity= new ArrayList<>();
        List<PriceTableAverageCity> list = priceTableService.groupAveragesByCity();
        if(list == null || list.size() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_CONTENT);
        }else {
            for (PriceTableAverageCity ptac : list) {
                AverageResponseItem item = new AverageResponseItem(ptac.getSaleAverage(), ptac.getPurchaseAverage(), ptac.getCity());
                listAverageCity.add(item);
            }
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, listAverageCity);
        }
        return response;
    }

    @GetMapping(value = "/prices/list/flag", produces = "application/json")
    public HashMap<String, Object> groupAverageByFlag(){
        HashMap<String, Object> response = new HashMap<>();
        List<AverageResponseItem> listAverageFlag= new ArrayList<>();
        List<PriceTableAverageFlag> list = priceTableService.groupAveragesByFlag();
        if(list == null || list.size() == 0){
            response.put(Constants.CODE, Response.SC_NO_CONTENT);
            response.put(Constants.MESSAGE, Constants.REQUEST_NO_CONTENT);
        }else {
            for (PriceTableAverageFlag ptaf : list) {
                AverageResponseItem item = new AverageResponseItem(ptaf.getSaleAverage(), ptaf.getPurchaseAverage(), ptaf.getFlag());
                listAverageFlag.add(item);
            }
            response.put(Constants.CODE, Response.SC_OK);
            response.put(Constants.RESPONSE, listAverageFlag);
        }
        return response;
    }



}
