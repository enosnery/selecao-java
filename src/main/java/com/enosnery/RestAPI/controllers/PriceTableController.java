package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.models.PriceTableItem;
import com.enosnery.RestAPI.services.PriceTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceTableController {

    @Autowired
    PriceTableService priceTableService;

    @RequestMapping(value = "/prices", method = RequestMethod.GET, produces = "application/json")
    public String prices(){
        String json = new Gson().toJson(priceTableService.findAllItems());
        return json;
    }


}
