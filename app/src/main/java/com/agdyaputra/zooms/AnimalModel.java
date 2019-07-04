package com.agdyaputra.zooms;

import java.io.Serializable;

public class AnimalModel implements Serializable {

    String animal_id, animal_name, species, gender, place_name, status, key;

    public AnimalModel() {
    }

    public AnimalModel(String Animal_Id,String AnimalName, String Species, String Gender, String Status, String Placement) {
        this.animal_id = Animal_Id;
        this.animal_name = AnimalName;
        this.species = Species;
        this.gender = Gender;
        this.status = Status;
        this.place_name = Placement;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }
}
