package com.agdyaputra.zooms;

public class TreatmentModel {

    private String med_name, dosage;
    private String treatment_id, animal_name, species, gender, place_name, treatment_time;

    public TreatmentModel(String med_name, String dosage, String treatment_id, String animal_name, String species, String gender, String place_name, String treatment_time) {
        this.med_name = med_name;
        this.dosage = dosage;
        this.treatment_id = treatment_id;
        this.animal_name = animal_name;
        this.species = species;
        this.gender = gender;
        this.place_name = place_name;
        this.treatment_time = treatment_time;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getTreatment_id() {
        return treatment_id;
    }

    public void setTreatment_id(String treatment_id) {
        this.treatment_id = treatment_id;
    }

    public String getAnimal_name() {
        return animal_name;
    }

    public void setAnimal_name(String animal_name) {
        this.animal_name = animal_name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getTreatment_time() {
        return treatment_time;
    }

    public void setTreatment_time(String treatment_time) {
        this.treatment_time = treatment_time;
    }
}
