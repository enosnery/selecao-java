package com.enosnery.RestAPI.models;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private Long installationCode;

    private String product;

    private Date collectDate;

    private Double purchasePrice;

    private Double salePrice;

    private String measurement;

    private String flag;

    public PriceTableItem() {
    }

    public PriceTableItem(String region, String state, String city, String distributor, Long installationCode, String product, Date collectDate, Double purchasePrice, Double salePrice, String measurement, String flag) {
        this.region = region;
        this.state = state;
        this.city = city;
        this.distributor = distributor;
        this.installationCode = installationCode;
        this.product = product;
        this.collectDate = collectDate;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.measurement = measurement;
        this.flag = flag;
    }

    public PriceTableItem(String[] csv) throws ParseException {
        this.region = csv[0];
        this.state = csv[1];
        this.city = csv[2];
        this.distributor = csv[3];
        this.installationCode = Long.parseLong(csv[4]);
        this.product = csv[5];
        this.collectDate = new SimpleDateFormat("dd/MM/yyyy").parse(csv[6]);
        if(csv[7] != null && !(csv[7].equals("")) && !(csv[7].isEmpty())) {
            String number = csv[7].replace(",", ".");
            this.purchasePrice = Double.parseDouble(number);
        }else{
            this.purchasePrice = 0d;
        }
        if(csv[8] != null && !(csv[8].equals("")) && !(csv[8].isEmpty())) {
            String number = csv[8].replace(",", ".");
            this.salePrice = Double.parseDouble(number);
        }else{
            this.purchasePrice = 0d;
        }
        this.measurement = csv[9];
        this.flag = csv[10];
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

    public Long getInstallationCode() {
        return installationCode;
    }

    public void setInstallationCode(Long installationCode) {
        this.installationCode = installationCode;
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
