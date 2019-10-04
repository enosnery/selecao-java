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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        HashMap<String, PriceTableItem> hashMap = new HashMap<>();
        List<PriceTableItem> list = priceTableService.findAllItems();
        if(list.size() == 0){
            return "Não existe nenhum dado registrado";
        }
        for(PriceTableItem pti : list) {
            hashMap.put("Item", pti);
        }
        return new Gson().toJson(hashMap);
    }

    @ApiOperation(value = "Salvar novo item na tabela de preços.", httpMethod = "POST")
    @PostMapping(value = "/prices/insert")
    public String insertItem(@RequestBody PriceTableItem item){
        PriceTableItem newItem = new PriceTableItem(item.getRegion(), item.getState(), item.getCity(), item.getDistributor(), item.getInstallationCode(), item.getProduct(), item.getCollectDate(), item.getPurchasePrice(), item.getSalePrice(), item.getMeasurement(), item.getFlag());
        priceTableService.savePriceTableItem(newItem);
        if(newItem.getId() == null || newItem.getId() == 0){
            return "Não foi possível salvar o item.";
        }
        HashMap<String, Long> map = new HashMap<>();
        String sb = "itemId";
        map.put(sb, newItem.getId());
        return new Gson().toJson(map);

    }

    @ApiOperation(value = "Atualizar item na tabela de preços.", httpMethod = "POST")
    @PostMapping(value = "/prices/update")
    public String updateItem(@RequestParam Long id){
        PriceTableItem updateItem = priceTableService.findById(id);
        if(updateItem != null) {
            priceTableService.savePriceTableItem(updateItem);
            return updateItem.getId() != null ? "Item Atualizado!" : "Problemas ao atualizar o item.";
        }else{
            return "Esse item não existe.";
        }
    }

    @ApiOperation(value = "Remover item da tabela de preços.", httpMethod = "POST")
    @PostMapping(value = "/prices/delete")
    public String deleteItem (@RequestParam Long id){
        if(id == null || id == 0){
            return "ID Inválido. Favor verificar e tentar novamente.";
        }
        priceTableService.deleteItem(id);
        return "Item deletado.";
    }

    @ApiOperation(value = "Média de preço de venda de combustível dado uma cidade específica.", httpMethod = "GET")
    @RequestMapping(value = "/prices/average/city", method = RequestMethod.GET, produces = "application/json")
    public String averageByCity(@RequestParam String city){
        if(city == null || city.equals("") || city.isEmpty()){
            return "Preencha os campos corretamente.";
        }
        Double result = priceTableService.averageByCity(city);
        if(result == null || result == 0d){
            return "Cidade inválida";
        }
        HashMap<String, Double> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("Média da Cidade: ");
        sb.append(city);
        map.put(sb.toString(), result);
        return new Gson().toJson(map);
    }


    @RequestMapping(value = "/prices/list/region", method = RequestMethod.GET, produces = "application/json")
    public String findByRegion(@RequestParam String region){
        HashMap<String, PriceTableItem> hashMap = new HashMap<>();
        if(region == null || region.equals("") || region.isEmpty()){
            return "Preencha os campos corretamente.";
        }
        List<PriceTableItem> list = priceTableService.findByRegion(region);
        if(list.size() == 0){
            return "Não existem registros para esta região";
        }
        for(PriceTableItem pti : list) {
            hashMap.put("Item", pti);
        }
        return new Gson().toJson(hashMap);
    }

    @RequestMapping(value = "/prices/list/group", method = RequestMethod.GET, produces = "application/json")
    public String groupByDistributor(){
        HashMap<String, Integer> map = new HashMap<>();
        List<PriceTableDistributorGroup> list = priceTableService.groupByDistributor();
        if(list.size() == 0){
            return "Não foi possível agrupar os dados";
        }
        for(PriceTableDistributorGroup ll : list){
            map.put(ll.getDistributor(), ll.getCount());
        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/prices/list/date", method = RequestMethod.GET, produces = "application/json")
    public String groupByDate(){
        HashMap<String, Integer> map = new HashMap<>();
        List<PriceTableDateGroup> list = priceTableService.groupByDate();
        if(list.size() == 0){
            return "Não foi possível agrupar os dados.";
        }
        for(PriceTableDateGroup dd : list){
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String date = format.format(dd.getDate());
            map.put(date, dd.getCount());

        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/prices/list/city", method = RequestMethod.GET, produces = "application/json")
    public String groupAverageByCity(){
        HashMap<String, HashMap> map = new HashMap<>();

        List<PriceTableAverageCity> list = priceTableService.groupAveragesByCity();
        if(list == null || list.size() == 0){
            return "Nenhum valor a ser mostrado";
        }

        for(PriceTableAverageCity ptac : list){
            HashMap<String, Double> mapItem = new HashMap<>();
            mapItem.put("SaleAverage", ptac.getSaleAverage());
            mapItem.put("PurchaseAverage", ptac.getPurchaseAverage());
            map.put(ptac.getCity(), mapItem);
        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/prices/list/flag", method = RequestMethod.GET, produces = "application/json")
    public String groupAverageByFlag(){
        HashMap<String, HashMap> map = new HashMap<>();
        List<PriceTableAverageFlag> list = priceTableService.groupAveragesByFlag();
        if(list == null || list.size() == 0){
            return "Nenhum valor a ser mostrado";
        }
        for(PriceTableAverageFlag ptaf : list){
            HashMap<String, Double> mapItem = new HashMap<>();
            mapItem.put("SaleAverage", ptaf.getSaleAverage());
            mapItem.put("PurchaseAverage", ptaf.getPurchaseAverage());
            map.put(ptaf.getFlag(), mapItem);
        }
        return new Gson().toJson(map);
    }

    @PostMapping(value = "/prices/import", consumes = {"multipart/form-data"})
    public String importCSV(@RequestPart("file") MultipartFile file) throws Exception {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (extension != null && !(extension.equals("csv"))) {
                return "Arquivo em Formato Inválido";
            }
            List<String[]> list = priceTableService.readAll(file);
            if (list.size() == 0) {
                return "Não foi possível importar os dados do CSV";
            }
            priceTableService.saveAll(list);
            return "CSV importado!";
        }catch (FileUploadException fe){
            fe.printStackTrace();
            return "Não foi adicionado nenhum arquivo.";
        }
    }

}
