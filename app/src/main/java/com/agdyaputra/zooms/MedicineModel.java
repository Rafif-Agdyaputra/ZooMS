package com.agdyaputra.zooms;

public class MedicineModel {

    private String med_id, med_name, stock;

    public MedicineModel(){

    }

    public MedicineModel(String med_id, String med_name, String stock) {
        this.med_id = med_id;
        this.med_name = med_name;
        this.stock = stock;
    }

    public String getMed_id() {
        return med_id;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
