package com.enosnery.RestAPI.controllers;

import com.enosnery.RestAPI.models.PriceTableItem;
import com.enosnery.RestAPI.services.ImportService;
import com.enosnery.RestAPI.services.PriceTableService;
import com.enosnery.RestAPI.utils.Constants;
import org.apache.catalina.connector.Response;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Controller
@CrossOrigin
public class ImportController {

    @Autowired
    ImportService importService;

    @Autowired
    PriceTableService priceTableService;

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public HashMap<String, Object> importCSV(@RequestPart("file") MultipartFile file) throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (extension != null && !(extension.equals("csv"))) {
                response.put(Constants.CODE, Response.SC_UNSUPPORTED_MEDIA_TYPE);
                response.put(Constants.MESSAGE, Constants.INVALID_FORMAT);
            }else{
                List<String[]> list = importService.readAll(file);
                if (list.size() == 0) {
                    response.put(Constants.CODE, Response.SC_BAD_REQUEST);
                    response.put(Constants.MESSAGE, Constants.ERROR_LIST_EMPTY);
                }else {
                    priceTableService.saveAll(list);
                    response.put(Constants.CODE, Response.SC_CREATED);
                    response.put(Constants.RESPONSE, Constants.SUCCESS_IMPORTED);
                }
            }
        }catch (FileUploadException fe){
            response.put(Constants.CODE, Response.SC_BAD_REQUEST);
            response.put(Constants.MESSAGE, Constants.ERROR_NO_FILE);
        }
        return response;
    }
}
