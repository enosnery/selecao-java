package com.enosnery.RestAPI.models;

import io.swagger.annotations.ApiModel;
import org.slf4j.ILoggerFactory;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SequenceGenerator(name="seq1", initialValue=0, allocationSize=100)
@Entity
@Table(name = "PriceTable")
@ApiModel(value = "Item da tabela de preço")
public class PriceTableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    private Long id;

    private String region;

    private String state;

    private String city;

    private String distributor;

    private String cnpj;

    private String product;

    private Date collectDate;

    private Double purchasePrice;

    private Double salePrice;

    private String measurement;

    private String flag;

    public PriceTableItem() {
    }

    public PriceTableItem(String region, String state, String city, String distributor, String cnpj, String product, Date collectDate, Double purchasePrice, Double salePrice, String measurement, String flag) {
        this.region = region;
        this.state = state;
        this.city = city;
        this.distributor = distributor;
        this.cnpj = cnpj;
        this.product = product;
        this.collectDate = collectDate;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.measurement = measurement;
        this.flag = flag;
    }

    public PriceTableItem(String[] csv) throws ParseException {
        this.region = csv[0].replaceAll("\\P{Print}", "");
        this.state = csv[1].replaceAll("\\P{Print}", "");
        this.city = csv[2].replaceAll("\\P{Print}", "");
        this.distributor = csv[3].replaceAll("\\P{Print}", "");
        this.cnpj = csv[4].replaceAll("\\P{Print}", "");
        this.product = csv[5].replaceAll("\\P{Print}", "");
        this.collectDate = new SimpleDateFormat("dd/MM/yyyy").parse(csv[6].replaceAll("\\P{Print}", ""));
        if(csv[7] != null && !(csv[7].equals("")) && !(csv[7].isEmpty())) {
            String number = csv[7].replaceAll("\\P{Print}", "").replace(",", ".");
            this.purchasePrice = Double.parseDouble(number);
        }else{
            this.purchasePrice = 0d;
        }
        if(csv[8] != null && !(csv[8].replaceAll("\\P{Print}", "").equals("")) && !(csv[8].replaceAll("\\P{Print}", "").isEmpty())) {
            String number = csv[8].replaceAll("\\P{Print}", "").replace(",", ".");
            this.salePrice = Double.parseDouble(number);
        }else{
            this.salePrice = 0d;
        }
        this.measurement = csv[9].replaceAll("\\P{Print}", "");
        this.flag = csv[10].replaceAll("\\P{Print}", "");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
