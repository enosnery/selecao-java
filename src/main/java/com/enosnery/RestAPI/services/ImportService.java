package com.enosnery.RestAPI.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ImportService {

    public List<String[]> readAll(MultipartFile mfile) throws Exception {
        File file = convert(mfile);
        CSVReaderBuilder builder = new CSVReaderBuilder(new FileReader(file));
        CSVReader csvReader = builder.withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).withSkipLines(1).build();
        List<String[]> list;
        list = csvReader.readAll();
        csvReader.close();
        return list;
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
